package com.tech.employee.databse;

import com.tech.employee.context.ApplicationContext;
import com.tech.employee.domain.EmployeeRepository;

public class DatabaseConfiguration {

    private DatabaseConfiguration() {
    }

    public static void initializeContext(ApplicationContext context) {
        context.registerBean("employeeDatabase", employeeDatabase());
        context.registerBean("employeeRepository", employeeRepository(context));
    }

    static EmployeeRepository employeeRepository(ApplicationContext context) {
        return new EmployeeRepositoryImpl(context.getFirstBean(EmployeeDatabase.class));
    }

    static EmployeeDatabase employeeDatabase() {
        return new InMemoryEmployeeDatabase();
    }
}
