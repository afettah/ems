package com.tech.ems.employee.exposition.cli;

import com.tech.ems.employee.Employee;
import com.tech.ems.employee.EmployeeId;
import com.tech.ems.shared.cli.TableDisplayMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
class EmployeeCliMapper {
    private final TableDisplayMapper tableDisplayMapper;

    public EmployeeResponse mapToResponse(Employee employee) {
        return new EmployeeResponse(employee);
    }

    public String mapToTableString(List<Employee> employees) {
        return tableDisplayMapper.display(employees.stream().map(this::mapToResponse).toList(), "id", "email", "name", "position", "salary", "createdAt", "updatedAt");
    }

    public EmployeeId mapToEmployeeId(String id) {
        return EmployeeId.fromString(id);
    }
}
