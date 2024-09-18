package com.tech.timeoff;

import com.tech.employee.domain.EmployeeId;
import com.tech.timeoff.category.CategoryId;
import com.tech.timeoff.category.TimeOffCategory;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
class TimeOffFixtures {
    static DateRange oneDayRange() {
        return new DateRange(LocalDate.now(), LocalDate.now().plusDays(1));
    }

    static TimeOffRequest timeOffRequest() {
        EmployeeId employeeId = EmployeeId.generate();
        CategoryId categoryId = CategoryId.generate();
        DateRange range = new DateRange(LocalDate.now(), LocalDate.now().plusDays(1));
        String comment = "Time off request comment";
        return new TimeOffRequest(employeeId, categoryId, range, comment);
    }

    public static TimeOffCategory category(boolean autoCancel) {
        return new TimeOffCategory(CategoryId.generate(), "Category name", "Category description", true, autoCancel);
    }

    public static TimeOff timeOff(DateRange dateRange) {
        return new TimeOff(TimeOffId.generate(), EmployeeId.generate(), category(false), dateRange, "Time off comment", TimeOff.TimeOffStatus.REQUESTED);
    }

    public static TimeOff autoCancellable(DateRange dateRange) {
        return new TimeOff(TimeOffId.generate(), EmployeeId.generate(), category(true), dateRange, "Auto cancellable time off comment", TimeOff.TimeOffStatus.REQUESTED);
    }
}
