package com.tech.timeoff;

import com.tech.employee.domain.EmployeeId;
import com.tech.timeoff.category.CategoryId;

public record TimeOffRequest(EmployeeId employeeId, CategoryId categoryId, DateRange dateRange, String comment) {
}
