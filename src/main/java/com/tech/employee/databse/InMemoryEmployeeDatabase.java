package com.tech.employee.databse;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class InMemoryEmployeeDatabase implements EmployeeDatabase {

    private final Map<String, EmployeeRow> employeeRows = new ConcurrentHashMap<>();

    @Override
    public void create(EmployeeRow employeeRow) {
        String id = employeeRow.getId();
        employeeRows.compute(id, (key, value) -> {
            if (value != null) {
                throw new DuplicatedKeyException("id", id, "employee");
            }
            return employeeRow;
        });
    }

    @Override
    public void update(EmployeeRow employeeRow) {
        String id = employeeRow.getId();
        employeeRows.compute(id, (key, value) -> {
            if (value == null) {
                throw new DataNotFoundException("id", id, "employee");
            }
            return employeeRow;
        });
    }

    @Override
    public Optional<EmployeeRow> findById(String id) {
        return Optional.ofNullable(employeeRows.get(id));
    }

    @Override
    public Stream<EmployeeRow> findAll() {
        return employeeRows.values().stream();
    }
}
