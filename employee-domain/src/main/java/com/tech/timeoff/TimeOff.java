package com.tech.timeoff;

import com.tech.employee.domain.EmployeeId;
import com.tech.timeoff.category.CategoryId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class TimeOff {
    private final TimeOffId id;
    private final EmployeeId employeeId;
    private final CategoryId categoryId;
    private final DateRange dateRange;
    private final String comment;
    private TimeOffStatus status;

    public static TimeOff create(TimeOffRequest timeOffRequest) {
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
        if (timeOffRequest.comment() == null) {
            throw new IllegalArgumentException("comment cannot be null");
        }

        return new TimeOff(TimeOffId.generate(), timeOffRequest.employeeId(), timeOffRequest.categoryId(), timeOffRequest.dateRange(), timeOffRequest.comment(), TimeOffStatus.REQUESTED);
    }

    enum TimeOffStatus {
        REQUESTED
    }

}
