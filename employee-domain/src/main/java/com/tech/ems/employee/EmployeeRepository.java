package com.tech.ems.employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    void create(Employee employee);
    void update(Employee employee);
    Employee getById(EmployeeId id);
    Optional<Employee> findById(EmployeeId id);
    List<Employee> findAll();
    List<Employee> findAll(EmployeeFilter filter);
    void delete(EmployeeId employeeId);

}
