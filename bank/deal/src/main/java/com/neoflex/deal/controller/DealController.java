package com.neoflex.deal.controller;

import com.neoflex.deal.model.dto.*;
import com.neoflex.deal.model.entities.Client;
import com.neoflex.deal.model.entities.Credit;
import com.neoflex.deal.model.entities.Statement;
import com.neoflex.deal.model.enumFilds.ApplicationStatusEnum;
import com.neoflex.deal.services.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deal")
@Slf4j
@Tag(name = "DealController", description = "контроллер \"Сделка\"")
public class DealController {
    private final ClientService clientService;
    private final StatementService statementService;
    private final ScoringDataDtoService scoringDataDtoService;
    private final CreditService creditService;
    private final EmailKafkaService emailKafkaService;

    @PostMapping("/statement")
    @Operation(summary = "расчёт возможных условий кредита",
            description = """
                    Приходит LoanStatementRequestDto, создаётся сущность Client, Statement - в БД, отправляется POST
                    запрос на /calculator/offers МС Калькулятор.""")
    public ResponseEntity<List<LoanOfferDto>> createStatement(
            @RequestBody @Valid LoanStatementRequestDto loanStatementRequestDto) {

        log.info("loanStatementRequestDto - {}", loanStatementRequestDto);
        Client client = clientService.createClient(loanStatementRequestDto);
        Statement statement = statementService.createStatement(
                client,
                ApplicationStatusEnum.PREAPPROVAL,
                Timestamp.valueOf(LocalDateTime.now()));

        List<LoanOfferDto> offers = statementService.getListOffers(statement, loanStatementRequestDto);
        log.info("Updated offer with UUID: {}", offers);
        return ResponseEntity.ok(offers);
    }

    @PostMapping("/offer/select")
    @Operation(summary = "выбор предложения",
            description = """
                    В заявке обновляется статус, история статусов(List<StatementStatusHistoryDto>),
                    принятое предложение LoanOfferDto устанавливается в поле appliedOffer. Заявка сохраняется.""")
    public void selectStatement(@RequestBody @Valid LoanOfferDto loanOfferDto) {
        log.info("loanOfferDto - {}", loanOfferDto);

        Statement statement = statementService.getStatement(loanOfferDto.getUuid());
        statement.setLoanOfferDto(loanOfferDto);
        Statement updateStatement = statementService.updateStatement(
                statement,
                Timestamp.valueOf(LocalDateTime.now()),
                ApplicationStatusEnum.APPROVED);
        log.info("updateStatement - {}", updateStatement);
        emailKafkaService.createFinishRegistrationEmail(updateStatement);
    }

    @PostMapping("/calculate/{statementId}")
    @Operation(summary = "обновление заявки",
            description = """
                    Приходит FinishRegistrationRequestDto и statementId. ScoringDataDto насыщается информацией
                    из FinishRegistrationRequestDto и Client. Отправляется POST запрос на /calculator/calc
                    МС Калькулятор с телом ScoringDataDto через RestClient. На основе полученного из кредитного
                    конвейера CreditDto создаётся сущность Credit и сохраняется в базу со статусом CALCULATED.
                    В заявке обновляется статус, история статусов. Заявка сохраняется.""")
    public void calculateStatement(@RequestBody @Valid FinishRegistrationRequestDto finishRegistrationRequestDto,
                                   @PathVariable("statementId") String statementId) {
        log.info("FinishRegistrationRequestDto - {}", finishRegistrationRequestDto);
        log.info("statementId - {}", statementId);
        Statement statement = statementService.getStatement(UUID.fromString(statementId));

        ScoringDataDto scoringDataDto = scoringDataDtoService.createScoringDataDto(finishRegistrationRequestDto, statement);
        log.info("scoringDataDto - {}", scoringDataDto);

        Client clientUpdate = clientService.updateClient(statement.getClient(), finishRegistrationRequestDto);
        statement.setClient(clientUpdate);

        Credit credit = creditService.createCredit(scoringDataDto);
        log.info("credit - {}", credit);

        statement.setCredit(credit);
        Statement updateStatement = statementService.updateStatement(
                statement,
                Timestamp.valueOf(LocalDateTime.now()),
                ApplicationStatusEnum.CC_APPROVED);
        log.info("updateStatement - {}", updateStatement);
        emailKafkaService.createDocumentsEmail(updateStatement);
    }
}
