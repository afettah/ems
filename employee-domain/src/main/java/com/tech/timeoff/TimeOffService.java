package com.tech.timeoff;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TimeOffService {
    private final TimeOffRepository timeOffRepository;

    public TimeOff request(TimeOffRequest timeOffRequest) {
        TimeOff timeOff = TimeOff.create(timeOffRequest);
        timeOffRepository.create(timeOff);
        return timeOff;
    }
}
