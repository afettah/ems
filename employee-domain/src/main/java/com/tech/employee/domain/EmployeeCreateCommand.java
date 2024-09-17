package com.tech.employee.domain;

import com.tech.employee.domain.salary.Salary;

public record EmployeeCreateCommand(String email, String name, String position, Salary salary) {
}
