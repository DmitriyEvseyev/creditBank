package com.neoflex.calculator.services;

import com.neoflex.calculator.model.dto.PaymentScheduleElementDto;
import com.neoflex.calculator.model.dto.ScoringDataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CreatePaymentScheduleElementDtoService {
    public List<PaymentScheduleElementDto> createPaymentScheduleElementDto(ScoringDataDto scoringDataDto,
                                                                           BigDecimal rate,
                                                                           BigDecimal monthlyPayment) {
        List<PaymentScheduleElementDto> dtoList = new ArrayList<>();
        for (int i = 1; i <= scoringDataDto.getTerm(); i++) {
            BigDecimal totalPayment = getTotalPayment(scoringDataDto.getAmount(), i, dtoList);
            BigDecimal interestPayment = getInterestPayment(totalPayment, rate, i);
            BigDecimal debtPayment = getDebtPayment(monthlyPayment, interestPayment);
            BigDecimal remainingDebt = getRemainingDebt(totalPayment, debtPayment);
            if (i == scoringDataDto.getTerm()) {
                PaymentScheduleElementDto lastPaymentScheduleElementDto = PaymentScheduleElementDto.builder()
                        .number(i)
                        .date(getDatePayment(i))
                        .totalPayment(totalPayment)
                        .interestPayment(interestPayment)
                        .debtPayment(totalPayment)
                        .remainingDebt(getRemainingDebt(totalPayment, totalPayment))
                        .build();
                log.info("lastPaymentScheduleElementDto - {}", lastPaymentScheduleElementDto);
                dtoList.add(lastPaymentScheduleElementDto);
            } else {
                PaymentScheduleElementDto paymentScheduleElementDto = PaymentScheduleElementDto.builder()
                        .number(i)
                        .date(getDatePayment(i))
                        .totalPayment(totalPayment)
                        .interestPayment(interestPayment)
                        .debtPayment(debtPayment)
                        .remainingDebt(remainingDebt)
                        .build();
                log.info("paymentScheduleElementDto - {}", paymentScheduleElementDto);
                dtoList.add(paymentScheduleElementDto);
            }
        }
        log.info("dtoList - {}", dtoList);
        return dtoList;
    }

    private BigDecimal getTotalPayment(BigDecimal totalPayment, Integer count, List<PaymentScheduleElementDto> dtoList) {
        if (dtoList.size() > 0) {
            BigDecimal newTotalPayment = dtoList.get(count - 2).getRemainingDebt();
            log.info("newTotalPayment - {}", newTotalPayment);
            return newTotalPayment;
        }
        log.info("totalPayment - {}", totalPayment);
        return totalPayment;
    }

    private BigDecimal getDebtPayment(BigDecimal monthlyPayment, BigDecimal interestPayment) {
        BigDecimal debtPayment = monthlyPayment.subtract(interestPayment);
        log.info("debtPayment - {}", debtPayment);
        return debtPayment;
    }

    private BigDecimal getRemainingDebt(BigDecimal totalPayment, BigDecimal debtPayment) {
        BigDecimal remainingDebt = totalPayment.subtract(debtPayment);
        log.info("remainingDebt - {}", remainingDebt);
        return remainingDebt;
    }

    //    Сначала считаем проценты (сумма, которая идет на уплату процентов по кредиту):
    //    Остаток долга × Процентная ставка × Количество дней в месяце / кол. дней в году
    public BigDecimal getInterestPayment(BigDecimal totalPayment,
                                         BigDecimal annualInterestRate, int i) {
        BigDecimal interestPayment = totalPayment
                .multiply(annualInterestRate.divide(new BigDecimal(100), 4, RoundingMode.HALF_UP))
                .multiply(new BigDecimal(getDaysInMonth(i)))
                .divide(new BigDecimal(getDaysInYear()), 2, RoundingMode.HALF_UP);
        log.info("interestPayment - {}", interestPayment);
        return interestPayment;
    }

    // дата платежа, последний день следующего месяца
    public LocalDate getDatePayment(int count) {
        LocalDate newMonthPaymentWithoutDay = LocalDate.now().plusMonths(count);
        LocalDate datePayment = LocalDate.of(newMonthPaymentWithoutDay.getYear(),
                newMonthPaymentWithoutDay.getMonthValue(),
                getDaysInMonth(count));
        log.info("datePayment - {}", datePayment);
        return datePayment;
    }

    //Количество дней в месяце
    private Integer getDaysInMonth(Integer i) {
        LocalDate newMonthPaymentWithoutDay = LocalDate.now().plusMonths(i);
        Integer daysInMonth = newMonthPaymentWithoutDay.lengthOfMonth();
        log.info("daysInMonth - {}", daysInMonth);
        return daysInMonth;
    }

    //Количество дней в году
    private Integer getDaysInYear() {
        //    високосный +\-
        return LocalDate.now().isLeapYear() ? 366 : 365;
    }
}

//   300 000 рублей, 18 месяцев под 15% годовых.
//       ежемес пл. =  18 715,44 ₽.
//        Остаток долга × Процентная ставка × Количество дней в месяце / кол. дней в году
//
//        Если год не високосный, а в месяце 30 дней, получится 3698,63 ₽
//           — это сумма процентов, которые мы заплатим в первом месяце.
//           На погашение основного долга пойдет остаток от нашего ежемесячного платежа:
//           18 715,44 ₽ − 3698,63 ₽ = 15 016,81 ₽.
//
//        Во втором месяце сумма процентов начислится на сумму кредита минус платеж по
//           основному долгу в первом месяце: 300 000 ₽ − 15 015,81 ₽ = 284 983,19 ₽.
//
//           Считаем проценты во втором месяце. Предположим, что во втором месяце 31 день:
//           284 983,19 × 15% × 31 / 365 = 3630,61 ₽.
//
//        На погашение основного долга во втором месяце пойдет
//           15 084,83 ₽ (18 715,44 − 3630,61).
