package com.tech.employee.domain.salary;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public record Salary(Instant effectiveDate, List<SalaryComponent> components) {
    public Salary {
        if (components == null || components.isEmpty()) {
            throw new IllegalArgumentException("Salary must have at least one component");
        }
        components = new ArrayList<>(components);
        if (effectiveDate == null) {
            throw new IllegalArgumentException("Salary effective date cannot be null");
        }
    }

    public static Salary fixedMonthlySalary(Money amount) {
        return new Salary(Instant.now(), List.of(new FixedSalaryComponent("Base Salary", PaymentRate.of(PaymentRate.Base.MONTHLY, amount), PaymentSchedule.MONTHLY)));
    }

    public List<SalaryComponent> components() {
        return unmodifiableList(components);
    }
}
