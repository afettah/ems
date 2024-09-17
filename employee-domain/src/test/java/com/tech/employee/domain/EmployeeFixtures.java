package com.tech.employee.domain;

import com.tech.employee.domain.salary.Money;
import com.tech.employee.domain.salary.Salary;

import java.time.Instant;

public class EmployeeFixtures {

    /**
     * Create a new employee with fixed values
     * @return always the same employee
     */
    public static Employee johnDoe() {
        return Employee.create(new EmployeeCreateCommand("john.doe@domain.com", "John Doe", "Software Engineer", Salary.fixedMonthlySalary(Money.euro(50000))));
    }

    public static Employee generate() {
        return generate(EmployeeId.generate());
    }

    public static Employee generate(EmployeeId id) {
        return new Employee(id,  "email-" + id.id() + "@domain.com", "name" + id.id(), "position" + id, Salary.fixedMonthlySalary(Money.euro(10000)), Instant.now(), Instant.now());
    }
}
