package com.tech.ems.employee.salary;

import com.tech.ems.employee.salary.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SalaryTest {

    @ParameterizedTest
    @MethodSource("invalidSalaryComponent")
    void create_salary_with_empty_components_should_throw_exception(Instant effectiveDate, List<SalaryComponent> components) {
        assertThrows(IllegalArgumentException.class, () -> new Salary(effectiveDate, components));
    }

    @Test
    void fixedMonthlySalary_should_create_salary_with_fixed_monthly_salary_component() {
        Salary salary = Salary.fixedMonthlySalary(Money.euro(1000));
        assertThat(salary.components()).containsExactlyElementsOf(
                List.of(new FixedSalaryComponent("Base Salary", PaymentRate.of(PaymentRate.Base.MONTHLY, Money.euro(1000)), PaymentSchedule.MONTHLY)));
    }

    private static List<Object[]> invalidSalaryComponent() {
        return List.of(
                new Object[]{null, null},
                new Object[]{Instant.now(), null},
                new Object[]{Instant.now(), List.of()},
                new Object[]{null, List.of(Salary.fixedMonthlySalary(Money.euro(1000)))}
        );
    }
}
