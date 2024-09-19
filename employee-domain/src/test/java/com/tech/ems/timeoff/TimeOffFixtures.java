package com.tech.ems.timeoff;

import com.tech.ems.employee.EmployeeId;
import com.tech.ems.timeoff.DateRange;
import com.tech.ems.timeoff.TimeOff;
import com.tech.ems.timeoff.TimeOffId;
import com.tech.ems.timeoff.TimeOffRequest;
import com.tech.ems.timeoff.category.CategoryId;
import com.tech.ems.timeoff.category.TimeOffCategory;
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
