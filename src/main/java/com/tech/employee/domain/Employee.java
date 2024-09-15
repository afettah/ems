package com.tech.employee.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.Objects;

@ToString(exclude = {"monthlySalary"})
@Getter
public class Employee {
    private final EmployeeId id;
    private final String email;
    private final String name;
    private String position;
    private Money monthlySalary;
    private final Instant createdAt;
    private Instant updatedAt;

    public Employee(EmployeeId id, String email, String name, String position, Money monthlySalary, Instant createdAt, Instant updatedAt) {
        Objects.requireNonNull(id, "Employee id cannot be null");
        Objects.requireNonNull(email, "Employee email cannot be null");
        Objects.requireNonNull(name, "Employee name cannot be null");
        Objects.requireNonNull(position, "Employee position cannot be null");
        Objects.requireNonNull(monthlySalary, "Employee salary cannot be null");
        Objects.requireNonNull(createdAt, "Employee createdAt cannot be null");

        this.id = id;
        this.email = email;
        this.name = name;
        this.createdAt = createdAt;
        this.position = position;
        this.monthlySalary = monthlySalary;
        this.updatedAt = updatedAt;
    }

    public static Employee create(EmployeeId id, String email, String name, String position, Money salary) {
        return new Employee(id, email, name, position, salary, Instant.now(), null);
    }

    public void updateMonthlySalary(Money salary) {
        Objects.requireNonNull(salary, "Employee salary cannot be null");
        this.monthlySalary = salary;
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
