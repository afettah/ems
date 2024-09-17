package com.tech.employee;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.shell.test.autoconfigure.ShellAutoConfiguration;

@SpringBootTest
@ImportAutoConfiguration(exclude = ShellAutoConfiguration.class)
@Import(TestcontainersConfiguration.class)
public abstract class AbstractITest {

    @Autowired
    private DatabaseCleaner cleaner;

    @BeforeEach
    void setUp() {
        cleaner.clearDatabase();
    }
}
