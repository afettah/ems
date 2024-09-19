package com.tech.ems.timeoff.infrastructure;

import com.tech.ems.employee.EmployeeRepository;
import com.tech.ems.timeoff.TimeOffRepository;
import com.tech.ems.timeoff.TimeOffService;
import com.tech.ems.timeoff.category.TimeOffCategoryRepository;
import com.tech.ems.timeoff.category.TimeOffCategoryService;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class TimeOffConfiguration {

    @Bean
    public TimeOffService timeOffService(TimeOffRepository timeOffRepository,
                                         TimeOffCategoryRepository timeOffCategoryRepository,
                                         EmployeeRepository employeeRepository) {
        return new TimeOffService(timeOffRepository, timeOffCategoryRepository, employeeRepository);
    }

    @Bean
    public TimeOffCategoryService timeOffCategoryService(TimeOffCategoryRepository timeOffCategoryRepository) {
        return new TimeOffCategoryService(timeOffCategoryRepository);
    }

    @Bean
    public TimeOffRepository timeOffRepository(DSLContext dslContext, TimeOffCategoryRepository timeOffCategoryRepository) {
        return new TimeOffJooqRepositoryImpl(dslContext, timeOffCategoryRepository);
    }

    @Bean
    public TimeOffCategoryRepository timeOffCategoryRepository(DSLContext dslContext) {
        return new TimeOffCategoryJooqRepositoryImpl(dslContext);
    }
}
