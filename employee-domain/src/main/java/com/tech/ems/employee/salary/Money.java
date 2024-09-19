package com.tech.ems.employee.salary;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public record Money(BigDecimal amount, Currency currency) {

    public Money {
        Objects.requireNonNull(amount, "Money amount cannot be null");
        Objects.requireNonNull(currency, "Money currency cannot be null");
    }

    public static Money of(BigDecimal amount, Currency currency) {
        return new Money(amount, currency);
    }

    public static Money euro(double amount) {
        return new Money(BigDecimal.valueOf(amount), Currency.getInstance("EUR"));
    }

    @Override
    public String toString() {
        return amount + " " + currency.getSymbol();
    }
}
