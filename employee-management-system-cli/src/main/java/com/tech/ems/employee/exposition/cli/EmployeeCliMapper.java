package com.tech.ems.employee.exposition.cli;

import com.tech.ems.employee.Employee;
import com.tech.ems.employee.EmployeeId;
import com.tech.ems.employee.salary.Salary;
import com.tech.ems.i18n.I18nMessageService;
import com.tech.ems.shared.cli.TableDisplayMapper;
import com.tech.ems.shared.user.UserContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
class EmployeeCliMapper {
    private final TableDisplayMapper tableDisplayMapper;
    private final I18nMessageService i18nMessageService;

    private static String mapToUserDateTime(Instant instant) {
        if (instant == null) {
            return null;
        }
        return instant.atZone(UserContext.getOrSetNew().getZoneId()).toString();
    }

    public String mapToTableString(List<Employee> employees) {
        return tableDisplayMapper.display(employees.stream().map(this::mapToResponse).toList(), "id", "email", "name", "position", "salary", "createdAt", "updatedAt");
    }

    public EmployeeId mapToEmployeeId(String id) {
        return EmployeeId.fromString(id);
    }

    private static String mapSalaryToString(Salary salary) {
        if (salary == null) {
            return null;
        }
        return salary.components().stream().map(Object::toString).collect(Collectors.joining("\n"));
    }

    public EmployeeResponse mapToResponse(Employee employee) {
        Locale locale = getUserLocal();
        String position = i18nMessageService.getMessage(translationKey("position", employee.getPosition().code(), "name"), locale)
                .orElse(employee.getPosition().name());
        return new EmployeeResponse(employee.getId().id(), employee.getEmail(), employee.getName(), position, mapSalaryToString(employee.getSalary()), mapToUserDateTime(employee.getCreatedAt()), mapToUserDateTime(employee.getUpdatedAt()));
    }

    private String translationKey(String entity, String code, String property) {
        return entity + "." + code + "." + property;
    }

    private Locale getUserLocal() {
        return UserContext.getOrSetNew().getLocale();
    }
}
