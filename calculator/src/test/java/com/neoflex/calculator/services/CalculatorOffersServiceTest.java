package com.neoflex.calculator.services;

import com.neoflex.calculator.model.LoanStatementRequest;
import com.neoflex.calculator.model.dto.LoanOfferDto;
import com.neoflex.calculator.model.dto.LoanStatementRequestDto;
import com.neoflex.calculator.utils.Converter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CalculatorOffersServiceTest {

    @Mock
    private Converter converter;

    @Mock
    private CreateLoanOfferService createOffer;

    @InjectMocks
    private CalculatorOffersService calculatorOffersService;

    private LoanStatementRequestDto loanStatementRequestDto;
    private LoanStatementRequest loanStatementRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Инициализация тестовых данных
        loanStatementRequestDto = new LoanStatementRequestDto();
        loanStatementRequestDto.setAmount(new BigDecimal("100000"));
        loanStatementRequestDto.setTerm(36);

        loanStatementRequest = new LoanStatementRequest();
        loanStatementRequest.setAmount(loanStatementRequestDto.getAmount());
        loanStatementRequest.setTerm(loanStatementRequestDto.getTerm());

        when(converter.convertLoanStatementRequestDtoToLoanStatementRequest(loanStatementRequestDto))
                .thenReturn(loanStatementRequest);
    }

    @Test
    public void testCalculateOffers() {
        // Настройка моков для CreateLoanOfferService
        when(createOffer.generateUUID()).thenReturn(UUID.fromString("d9d74d43-dcbc-4fd5-aeea-b52a6dd4873a"),
                UUID.fromString("d9d74d43-dcbc-4fd5-aeea-b52a6dd4873b"),
                UUID.fromString("d9d74d43-dcbc-4fd5-aeea-b52a6dd4873c"),
                        UUID.fromString("d9d74d43-dcbc-4fd5-aeea-b52a6dd4873d"));
        when(createOffer.getTotalAmount(any(), any(), any(), any())).thenReturn(
                new BigDecimal("110000"), // без страховки, без зарплатного клиента
                new BigDecimal("115000"), // с страховкой, без зарплатного клиента
                new BigDecimal("105000"), // без страховки, с зарплатным клиентом
                new BigDecimal("120000")  // с страховкой, с зарплатным клиентом
        );
        when(createOffer.getMonthlyPayment(any(), any(), any(), any())).thenReturn(
                new BigDecimal("3666.67"), // без страховки, без зарплатного клиента
                new BigDecimal("3833.33"), // с страховкой, без зарплатного клиента
                new BigDecimal("3500.00"),  // без страховки, с зарплатным клиентом
                new BigDecimal("4000.00")   // с страховкой, с зарплатным клиентом
        );
        when(createOffer.annualInterestRateCalculate(any(), any())).thenReturn(
                new BigDecimal("10.0"), // без страховки, без зарплатного клиента
                new BigDecimal("11.0"), // с страховкой, без зарплатного клиента
                new BigDecimal("9.0"),  // без страховки, с зарплатным клиентом
                new BigDecimal("12.0")  // с страховкой, с зарплатным клиентом
        );

        LoanOfferDto loanOfferDto1 = new LoanOfferDto(UUID.fromString("d9d74d43-dcbc-4fd5-aeea-b52a6dd4873a"), loanStatementRequestDto.getAmount(),
                new BigDecimal("110000"), 36, new BigDecimal("3666.67"),
                new BigDecimal("10.0"), false, false);

        LoanOfferDto loanOfferDto2 = new LoanOfferDto(UUID.fromString("d9d74d43-dcbc-4fd5-aeea-b52a6dd4873b"), loanStatementRequestDto.getAmount(),
                new BigDecimal("115000"), 36, new BigDecimal("3833.33"),
                new BigDecimal("11.0"), true, false);

        LoanOfferDto loanOfferDto3 = new LoanOfferDto(UUID.fromString("d9d74d43-dcbc-4fd5-aeea-b52a6dd4873c"), loanStatementRequestDto.getAmount(),
                new BigDecimal("105000"), 36, new BigDecimal("3500.00"),
                new BigDecimal("9.0"), false, true);

        LoanOfferDto loanOfferDto4 = new LoanOfferDto(UUID.fromString("d9d74d43-dcbc-4fd5-aeea-b52a6dd4873d"), loanStatementRequestDto.getAmount(),
                new BigDecimal("120000"), 36, new BigDecimal("4000.00"),
                new BigDecimal("12.0"), true, true);

        when(converter.convertListLoanOfferToListLoanOfferDto(any())).thenReturn(List.of(
                loanOfferDto1,
                loanOfferDto2,
                loanOfferDto3,
                loanOfferDto4
        ));

        // Выполнение тестируемого метода
        List<LoanOfferDto> result = calculatorOffersService.calculateOffers(loanStatementRequestDto);

        // Проверка результата
        assertEquals(4, result.size());
        assertEquals(loanOfferDto3, result.get(0)); // Проверка на минимальную ставку
        assertEquals(loanOfferDto1, result.get(1));
        assertEquals(loanOfferDto2, result.get(2));
        assertEquals(loanOfferDto4, result.get(3)); // Проверка на максимальную ставку

        verify(converter).convertLoanStatementRequestDtoToLoanStatementRequest(loanStatementRequestDto);
        verify(createOffer, times(4)).generateUUID();
        verify(createOffer, times(4)).getTotalAmount(any(), any(), any(), any());
        verify(createOffer, times(4)).getMonthlyPayment(any(), any(), any(), any());
        verify(createOffer, times(4)).annualInterestRateCalculate(any(), any());
    }
}