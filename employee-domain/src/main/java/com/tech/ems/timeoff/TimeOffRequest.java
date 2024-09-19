package com.tech.ems.timeoff;

import com.tech.ems.employee.EmployeeId;
import com.tech.ems.timeoff.category.CategoryId;

public record TimeOffRequest(EmployeeId employeeId, CategoryId categoryId, DateRange dateRange, String comment) {
}
