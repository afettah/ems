package com.tech.timeoff;

import com.tech.employee.domain.EmployeeId;

import java.util.List;

public interface TimeOffRepository {
    void create(TimeOff expectedTimeOff);

    List<TimeOff> findOverlappingDateRange(EmployeeId employeeId, DateRange dateRange);

    void cancel(TimeOffId id);

    List<TimeOff> findAll(TimeOffFilter timeOffFilter);
}
