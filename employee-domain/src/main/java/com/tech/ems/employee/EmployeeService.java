package com.tech.ems.employee;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Transactional
public class EmployeeService {
    private final EmployeeRepository repository;

    public void create(EmployeeCreateCommand employee) {
        repository.create(Employee.create(employee));
    }

    public void update(EmployeeId employeeId, EmployeeUpdateCommand employeeCommand) {
        Employee employee = repository.getById(employeeId);
        employee.update(employeeCommand);
        repository.update(employee);
    }

    public List<Employee> findAll() {
        return repository.findAll();
    }

    public List<Employee> findAll(EmployeeFilter employeeFilter) {
        return repository.findAll(employeeFilter);
    }

    public void delete(EmployeeId employeeId) {
        repository.delete(employeeId);
    }


}
