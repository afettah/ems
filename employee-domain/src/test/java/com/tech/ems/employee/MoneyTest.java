package com.tech.ems.employee;

import com.tech.ems.employee.salary.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void money_should_create_instance_with_specific_amount_and_currency() {
        // Given
        BigDecimal amount = new BigDecimal("150.50");
        Currency currency = Currency.getInstance("USD");

        // When
        Money money = Money.of(amount, currency);

        // Then
        assertEquals(amount, money.amount(), "Amount should match the expected value");
        assertEquals(currency, money.currency(), "Currency should match the expected value");
    }

    @Test
    void money_of_null_amount_should_throw_exception() {
        // Given
        BigDecimal amount = null;
        Currency currency = Currency.getInstance("USD");

        // When
        // Then
        assertThrows(NullPointerException.class, () -> Money.of(amount, currency));
    }

    @Test
    void money_of_null_currency_should_throw_exception() {
        // Given
        BigDecimal amount = new BigDecimal("150.50");
        Currency currency = null;

        // When
        // Then
        assertThrows(NullPointerException.class, () -> Money.of(amount, currency));
    }

    @Test
    void euro_should_create_money_instance_with_euro_currency() {
        // Given
        double amount = 100.0;
        BigDecimal expectedAmount = BigDecimal.valueOf(amount);
        Currency expectedCurrency = Currency.getInstance("EUR");

        // When
        Money money = Money.euro(amount);

        // Then
        assertEquals(expectedAmount, money.amount(), "Amount should match the expected value");
        assertEquals(expectedCurrency, money.currency(), "Currency should be EUR");
    }

    @Test
    void euro_should_create_money_instance_with_negative_amount() {
        // Given
        double amount = -50.0;
        BigDecimal expectedAmount = BigDecimal.valueOf(amount);
        Currency expectedCurrency = Currency.getInstance("EUR");

        // When
        Money money = Money.euro(amount);

        // Then
        assertEquals(expectedAmount, money.amount(), "Negative amount should match the expected value");
        assertEquals(expectedCurrency, money.currency(), "Currency should be EUR");
    }

    @Test
    void euro_should_create_money_instance_with_zero_amount() {
        // Given
        double amount = 0.0;
        BigDecimal expectedAmount = BigDecimal.valueOf(amount);
        Currency expectedCurrency = Currency.getInstance("EUR");

        // When
        Money money = Money.euro(amount);

        // Then
        assertEquals(expectedAmount, money.amount(), "Zero amount should match the expected value");
        assertEquals(expectedCurrency, money.currency(), "Currency should be EUR");
    }

}
