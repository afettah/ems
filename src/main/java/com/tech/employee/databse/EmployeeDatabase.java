package com.tech.employee.databse;

import java.util.Optional;
import java.util.stream.Stream;

interface EmployeeDatabase {

    void create(EmployeeRow employeeRow);

    void update(EmployeeRow employeeRow);

    Optional<EmployeeRow> findById(String id);

    Stream<EmployeeRow> findAll();
}
