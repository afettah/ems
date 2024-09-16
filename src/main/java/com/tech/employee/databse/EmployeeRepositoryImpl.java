package com.tech.employee.databse;

import com.tech.employee.domain.Employee;
import com.tech.employee.domain.EmployeeId;
import com.tech.employee.domain.EmployeeRepository;

import java.util.List;

class EmployeeRepositoryImpl implements EmployeeRepository {
    private final EmployeeDatabase database;

    EmployeeRepositoryImpl(EmployeeDatabase database) {
        this.database = database;
    }

    @Override
    public void create(Employee employee) {
        database.create(mapToRow(employee));
    }

    @Override
    public void update(Employee employee) {
        database.update(mapToRow(employee));
    }

    @Override
    public Employee getById(EmployeeId id) {
        return database.findById(id.id())
                .map(this::mapToEmployee)
                .orElseThrow(() -> new DataNotFoundException("id", id.id(), "employee"));
    }

    @Override
    public List<Employee> findAll() {
        return database.findAll().map(this::mapToEmployee).toList();
    }

    private EmployeeRow mapToRow(Employee employee) {
        return EmployeeRow.builder().id(employee.getId().id())
                .email(employee.getEmail())
                .name(employee.getName())
                .position(employee.getPosition())
                .salary(employee.getSalary())
                .createdAt(employee.getCreatedAt())
                .updatedAt(employee.getUpdatedAt())
                .build();
    }

    private Employee mapToEmployee(EmployeeRow row) {
        return new Employee(EmployeeId.fromString(row.getId()), row.getEmail(), row.getName(), row.getPosition(),
                row.getSalary(), row.getCreatedAt(), row.getUpdatedAt());
    }
}
