package com.tech.ems.employee.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tech.ems.employee.EmployeeRepository;
import com.tech.ems.employee.EmployeeService;
import com.tech.ems.employee.position.PositionRepository;
import com.tech.ems.shared.infrastructure.HistoryJooqRepository;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class EmployeeConfiguration {
    @Bean
    public EmployeeService employeeService(EmployeeRepository employeeRepository) {
        return new EmployeeService(employeeRepository);
    }

    @Bean
    public EmployeeRepository employeeRepository(DSLContext dslContext, HistoryJooqRepository historyRepository, PositionRepository positionRepository) {
        return new EmployeeJooqRepositoryImpl(dslContext, historyRepository, positionRepository);
    }

    @Bean
    public PositionRepository positionRepository(DSLContext dslContext) {
        return new PositionJooqRepositoryImpl(dslContext);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }
}
