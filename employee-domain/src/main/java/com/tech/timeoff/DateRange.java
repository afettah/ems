package com.tech.timeoff;

import java.time.LocalDate;
import java.util.Objects;

record DateRange(LocalDate start, LocalDate end) {
    public DateRange {
        Objects.requireNonNull(start, "Start date must not be null");
        Objects.requireNonNull(end, "End date must not be null");
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
    }
}
