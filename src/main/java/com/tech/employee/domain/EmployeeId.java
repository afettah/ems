package com.tech.employee.domain;

import java.util.UUID;

public record EmployeeId(UUID uuid) {

    public EmployeeId {
        if (uuid == null) {
            throw new IllegalArgumentException("uuid cannot be null");
        }
    }

    public static EmployeeId generate() {
        return new EmployeeId(UUID.randomUUID());
    }

    public static EmployeeId fromString(String uuid) {
        return new EmployeeId(UUID.fromString(uuid));
    }

    public String id() {
        return uuid.toString();
    }
}
