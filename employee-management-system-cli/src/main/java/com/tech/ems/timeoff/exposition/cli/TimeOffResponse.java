package com.tech.ems.timeoff.exposition.cli;

import com.tech.ems.timeoff.TimeOff;

record TimeOffResponse(String id, String employeeId, String category, String startDate, String endDate, String status) {
    TimeOffResponse(TimeOff timeOff) {
        this(timeOff.getId().id(), timeOff.getEmployeeId().id(), timeOff.getCategory().name(), timeOff.getDateRange().start().toString(), timeOff.getDateRange().end().toString(), timeOff.getStatus().name());
    }
}
