package com.tech.ems.employee;

import com.tech.ems.employee.salary.Salary;

public record EmployeeCreateCommand(String email, String name, String position, Salary salary) {
}
