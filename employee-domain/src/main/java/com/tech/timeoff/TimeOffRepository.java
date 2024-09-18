package com.tech.timeoff;

import java.util.List;

public interface TimeOffRepository {
    void create(TimeOff expectedTimeOff);

    List<TimeOff> findOverlappingDateRange(DateRange dateRange);
}
