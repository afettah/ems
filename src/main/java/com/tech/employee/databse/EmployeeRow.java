package com.tech.employee.databse;

import com.tech.employee.domain.salary.Salary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
class EmployeeRow {
    private final String id;
    private String email;
    private String name;
    private String phone;
    private String position;
    private Salary salary;
    private Instant createdAt;
    private Instant updatedAt;
}
