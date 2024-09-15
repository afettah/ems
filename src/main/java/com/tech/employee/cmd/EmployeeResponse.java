package com.tech.employee.cmd;

import com.tech.employee.domain.Employee;

record EmployeeResponse(String id, String name, String department, String designation, String salary, String createdAt, String updatedAt) {
    EmployeeResponse(Employee employee) {
        this(employee.getId().id(), employee.getName(), employee.getPosition(), employee.getPosition(), employee.getMonthlySalary().amount() + " " + employee.getMonthlySalary().currency().getSymbol(), employee.getCreatedAt().toString(), employee.getUpdatedAt() != null ? employee.getUpdatedAt().toString() : null);
    }
}
