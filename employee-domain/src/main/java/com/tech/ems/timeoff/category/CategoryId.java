package com.tech.ems.timeoff.category;

import java.util.UUID;

public record CategoryId(UUID uuid) {

    public CategoryId {
        if (uuid == null) {
            throw new IllegalArgumentException("uuid cannot be null");
        }
    }

    public static CategoryId generate() {
        return new CategoryId(UUID.randomUUID());
    }

    public String id() {
        return uuid.toString();
    }
}
