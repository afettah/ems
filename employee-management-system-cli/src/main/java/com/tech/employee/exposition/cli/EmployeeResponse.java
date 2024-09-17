package com.tech.employee.exposition.cli;

import com.tech.employee.domain.Employee;
import com.tech.employee.domain.salary.Salary;

import java.util.stream.Collectors;

record EmployeeResponse(String id, String email, String name, String position, String salary, String createdAt, String updatedAt) {
    EmployeeResponse(Employee employee) {
        this(employee.getId().id(), employee.getEmail(), employee.getName(), employee.getPosition(), mapSalaryToString(employee.getSalary()), employee.getCreatedAt().toString(), employee.getUpdatedAt() != null ? employee.getUpdatedAt().toString() : null);
    }

    private static String mapSalaryToString(Salary salary) {
        if (salary == null) {
            return null;
        }
        return salary.components().stream().map(Object::toString).collect(Collectors.joining("\n"));
    }
}
