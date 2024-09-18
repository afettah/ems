package com.tech.timeoff;

import com.tech.employee.domain.EmployeeRepository;
import com.tech.timeoff.category.TimeOffCategoryRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TimeOffService {
    private final TimeOffRepository timeOffRepository;
    private final TimeOffCategoryRepository timeOffCategoryRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public TimeOff request(TimeOffRequest timeOffRequest) {
        timeOffCategoryRepository.findById(timeOffRequest.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(timeOffRequest.categoryId().toString()));

        employeeRepository.findById(timeOffRequest.employeeId())
                .orElseThrow(() -> new EmployeeNotFoundException(timeOffRequest.employeeId().toString()));

        TimeOff timeOff = TimeOff.create(timeOffRequest);
        timeOffRepository.create(timeOff);
        return timeOff;
    }
}
