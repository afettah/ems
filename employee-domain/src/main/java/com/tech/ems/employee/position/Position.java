package com.tech.ems.employee.position;

import java.util.Objects;

public record Position(String code, String name) {

    public Position {
        Objects.requireNonNull(code, "Position code must not be null");
        Objects.requireNonNull(name, "Position name must not be null");
    }
}
