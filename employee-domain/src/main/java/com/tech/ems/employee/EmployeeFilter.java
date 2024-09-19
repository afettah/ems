package com.tech.ems.employee;

import java.util.Optional;

public record EmployeeFilter(EmployeeId id, String name, String email, String position) {

    public Optional<EmployeeId> getId() {
        return Optional.ofNullable(id);
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }

    public Optional<String> getPosition() {
        return Optional.ofNullable(position);
    }
}
