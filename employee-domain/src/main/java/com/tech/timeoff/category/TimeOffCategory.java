package com.tech.timeoff.category;

public record TimeOffCategory(CategoryId id, String name, String description, boolean paid) {
    public TimeOffCategory {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be null or blank");
        }
    }

    public static TimeOffCategory create(String name, String description, boolean paid) {
        return new TimeOffCategory(CategoryId.generate(), name, description, paid);
    }
}
