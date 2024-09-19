package com.tech.ems.employee.exposition.cli;

import com.tech.ems.employee.Employee;
import com.tech.ems.employee.salary.Salary;
import com.tech.ems.shared.user.UserContext;

import java.time.Instant;
import java.util.stream.Collectors;

record EmployeeResponse(String id, String email, String name, String position, String salary, String createdAt, String updatedAt) {
    EmployeeResponse(Employee employee) {
        this(employee.getId().id(), employee.getEmail(), employee.getName(), employee.getPosition(), mapSalaryToString(employee.getSalary()), mapToUserDateTime(employee.getCreatedAt()), mapToUserDateTime(employee.getUpdatedAt()));
    }

    private static String mapToUserDateTime(Instant instant) {
        if (instant == null) {
            return null;
        }
        return instant.atZone(UserContext.getOrSetNew().getZoneId()).toString();
    }

    private static String mapSalaryToString(Salary salary) {
        if (salary == null) {
            return null;
        }
        return salary.components().stream().map(Object::toString).collect(Collectors.joining("\n"));
    }
}
