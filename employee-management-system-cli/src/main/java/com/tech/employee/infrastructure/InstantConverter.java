package com.tech.employee.infrastructure;

import jakarta.validation.constraints.NotNull;
import org.jooq.Converter;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class InstantConverter implements Converter<OffsetDateTime, Instant> {

    @Override
    public Instant from(OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null) {
            return null;
        }
        return offsetDateTime.toInstant();
    }

    @Override
    public OffsetDateTime to(Instant instant) {
        if (instant == null) {
            return null;
        }
        return OffsetDateTime.ofInstant(instant, ZoneOffset.UTC);
    }

    @Override
    @NotNull
    public Class<OffsetDateTime> fromType() {
        return OffsetDateTime.class;
    }

    @Override
    @NotNull
    public Class<Instant> toType() {
        return Instant.class;
    }
}
