package com.tech.ems.employee;

import com.tech.ems.employee.salary.Salary;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.Objects;

@ToString(exclude = {"salary"})
@Getter
public class Employee {
    private final EmployeeId id;
    private final String email;
    private final String name;
    private String position;
    private Salary salary;
    private final Instant createdAt;
    private final Instant updatedAt;

    public Employee(EmployeeId id, String email, String name, String position, Salary salary, Instant createdAt, Instant updatedAt) {
        Objects.requireNonNull(id, "Employee id cannot be null");
        Objects.requireNonNull(email, "Employee email cannot be null");
        Objects.requireNonNull(name, "Employee name cannot be null");
        Objects.requireNonNull(position, "Employee position cannot be null");
        Objects.requireNonNull(salary, "Employee salary cannot be null");
        if (!EmailValidator.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        this.id = id;
        this.email = email.toLowerCase();
        this.name = name;
        this.createdAt = createdAt;
        this.position = position;
        this.salary = salary;
        this.updatedAt = updatedAt;
    }

    public static Employee create(EmployeeCreateCommand command) {
        return new Employee(EmployeeId.generate(), command.email(), command.name(), command.position(), command.salary(), null, null);
    }

    public void update(EmployeeUpdateCommand employeeCommand) {
        employeeCommand.getPosition().ifPresent(this::updatePosition);
        employeeCommand.getSalary().ifPresent(this::updateSalary);
    }

    public void updateSalary(Salary salary) {
        Objects.requireNonNull(salary, "Employee salary cannot be null");
        this.salary = salary;
    }

    public void updatePosition(String position) {
        Objects.requireNonNull(position, "Employee position cannot be null");
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id.equals(employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
