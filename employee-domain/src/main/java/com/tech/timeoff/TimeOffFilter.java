package com.tech.timeoff;

import com.tech.employee.domain.EmployeeId;

import java.util.Optional;

public record TimeOffFilter(EmployeeId employeeId) {
    public Optional<EmployeeId> getEmployeeId() {
        return Optional.ofNullable(employeeId);
    }
}
