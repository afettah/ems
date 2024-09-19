package com.tech.ems.timeoff;

import com.tech.ems.employee.EmployeeId;

import java.util.List;

public interface TimeOffRepository {
    void create(TimeOff expectedTimeOff);

    List<TimeOff> findOverlappingDateRange(EmployeeId employeeId, DateRange dateRange);

    void cancel(TimeOffId id);

    List<TimeOff> findAll(TimeOffFilter timeOffFilter);
}
