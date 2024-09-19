package com.tech.ems.employee;

import com.tech.ems.employee.position.Position;
import com.tech.ems.employee.salary.Salary;

import java.util.Optional;


public record EmployeeUpdateCommand(Position position, Salary salary) {

    public Optional<Position> getPosition() {
        return Optional.ofNullable(position);
    }

    public Optional<Salary> getSalary() {
        return Optional.ofNullable(salary);
    }
}
