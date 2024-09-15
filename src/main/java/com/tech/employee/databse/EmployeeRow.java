package com.tech.employee.databse;

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
    private double salary;
    private String currency;
    private Instant createdAt;
    private Instant updatedAt;
}
