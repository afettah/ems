package com.tech.timeoff;

import com.tech.employee.domain.EmployeeId;
import com.tech.timeoff.category.TimeOffCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@AllArgsConstructor
@Getter
@ToString
public class TimeOff {
    private final TimeOffId id;
    private final EmployeeId employeeId;
    private final TimeOffCategory category;
    private final DateRange dateRange;
    private final String comment;
    private TimeOffStatus status;

    public static TimeOff create(TimeOffRequest timeOffRequest, TimeOffCategory category) {
        Objects.requireNonNull(timeOffRequest, "Time off request cannot be null");

        if (timeOffRequest.employeeId() == null) {
            throw new IllegalArgumentException("employeeId cannot be null");
        }
        if (timeOffRequest.categoryId() == null) {
            throw new IllegalArgumentException("categoryId cannot be null");
        }
        if (timeOffRequest.dateRange() == null) {
            throw new IllegalArgumentException("dateRange cannot be null");
        }

        return new TimeOff(TimeOffId.generate(), timeOffRequest.employeeId(), category, timeOffRequest.dateRange(), timeOffRequest.comment(), TimeOffStatus.REQUESTED);
    }

    public boolean isAutoCancellable() {
        return category.autoCancellable();
    }

    public enum TimeOffStatus {
        REQUESTED,
        CANCELLED
    }

}
