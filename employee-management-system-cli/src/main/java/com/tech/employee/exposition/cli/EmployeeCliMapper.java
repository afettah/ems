package com.tech.employee.exposition.cli;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.employee.domain.Employee;
import com.tech.employee.domain.EmployeeId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
class EmployeeCliMapper {
    private final ObjectMapper objectMapper;

    EmployeeCliMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public EmployeeResponse mapToResponse(Employee employee) {
        return new EmployeeResponse(employee);
    }

    public String mapToTableString(List<Employee> employees) {
        List<Map<String, Object>> employeeList = new ArrayList<>();
        for (Employee employee : employees) {
            employeeList.add(objectMapper.convertValue(mapToResponse(employee), new TypeReference<>() {
            }));
        }
        return TableDisplayUtils.displayTable(employeeList, "id", "email", "name", "position", "salary", "createdAt", "updatedAt");
    }

    public EmployeeId mapToEmployeeId(String id) {
        return EmployeeId.fromString(id);
    }
}
