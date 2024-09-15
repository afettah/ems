package com.tech.employee.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository repository;

    public void create(Employee employee) {
        repository.create(employee);
    }
}
