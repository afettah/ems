package com.tech.ems.shared.infrastructure;

import com.tech.ems.i18n.I18nMessageRepository;
import com.tech.ems.i18n.I18nMessageService;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class I18nConfiguration {

    @Bean
    I18nMessageRepository i18nMessageRepository(DSLContext dslContext) {
        return new I18nMessageRepositoryJooqImpl(dslContext);
    }

    @Bean
    I18nMessageService i18nMessageService(I18nMessageRepository i18nMessageRepository) {
        return new I18nMessageService(i18nMessageRepository);
    }
}
