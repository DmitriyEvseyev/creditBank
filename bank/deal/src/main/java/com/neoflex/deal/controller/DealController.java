package com.neoflex.deal.controller;

import com.neoflex.deal.model.dto.*;
import com.neoflex.deal.model.entities.Client;
import com.neoflex.deal.model.entities.Credit;
import com.neoflex.deal.model.entities.Statement;
import com.neoflex.deal.model.enumFilds.ApplicationStatusEnum;
import com.neoflex.deal.services.ClientService;
import com.neoflex.deal.services.CreditService;
import com.neoflex.deal.services.ScoringDataDtoService;
import com.neoflex.deal.services.StatementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.neoflex.deal.model.enumFilds.ApplicationStatusEnum.APPROVED;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@RequestMapping("/deal")
//@RequiredArgsConstructor
@Slf4j
@Tag(name = "DealController", description = "контроллер \"Сделка\"")
public class DealController {

    private final ClientService clientService;
    private final StatementService statementService;
    private final ScoringDataDtoService scoringDataDtoService;
    private final CreditService creditService;
    private final RestClient restClient;

    @Autowired
    public DealController(ClientService clientService,
                          StatementService statementService,
                          ScoringDataDtoService scoringDataDtoService,
                          CreditService creditService) {
        this.clientService = clientService;
        this.statementService = statementService;
        this.scoringDataDtoService = scoringDataDtoService;
        this.creditService = creditService;
        this.restClient = RestClient.create();
    }

    @PostMapping("/statement")
    @Operation(summary = "расчёт возможных условий кредита",
            description = "приходит LoanStatementRequestDto, создаётся сущность Client, " +
                    "Statement - в БД, отправляется POST запрос на /calculator/offers МС Калькулятор.")
    public ResponseEntity<List<LoanOfferDto>> createStatement(
            @RequestBody @Valid LoanStatementRequestDto loanStatementRequestDto) {

        log.info("loanStatementRequestDto - {}", loanStatementRequestDto);
        Client client = clientService.createClient(loanStatementRequestDto);
        Statement statement = statementService.createStatement(
                client,
                ApplicationStatusEnum.PREAPPROVAL,
                Timestamp.valueOf(LocalDateTime.now()));

        var response = restClient
                .post()
                .uri("http://localhost:8080/calculator/offers")
                .contentType(APPLICATION_JSON)
                .body(loanStatementRequestDto)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<LoanOfferDto>>() {
                });

        log.info("response - {}", response);
        List<LoanOfferDto> offers = response.getBody();

        for (LoanOfferDto offer : offers) {
            offer.setUuid(statement.getStatementId());
            log.info("Updated offer with UUID: {}", offer);
        }
        return ResponseEntity.ok(offers);
    }


    @PostMapping("/offer/select")
    @Operation(summary = "выбор предложения",
            description = "в заявке обновляется статус, история статусов(List<StatementStatusHistoryDto>), " +
                    "принятое предложение LoanOfferDto устанавливается в поле appliedOffer. " +
                    "Заявка сохраняется.")
    public void selectStatement(@RequestBody @Valid LoanOfferDto loanOfferDto) {
        log.info("loanOfferDto - {}", loanOfferDto);

        Statement statement = statementService.getStatement(loanOfferDto.getUuid());
        statement.setLoanOfferDto(loanOfferDto);
        Statement updateStatement = statementService.updateStatement(
                statement,
                Timestamp.valueOf(LocalDateTime.now()),
                ApplicationStatusEnum.APPROVED);
        log.info("updateStatement - {}", updateStatement);
    }

    @PostMapping("/calculate/{statementId}")
    @Operation(summary = "обновление заявки",
            description = "приходит FinishRegistrationRequestDto и statementId. " +
                    "ScoringDataDto насыщается информацией из FinishRegistrationRequestDto и Client. " +
                    "Отправляется POST запрос на /calculator/calc МС Калькулятор с телом ScoringDataDto через RestClient. " +
                    "На основе полученного из кредитного конвейера CreditDto создаётся сущность Credit и сохраняется в базу со статусом CALCULATED. " +
                    "В заявке обновляется статус, история статусов. Заявка сохраняется. ")
    public void calculateStatement(@RequestBody @Valid FinishRegistrationRequestDto finishRegistrationRequestDto,
                                   @PathVariable("statementId") String statementId) {
        log.info("FinishRegistrationRequestDto - {}", finishRegistrationRequestDto);
        log.info("statementId - {}", statementId);
        UUID statementIdUuid = UUID.fromString(statementId);

        Statement statement = statementService.getStatement(statementIdUuid);

        ScoringDataDto scoringDataDto = scoringDataDtoService.createScoringDataDto(finishRegistrationRequestDto, statement);
        log.info("scoringDataDto - {}", scoringDataDto);
        Client client = statement.getClient();
        Client clientUpdate = clientService.updateClient(client, finishRegistrationRequestDto);
        statement.setClient(clientUpdate);
        var response = restClient
                .post()
                .uri("http://localhost:8080/calculator/calc")
                .contentType(APPLICATION_JSON)
                .body(scoringDataDto)
                .retrieve()
                .toEntity(CreditDto.class);
        System.out.println("response");
        System.out.println(response.getBody());
        CreditDto creditDto = response.getBody();

        log.info("creditDto - {}", creditDto);
        Credit credit = creditService.createCredit(creditDto);
        log.info("credi - {}", credit);

        statement.setCredit(credit);
        Statement updateStatement = statementService.updateStatement(
                statement,
                Timestamp.valueOf(LocalDateTime.now()),
                ApplicationStatusEnum.CC_APPROVED);
        log.info("updateStatement - {}", updateStatement);
    }
}
