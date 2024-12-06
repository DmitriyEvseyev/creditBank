package com.neoflex.deal.services;

import com.neoflex.deal.model.dto.FinishRegistrationRequestDto;
import com.neoflex.deal.model.dto.LoanStatementRequestDto;
import com.neoflex.deal.model.entities.Client;
import com.neoflex.deal.model.entities.Employment;
import com.neoflex.deal.model.entities.Passport;
import com.neoflex.deal.model.enumFilds.EmploymentStatusEnum;
import com.neoflex.deal.model.enumFilds.GenderEnum;
import com.neoflex.deal.model.enumFilds.MaritalStatusEnum;
import com.neoflex.deal.model.enumFilds.PositionEnum;
import com.neoflex.deal.repositories.ClientRepository;
import com.neoflex.deal.repositories.EmploymentRepository;
import com.neoflex.deal.repositories.PassportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PassportRepository passportRepository;

    @Mock
    private EmploymentRepository employmentRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClientService clientService;

    private LoanStatementRequestDto loanStatementRequestDto;
    private FinishRegistrationRequestDto finishRegistrationRequestDto;
    private Client clientCreateClient;
    private Client clientUpdateClient;
    private Passport passportCreateClient;
    private Passport passportUpdateClient;
    private Employment employment;

    @BeforeEach
    public void setUp() {
        loanStatementRequestDto = LoanStatementRequestDto.builder()
                .amount(new BigDecimal("150000.00"))
                .term(24)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .email("ivan.ivanov@example.com")
                .birthdate(LocalDate.of(1995, 10, 25))
                .passportSeries("1234")
                .passportNumber("567890")
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
        passportCreateClient = Passport.builder()
                .passportSeries("1234")
                .passportNumber("567890")
                .build();
        clientCreateClient = Client.builder()
                .lastName("Ivanov")
                .firstName("Ivan")
                .middleName("Ivanovich")
                .birthdate(LocalDate.of(1995, 10, 25))
                .email("ivan.ivanov@example.com")
                .passport(passportCreateClient)
                .build();
        employment = Employment.builder()
                .employmentStatus(EmploymentStatusEnum.EMPLOYED)
                .employerINN("1234567890")
                .salary(new BigDecimal("75000.00"))
                .position(PositionEnum.MID_MANAGER)
                .workExperienceTotal(120)
                .workExperienceCurrent(24)
                .build();
        passportUpdateClient = Passport.builder()
                .passportSeries("1234")
                .passportNumber("567890")
                .passportIssueBranch("Department 1")
                .passportIssueDate(LocalDate.of(2005, 10, 25))
                .build();
        clientUpdateClient = Client.builder()
                .lastName("Ivanov")
                .firstName("Ivan")
                .middleName("Ivanovich")
                .birthdate(LocalDate.of(1995, 10, 25))
                .email("ivan.ivanov@example.com")
                .gender(GenderEnum.MALE)
                .maritalStatus(MaritalStatusEnum.MARRIED)
                .dependentAmount(2)
                .passport(passportUpdateClient)
                .employment(employment)
                .accountNumber("1234567890")
                .build();
    }

    @Test
    public void testCreateClient() {
        when(modelMapper.map(loanStatementRequestDto, Passport.class)).thenReturn(passportCreateClient);
        when(modelMapper.map(loanStatementRequestDto, Client.class)).thenReturn(clientCreateClient);
        when(passportRepository.save(passportCreateClient)).thenReturn(passportCreateClient);
        when(clientRepository.save(clientCreateClient)).thenReturn(clientCreateClient);

        Client returnClient = clientService.createClient(loanStatementRequestDto);

        assertThat(returnClient).isNotNull();
        assertThat(returnClient).isEqualTo(clientCreateClient);
        verify(passportRepository).save(passportCreateClient);
        verify(clientRepository).save(clientCreateClient);
    }

    @Test
    public void testUpdateClient() {
        when(modelMapper.map(finishRegistrationRequestDto.getEmployment(), Employment.class)).thenReturn(employment);
        when(employmentRepository.save(employment)).thenReturn(employment);
        when(passportRepository.save(passportUpdateClient)).thenReturn(passportUpdateClient);
        when(clientRepository.save(clientUpdateClient)).thenReturn(clientUpdateClient);

        Client returnClient = clientService.updateClient(clientUpdateClient, finishRegistrationRequestDto);

        assertThat(returnClient).isNotNull();
        assertThat(returnClient).isEqualTo(clientUpdateClient);
        verify(employmentRepository).save(employment);
        verify(passportRepository).save(passportUpdateClient);
        verify(clientRepository).save(clientUpdateClient);
    }
}