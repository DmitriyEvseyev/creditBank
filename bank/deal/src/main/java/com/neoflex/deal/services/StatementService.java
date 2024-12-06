package com.neoflex.deal.services;

import com.neoflex.deal.exeptions.NotFoundException;
import com.neoflex.deal.model.entities.Client;
import com.neoflex.deal.model.entities.Statement;
import com.neoflex.deal.model.entities.StatusHistory;
import com.neoflex.deal.model.enumFilds.ApplicationStatusEnum;
import com.neoflex.deal.repositories.StatementRepository;
import com.neoflex.deal.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static com.neoflex.deal.model.enumFilds.ApplicationStatusEnum.PREAPPROVAL;
import static com.neoflex.deal.model.enumFilds.ChangeTypeEnum.MANUAL;

@Service
@RequiredArgsConstructor
public class StatementService {
    private final StatementRepository statementRepository;

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
        return statement.orElseThrow(() -> new NotFoundException(
                Constants.NOT_FOUND_STATEMENT_EXCEPTION_MESSAGE + uuid));
    }

    public Statement updateStatement(Statement statement, Timestamp timestamp) {
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
}
