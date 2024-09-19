package com.tech.ems.employee;

import com.tech.ems.employee.salary.Salary;

import java.util.Optional;


public record EmployeeUpdateCommand(String position, Salary salary) {

    public Optional<String> getPosition() {
        return Optional.ofNullable(position);
    }

    public Optional<Salary> getSalary() {
        return Optional.ofNullable(salary);
    }
}
