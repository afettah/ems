package com.tech.timeoff;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DateRangeTest {

    @Test
    void create_date_range_with_valid_dates_should_return_DateRange() {
        LocalDate start = LocalDate.of(2023, 9, 18);
        LocalDate end = LocalDate.of(2023, 9, 19);

        DateRange dateRange = new DateRange(start, end);

        assertThat(dateRange.start()).isEqualTo(start);
        assertThat(dateRange.end()).isEqualTo(end);
    }

    @Test
    void create_date_range_with_start_after_end_should_throw_exception() {
        LocalDate start = LocalDate.of(2023, 9, 20);
        LocalDate end = LocalDate.of(2023, 9, 19);

        assertThrows(IllegalArgumentException.class, () -> new DateRange(start, end));
    }

    @Test
    void create_date_range_with_null_start_should_throw_exception() {
        LocalDate end = LocalDate.of(2023, 9, 19);

        assertThrows(NullPointerException.class, () -> new DateRange(null, end));
    }

    @Test
    void create_date_range_with_null_end_should_throw_exception() {
        LocalDate start = LocalDate.of(2023, 9, 18);

        assertThrows(NullPointerException.class, () -> new DateRange(start, null));
    }
}

