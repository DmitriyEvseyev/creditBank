package com.neoflex.deal.services;

import com.neoflex.deal.exeptions.EntityNotFoundException;
import com.neoflex.deal.model.dto.LoanOfferDto;
import com.neoflex.deal.model.dto.LoanStatementRequestDto;
import com.neoflex.deal.model.entities.Client;
import com.neoflex.deal.model.entities.Statement;
import com.neoflex.deal.model.entities.StatusHistory;
import com.neoflex.deal.model.enumFilds.ApplicationStatusEnum;
import com.neoflex.deal.repositories.StatementRepository;
import com.neoflex.deal.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.neoflex.deal.model.enumFilds.ChangeTypeEnum.MANUAL;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
public class StatementService {
    private final StatementRepository statementRepository;
    private final RestClient restClient;

    public Statement createStatement(Client client,
                                     ApplicationStatusEnum applicationStatusEnum,
                                     Timestamp timestamp) {
        Statement statement = Statement.builder()
                .client(client)
                .applicationStatusEnum(applicationStatusEnum)
                .creationDate(timestamp)
                .listStatusHistory(new ArrayList<>())
                .build();

        statement.getListStatusHistory().add(createStatusHistory(statement, timestamp));
        Statement saveStatement = statementRepository.save(statement);
        return saveStatement;
    }

    public Statement getStatement(UUID uuid) {
        Optional<Statement> statement = statementRepository.findById(uuid);
        return statement.orElseThrow(() -> new EntityNotFoundException(
                Constants.NOT_FOUND_STATEMENT_EXCEPTION_MESSAGE + uuid));
    }

    public Statement updateStatement(Statement statement,
                                     Timestamp timestamp,
                                     ApplicationStatusEnum applicationStatusEnum) {
        statement.setApplicationStatusEnum(applicationStatusEnum);
        statement.getListStatusHistory().add(createStatusHistory(statement, timestamp));
        return statementRepository.save(statement);
    }

    private StatusHistory createStatusHistory(Statement statement, Timestamp timestamp) {
        StatusHistory statusHistory = StatusHistory.builder()
                .status(statement.getApplicationStatusEnum())
                .time(timestamp)
                .changeType(MANUAL).build();
        return statusHistory;
    }

    public List<LoanOfferDto> getListOffers(Statement statement, LoanStatementRequestDto loanStatementRequestDto) {
        var response = restClient
                .post()
                .uri("http://localhost:8080/calculator/offers")
                .contentType(APPLICATION_JSON)
                .body(loanStatementRequestDto)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<LoanOfferDto>>() {
                });
        List<LoanOfferDto> offers = response.getBody();
        for (LoanOfferDto offer : offers) {
            offer.setUuid(statement.getStatementId());
        }
        return offers;
    }
}
