package com.tech.employee.cmd;

import com.tech.employee.domain.Employee;

class EmployeeDomainMapper {
    public EmployeeResponse mapToResponse(Employee employee) {
        return new EmployeeResponse(employee);
    }
}
