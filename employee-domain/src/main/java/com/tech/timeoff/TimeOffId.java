package com.tech.timeoff;

import java.util.UUID;

public record TimeOffId(UUID uuid) {

    public TimeOffId {
        if (uuid == null) {
            throw new IllegalArgumentException("uuid cannot be null");
        }
    }

    public static TimeOffId generate() {
        return new TimeOffId(UUID.randomUUID());
    }

    public static TimeOffId fromString(String uuid) {
        return new TimeOffId(UUID.fromString(uuid));
    }

    public String id() {
        return uuid.toString();
    }
}
