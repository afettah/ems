package com.tech.ems.timeoff;

import lombok.Getter;

import java.util.List;

@Getter
public class OverlappingTimeOffException extends RuntimeException {
    private final DateRange dateRange;
    private final List<TimeOff> timeOffList;

    public OverlappingTimeOffException(DateRange dateRange, List<TimeOff> timeOffList) {
        super("Time off request for date range " + dateRange + " overlaps with existing time off entries: " + timeOffList);
        this.dateRange = dateRange;
        this.timeOffList = timeOffList != null ? List.copyOf(timeOffList) : List.of();
    }
}
