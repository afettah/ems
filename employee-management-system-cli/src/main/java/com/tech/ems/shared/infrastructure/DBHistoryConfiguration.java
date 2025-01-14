package com.tech.ems.shared.infrastructure;

import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class DBHistoryConfiguration {
    @Bean
    public HistoryJooqRepository historyRepository(DSLContext dslContext) {
        return new HistoryJooqRepository(dslContext);
    }
}
