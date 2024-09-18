package com.tech.timeoff.infrastructure;

import com.tech.employee.domain.EmployeeRepository;
import com.tech.timeoff.TimeOffRepository;
import com.tech.timeoff.TimeOffService;
import com.tech.timeoff.category.TimeOffCategoryRepository;
import com.tech.timeoff.category.TimeOffCategoryService;
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
    public TimeOffRepository timeOffRepository(DSLContext dslContext) {
        return new TimeOffJooqRepositoryImpl(dslContext);
    }

    @Bean
    public TimeOffCategoryRepository timeOffCategoryRepository(DSLContext dslContext) {
        return new TimeOffCategoryJooqRepositoryImpl(dslContext);
    }
}
