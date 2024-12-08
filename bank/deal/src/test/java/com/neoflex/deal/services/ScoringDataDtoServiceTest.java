package com.neoflex.deal.services;

import com.neoflex.deal.model.dto.FinishRegistrationRequestDto;
import com.neoflex.deal.model.dto.LoanOfferDto;
import com.neoflex.deal.model.dto.ScoringDataDto;
import com.neoflex.deal.model.entities.Client;
import com.neoflex.deal.model.entities.Employment;
import com.neoflex.deal.model.entities.Statement;
import com.neoflex.deal.model.enumFilds.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScoringDataDtoServiceTest {
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private ScoringDataDtoService scoringDataDtoService;
    private Employment employment;
    private FinishRegistrationRequestDto finishRegistrationRequestDto;
    private Statement statement;
    private Client client;
    private LoanOfferDto loanOfferDto;
    private ScoringDataDto scoringDataDto;


    @BeforeEach
    public void setUp() {
        loanOfferDto = LoanOfferDto.builder()
                .requestedAmount(new BigDecimal("10000"))
                .term(12)
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build();
        scoringDataDto = new ScoringDataDto();
        employment = Employment.builder()
                .employmentStatus(EmploymentStatusEnum.EMPLOYED)
                .employerINN("1234567890")
                .salary(new BigDecimal("75000.00"))
                .position(PositionEnum.MID_MANAGER)
                .workExperienceTotal(120)
                .workExperienceCurrent(24)
                .build();
        finishRegistrationRequestDto = FinishRegistrationRequestDto.builder()
                .gender(GenderEnum.MALE)
                .maritalStatus(MaritalStatusEnum.MARRIED)
                .dependentAmount(2)
                .passportIssueDate(LocalDate.of(2005, 10, 25))
                .passportIssueBranch("Department 1")
                .employment(employment)
                .accountNumber("12345678901234567890")
                .build();
        client =  Client.builder()
                .clientId(UUID.fromString("4b76f44b-0ec6-48db-a5f9-1bf1fbfac53b"))
                .lastName("Ivanov")
                .firstName("Ivan")
                .middleName("Ivanovich")
                .birthdate(LocalDate.of(1995, 10, 25))
                .email("ivan.ivanov@example.com")
                .build();
        statement = Statement.builder()
                .loanOfferDto(loanOfferDto)
                .client(client)
                .build();
        scoringDataDto = ScoringDataDto.builder()
                .amount(new BigDecimal("10000"))
                .term(12)
                .firstName("Ivan")
                .middleName("Ivanovich")
                .lastName("Ivanov")
                .gender(GenderEnum.MALE)
                .maritalStatus(MaritalStatusEnum.MARRIED)
                .birthdate(LocalDate.of(1995, 10, 25))
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .passportIssueDate(LocalDate.of(2005, 10, 25))
                .passportIssueBranch("Department 1")
                .employment(employment)
                .accountNumber("12345678901234567890")
                .build();
    }

    @Test
    public void testCreateScoringDataDto() {
        when(modelMapper.map(client, ScoringDataDto.class)).thenReturn(scoringDataDto);

        ScoringDataDto result = scoringDataDtoService.createScoringDataDto(finishRegistrationRequestDto, statement);

        assertNotNull(result);
        assertEquals(scoringDataDto, result);
        assertEquals(result.getAmount(),loanOfferDto.getRequestedAmount());
        assertEquals(result.getFirstName(), client.getFirstName());
        assertEquals(result.getGender(),finishRegistrationRequestDto.getGender());
        assertEquals(result.getEmployment().getEmployerINN(),
                finishRegistrationRequestDto.getEmployment().getEmployerINN());
        verify(modelMapper).map(client, ScoringDataDto.class);
        verify(modelMapper).map(finishRegistrationRequestDto, scoringDataDto);
        verify(modelMapper).map(loanOfferDto, scoringDataDto);
    }
}
