package com.tech.employee;


import com.tech.employee.employee.App;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    void should_create_instance_of_app() {
        App app = new App();
        assertThat(app).isNotNull();
    }
}
