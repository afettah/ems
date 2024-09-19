package com.tech.ems.timeoff;

import com.tech.ems.employee.EmployeeId;

import java.util.Optional;

public record TimeOffFilter(EmployeeId employeeId) {
    public Optional<EmployeeId> getEmployeeId() {
        return Optional.ofNullable(employeeId);
    }
}
