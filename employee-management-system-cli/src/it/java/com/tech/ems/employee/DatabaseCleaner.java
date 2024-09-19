package com.tech.ems.employee;

import com.tech.ems.jooq.generated.JEms;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DatabaseCleaner {

    private final DSLContext dslContext;

    public DatabaseCleaner(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public void clearDatabase() {
        JEms.EMS.tableStream()
                .forEach(table -> dslContext.truncate(table).cascade().execute());
        log.info("Database has been cleaned");
    }
}
