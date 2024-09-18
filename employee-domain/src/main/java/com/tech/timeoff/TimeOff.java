package com.tech.timeoff;

import com.tech.employee.domain.EmployeeId;
import com.tech.timeoff.category.CategoryId;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
        return new TimeOff(TimeOffId.generate(), timeOffRequest.employeeId(), timeOffRequest.categoryId(), timeOffRequest.dateRange(), timeOffRequest.comment(), TimeOffStatus.REQUESTED);
    }

    enum TimeOffStatus {
        REQUESTED
    }

}
