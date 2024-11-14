package com.neoflex.calculator.services;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
public class PaymentScheduleService {


    //    Сначала считаем проценты:
    //    Остаток долга × Процентная ставка × Количество дней в месяце / кол. дней в году
    private BigDecimal getInterestPayment(BigDecimal remainingDebt,
                                          BigDecimal annualInterestRate,
                                          Integer term,
                                          BigDecimal monthlyPayment) {
        return remainingDebt.multiply(annualInterestRate)
                .multiply(new BigDecimal(getDaysInMonth()))
                .divide(new BigDecimal(getDaysInYear()), 2, RoundingMode.HALF_UP);

    }

    // дата первого платежа
    private LocalDate getFirstDatePayment() {
        LocalDate currentDate = LocalDate.now();
        int monthOfFirstPayment = currentDate.plusMonths(1).getMonthValue();
        return LocalDate.of(currentDate.getYear(),
                monthOfFirstPayment,
                getDaysInMonth());
    }

    //Количество дней в месяце
    private Integer getDaysInMonth() {
        return LocalDate.now().lengthOfMonth();
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