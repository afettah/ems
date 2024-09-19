package com.tech.ems.employee;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.shell.test.autoconfigure.ShellAutoConfiguration;

@SpringBootTest
@ImportAutoConfiguration(exclude = ShellAutoConfiguration.class)
@Import(TestcontainersConfiguration.class)
@Slf4j
public abstract class AbstractITest {

    @Autowired
    private DatabaseCleaner cleaner;

    @BeforeEach
    public void clearDatabase() {
        cleaner.clearDatabase();
        log.info("Cleaning database");
    }
}
