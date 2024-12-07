package com.neoflex.deal.services;

import com.neoflex.deal.exeptions.NotFoundException;
import com.neoflex.deal.model.dto.PaymentScheduleElementDto;
import com.neoflex.deal.model.entities.Client;
import com.neoflex.deal.model.entities.Credit;
import com.neoflex.deal.model.entities.Statement;
import com.neoflex.deal.model.entities.StatusHistory;
import com.neoflex.deal.model.enumFilds.ApplicationStatusEnum;
import com.neoflex.deal.model.enumFilds.ChangeTypeEnum;
import com.neoflex.deal.model.enumFilds.CreditStatusEnum;
import com.neoflex.deal.repositories.StatementRepository;
import com.neoflex.deal.utils.Constants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatementServiceTest {
    @Mock
    private StatementRepository statementRepository;
    @InjectMocks
    private StatementService statementService;
    private Client client;
    private Statement statement;
    private Credit credit;
    private Timestamp timestamp;
    private StatusHistory statusHistory;
    private List<StatusHistory> historyList;

    @BeforeEach
    void setUp() {
        timestamp = Timestamp.valueOf(LocalDateTime.now());
        statusHistory = StatusHistory.builder()
                .status(ApplicationStatusEnum.PREAPPROVAL)
                .time(timestamp)
                .changeType(ChangeTypeEnum.MANUAL).build();
        historyList = new ArrayList<>();
        historyList.add(statusHistory);
        client = Client.builder()
                .clientId(UUID.fromString("4b76f44b-0ec6-48db-a5f9-1bf1fbfac53b"))
                .lastName("Ivanov")
                .firstName("Ivan")
                .middleName("Ivanovich")
                .birthdate(LocalDate.of(1995, 10, 25))
                .email("ivan.ivanov@example.com")
                .build();
        credit = Credit.builder()
                .amount(new BigDecimal("150000"))
                .term(36)
                .monthlyPayment(new BigDecimal("15000"))
                .rate(new BigDecimal("9.5"))
                .psk(new BigDecimal("10.5"))
                .paymentSchedule(List.of(new PaymentScheduleElementDto()))
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .creditStatusEnum(CreditStatusEnum.CALCULATED)
                .build();
        statement = Statement.builder()
                .client(client)
                .applicationStatusEnum(ApplicationStatusEnum.PREAPPROVAL)
                .creationDate(timestamp)
                .listStatusHistory(historyList)
                .build();

    }

    @Test
    void createStatement() {
        when(statementRepository.save(statement)).thenReturn(statement);

        Statement returnStatement = statementService.createStatement(client, ApplicationStatusEnum.PREAPPROVAL, timestamp);

        assertNotNull(returnStatement);
        assertEquals(statement, returnStatement);
    }

    @Test
    void getStatement() {
        UUID uuid = UUID.randomUUID();
        when(statementRepository.findById(uuid)).thenReturn(Optional.of(statement));
        Statement foundStatement = statementService.getStatement(uuid);
        assertNotNull(foundStatement);
        assertEquals(statement, foundStatement);
    }

    @Test
    public void GetStatement_NotFound() {
        UUID uuid = UUID.randomUUID();
        when(statementRepository.findById(uuid)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            statementService.getStatement(uuid);
        });
        Assertions.assertThat(exception).isInstanceOf(NotFoundException.class);
        assertEquals(Constants.NOT_FOUND_STATEMENT_EXCEPTION_MESSAGE + uuid, exception.getMessage());
    }

    @Test
    public void updateStatement() {
        when(statementRepository.save(statement)).thenReturn(statement);

        Statement updatedStatement = statementService.updateStatement(
                statement,
                timestamp,
                ApplicationStatusEnum.APPROVED);

        assertNotNull(updatedStatement);
        assertEquals(updatedStatement, statement);
        assertEquals(2, updatedStatement.getListStatusHistory().size());
    }
}
