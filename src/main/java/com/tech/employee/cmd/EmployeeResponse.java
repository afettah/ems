package com.tech.employee.cmd;

import com.tech.employee.domain.Employee;
import com.tech.employee.domain.salary.Salary;

import java.util.stream.Collectors;

record EmployeeResponse(String id, String name, String department, String designation, String salary, String createdAt, String updatedAt) {
    EmployeeResponse(Employee employee) {
        this(employee.getId().id(), employee.getName(), employee.getPosition(), employee.getPosition(), mapSalaryToString(employee.getSalary()), employee.getCreatedAt().toString(), employee.getUpdatedAt() != null ? employee.getUpdatedAt().toString() : null);
    }

    private static String mapSalaryToString(Salary salary) {
        if (salary == null) {
            return null;
        }
        return salary.components().stream().map(Object::toString).collect(Collectors.joining("\n"));
    }
}
