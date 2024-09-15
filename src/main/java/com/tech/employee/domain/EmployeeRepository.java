package com.tech.employee.domain;

import java.util.List;

public interface EmployeeRepository {
    void create(Employee employee);
    void update(Employee employee);
    Employee getById(EmployeeId id);
    List<Employee> findAll();
}
