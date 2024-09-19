package com.tech.timeoff;

import com.tech.employee.domain.EmployeeRepository;
import com.tech.timeoff.category.TimeOffCategory;
import com.tech.timeoff.category.TimeOffCategoryRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TimeOffService {
    private final TimeOffRepository timeOffRepository;
    private final TimeOffCategoryRepository timeOffCategoryRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public TimeOff request(TimeOffRequest timeOffRequest) {
        TimeOffCategory category = timeOffCategoryRepository.findById(timeOffRequest.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(timeOffRequest.categoryId().toString()));

        employeeRepository.findById(timeOffRequest.employeeId())
                .orElseThrow(() -> new EmployeeNotFoundException(timeOffRequest.employeeId().toString()));

        List<TimeOff> timeOffList = timeOffRepository.findOverlappingDateRange(timeOffRequest.employeeId(), timeOffRequest.dateRange());
        List<TimeOff> notCancellable = timeOffList.stream()
                .filter(timeOff -> !timeOff.isAutoCancellable())
                .toList();
        if (!notCancellable.isEmpty()) {
            throw new OverlappingTimeOffException(timeOffRequest.dateRange(), notCancellable);
        }
        TimeOff timeOff = TimeOff.create(timeOffRequest, category);
        timeOffRepository.create(timeOff);

        if (!timeOffList.isEmpty()) {
            cancel(timeOffList);
        }
        return timeOff;
    }

    private void cancel(List<TimeOff> timeOffList) {
        for (TimeOff timeOff : timeOffList) {
            cancel(timeOff);
        }
    }

    private void cancel(TimeOff timeOff) {
        timeOffRepository.cancel(timeOff.getId());
    }

    public List<TimeOff> findAll(TimeOffFilter timeOffFilter) {
        return timeOffRepository.findAll(timeOffFilter);
    }
}
