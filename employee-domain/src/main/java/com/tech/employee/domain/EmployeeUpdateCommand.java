package com.tech.employee.domain;

import com.tech.employee.domain.salary.Salary;

import java.util.Optional;


public record EmployeeUpdateCommand(String position, Salary salary) {

    public Optional<String> getPosition() {
        return Optional.ofNullable(position);
    }

    public Optional<Salary> getSalary() {
        return Optional.ofNullable(salary);
    }
}
