package com.tech.employee.domain;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository repository;

    public void create(Employee employee) {
        repository.create(employee);
    }

    public List<Employee> findAll() {
        return repository.findAll();
    }
}
