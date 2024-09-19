package com.tech.ems.employee.salary;

import com.tech.ems.employee.salary.Money;
import com.tech.ems.employee.salary.PaymentRate;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class PaymentRateTest {

    @Test
    void newPaymentRate_shouldCreatePaymentRate() {
        PaymentRate paymentRate = new PaymentRate(PaymentRate.Base.MONTHLY, Money.euro(1000.00));
        assertThat(paymentRate).extracting(PaymentRate::base, PaymentRate::amount)
                .containsExactly(PaymentRate.Base.MONTHLY, Money.euro(1000.00));
    }

    @Test
    void newPaymentRate_withoutAmount_shouldThrowException() {
        assertThatThrownBy(() -> new PaymentRate(PaymentRate.Base.MONTHLY, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Payment rate amount cannot be null");
    }

    @Test
    void newPaymentRate_withoutBase_shouldThrowException() {
        assertThatThrownBy(() -> new PaymentRate(null, Money.euro(1000.00)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Payment rate base cannot be null");
    }
}
