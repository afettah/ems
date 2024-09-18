package com.tech.employee.exposition.cli;

import com.tech.employee.domain.Employee;
import com.tech.employee.domain.EmployeeId;
import com.tech.shared.cli.TableDisplayMapper;
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
