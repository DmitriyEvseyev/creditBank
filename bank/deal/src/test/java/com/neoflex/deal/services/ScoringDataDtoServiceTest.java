package com.neoflex.deal.services;

import com.neoflex.deal.model.dto.FinishRegistrationRequestDto;
import com.neoflex.deal.model.dto.LoanOfferDto;
import com.neoflex.deal.model.dto.ScoringDataDto;
import com.neoflex.deal.model.entities.Client;
import com.neoflex.deal.model.entities.Employment;
import com.neoflex.deal.model.entities.Statement;
import com.neoflex.deal.model.enumFilds.EmploymentStatusEnum;
import com.neoflex.deal.model.enumFilds.GenderEnum;
import com.neoflex.deal.model.enumFilds.MaritalStatusEnum;
import com.neoflex.deal.model.enumFilds.PositionEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScoringDataDtoServiceTest {
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private Configuration configuration;
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




        // Настройка мока для getConfiguration()
        when(modelMapper.getConfiguration()).thenReturn(configuration);

        // Настройка мока для метода setAmbiguityIgnored
        doNothing().when(configuration).setAmbiguityIgnored(true);
        finishRegistrationRequestDto = new FinishRegistrationRequestDto();
        statement = mock(Statement.class);
        client = new Client(); // Инициализируйте клиента по мере необходимости
        loanOfferDto = new LoanOfferDto();
        scoringDataDto = new ScoringDataDto();

        when(statement.getClient()).thenReturn(client);
        when(statement.getLoanOfferDto()).thenReturn(loanOfferDto);
        when(modelMapper.map(client, ScoringDataDto.class)).thenReturn(scoringDataDto);
    }

    @Test
    public void testCreateScoringDataDto() {
        // Настройка мока для LoanOfferDto
        doNothing().when(modelMapper).map(finishRegistrationRequestDto, scoringDataDto);
//.thenAnswer(invocation -> {
//            ScoringDataDto dto = invocation.getArgument(1);
//            // Здесь можно установить значения из finishRegistrationRequestDto в scoringDataDto
//            // Например:
//            // dto.setSomeField(finishRegistrationRequestDto.getSomeField());
//            return null; // Возвращаем null, так как метод ничего не возвращает
//        });

        // Настройка мока для LoanOfferDto
        doNothing().when(modelMapper).map(loanOfferDto, scoringDataDto);
//            .thenAnswer(invocation -> {
//            ScoringDataDto dto = invocation.getArgument(1);
//            // Здесь можно установить значения из loanOfferDto в scoringDataDto
//            dto.setAmount(loanOfferDto.getRequestedAmount());
//            return null; // Возвращаем null, так как метод ничего не возвращает
//        });

        // Вызов метода
        ScoringDataDto result = scoringDataDtoService.createScoringDataDto(finishRegistrationRequestDto, statement);

        // Проверка результатов
        assertNotNull(result);
        assertEquals(scoringDataDto, result);

        // Проверка взаимодействий с моделью
        verify(statement).getClient();
        verify(statement).getLoanOfferDto();
        verify(modelMapper).map(client, ScoringDataDto.class);
        verify(modelMapper).map(finishRegistrationRequestDto, scoringDataDto);
        verify(modelMapper).map(loanOfferDto, scoringDataDto);
    }
}
