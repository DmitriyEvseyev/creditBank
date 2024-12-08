package com.neoflex.statement.controller;

import com.neoflex.statement.model.LoanOfferDto;
import com.neoflex.statement.model.LoanStatementRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@Slf4j
@RequestMapping("/statement")
@RequiredArgsConstructor
@Tag(name = "StatementController", description = "Заявка.")
public class StatementController {
    private final RestClient restClient;

    @PostMapping()
    @Operation(summary = "Создаётся 4 кредитных предложения ",
            description = "создаётся 4 кредитных предложения LoanOfferDto " +
                    "на основании всех возможных комбинаций булевских полей isInsuranceEnabled и isSalaryClient ")
    public ResponseEntity<List<LoanOfferDto>> createOffers(@RequestBody @Valid LoanStatementRequestDto loanStatementRequestDto) {
        log.info("loanStatementRequestDto - {}", loanStatementRequestDto);
        var response = restClient
                .post()
                .uri("http://localhost:8081/deal/statement")
                .contentType(APPLICATION_JSON)
                .body(loanStatementRequestDto)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<LoanOfferDto>>() {
                });
        log.info("response - {}", response);
        List<LoanOfferDto> offers = response.getBody();
        return ResponseEntity.ok(offers);
    }

    @PostMapping("/offer")
    @Operation(summary = "Отправка выбранного предложения по кредиту.",
            description = "Происходит отправка выбранного предложения в мс \"Заявка\".")
    public void sendOffer(@RequestBody @Valid LoanOfferDto loanOfferDto) {
        log.info("loanOfferDto - {}", loanOfferDto);
        var response = restClient
                .post()
                .uri("http://localhost:8081/deal/offer/select")
                .contentType(APPLICATION_JSON)
                .body(loanOfferDto)
                .retrieve()
                .toEntity(String.class);
        log.info("responce - {}", response);
    }
}
