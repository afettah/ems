package com.tech.employee.domain.salary;

public record PaymentRate(Base base, Money amount) {

    public PaymentRate {
        if (amount == null) {
            throw new IllegalArgumentException("Payment rate amount cannot be null");
        }
        if (base == null) {
            throw new IllegalArgumentException("Payment rate base cannot be null");
        }
    }

    public static PaymentRate of(Base base, Money amount) {
        return new PaymentRate(base, amount);
    }

    public enum Base {
        HOURLY,
        DAILY,
        WEEKLY,
        BIWEEKLY,
        MONTHLY,
        YEARLY
    }
}
