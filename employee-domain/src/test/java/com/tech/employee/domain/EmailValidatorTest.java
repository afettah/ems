package com.tech.employee.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class EmailValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "test@example.com",
            "user.name@domain.co.uk",
            "user+alias@domain.com",
            "user.name@sub.domain.org",
            "user_name@domain.com",
            "username@domain.io",
            "email@domain.tech"
    })
    void should_return_true_when_email_is_valid(String email) {
        boolean result = EmailValidator.isValidEmail(email);
        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "invalid-email",
            "invalid-email@",
            "invalid-email@domain",
            "invalid-email@domain.",
            "@domain.com",
            "email@domain..com",
            "email@domain.c",
            "user.@domain.com",
            "user@domain_com"
    })
    void should_return_false_when_email_is_invalid(String email) {
        boolean result = EmailValidator.isValidEmail(email);
        assertThat(result).isFalse();
    }
}
