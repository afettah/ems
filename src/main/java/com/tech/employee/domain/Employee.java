package com.tech.employee.domain;

import com.tech.employee.domain.salary.Salary;
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
    private Instant updatedAt;

    public Employee(EmployeeId id, String email, String name, String position, Salary salary, Instant createdAt, Instant updatedAt) {
        Objects.requireNonNull(id, "Employee id cannot be null");
        Objects.requireNonNull(email, "Employee email cannot be null");
        Objects.requireNonNull(name, "Employee name cannot be null");
        Objects.requireNonNull(position, "Employee position cannot be null");
        Objects.requireNonNull(salary, "Employee salary cannot be null");
        Objects.requireNonNull(createdAt, "Employee createdAt cannot be null");

        this.id = id;
        this.email = email;
        this.name = name;
        this.createdAt = createdAt;
        this.position = position;
        this.salary = salary;
        this.updatedAt = updatedAt;
    }

    public static Employee create(EmployeeCreateCommand command) {
        return new Employee(EmployeeId.generate(), command.email(), command.name(), command.position(), command.salary(), Instant.now(), null);
    }

    public void updateSalary(Salary salary) {
        Objects.requireNonNull(salary, "Employee salary cannot be null");
        this.salary = salary;
        this.updatedAt = Instant.now();
    }

    public void updatePosition(String position) {
        Objects.requireNonNull(position, "Employee position cannot be null");
        this.position = position;
        this.updatedAt = Instant.now();
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
