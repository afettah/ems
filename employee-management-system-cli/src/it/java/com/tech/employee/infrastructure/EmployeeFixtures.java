package com.tech.employee.infrastructure;

import com.tech.employee.domain.Employee;
import com.tech.employee.domain.EmployeeCreateCommand;
import com.tech.employee.domain.EmployeeId;
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
        return generate(id, "name-" + id);
    }

    public static Employee generate(String name) {
        return generate(EmployeeId.generate(), name);
    }

    public static Employee generate(EmployeeId id, String name) {
        return new Employee(id,  "email-" + id.id() + "@domain.com", name, "position" + id, Salary.fixedMonthlySalary(Money.euro(10000)), Instant.now(), Instant.now());
    }
}
