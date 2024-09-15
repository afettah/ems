package com.tech.employee.domain;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeIdTest {

    @Test
    void create_EmployeeId_with_null_id_should_throw_exception() {
        assertThrows(IllegalArgumentException.class, () -> new EmployeeId(null));
    }

    @Test
    void generate_should_return__new_employee_id() {
        EmployeeId employeeId = EmployeeId.generate();
        assertNotNull(employeeId);
        assertThat(employeeId.uuid()).isNotNull();
    }

    @Test
    void generate_should_generate_different_employee_ids() {
        int expected = 1000;
        Set<EmployeeId> employeeIds = new HashSet<>();
        Set<String> ids = new HashSet<>();

        for (int i = 0; i < expected; i++) {
            EmployeeId employeeId = EmployeeId.generate();
            employeeIds.add(employeeId);
            ids.add(employeeId.id());
        }

        assertThat(employeeIds).hasSize(expected);
        assertThat(ids).hasSize(expected);
    }

    @Test
    void id_should_return_string_representation_of_employee_id() {
        UUID uuid = UUID.randomUUID();
        EmployeeId employeeId = new EmployeeId(uuid);
        assertThat(employeeId.id()).isEqualTo(uuid.toString());
    }

    @Test
    void fromString_should_return_employee_id_from_string_uuid() {
        String id = "123e4567-e89b-12d3-a456-426614174000";
        EmployeeId employeeId = EmployeeId.fromString(id);
        assertThat(employeeId.id()).isEqualTo(id);
    }
}
