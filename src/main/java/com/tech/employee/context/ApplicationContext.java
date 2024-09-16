package com.tech.employee.context;

import com.tech.employee.databse.DatabaseConfiguration;
import com.tech.employee.domain.EmployeeRepository;
import com.tech.employee.domain.EmployeeService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple application context implementation
 * <p>
 *     This class is responsible for managing beans in the application context.
 *     It provides a way to register beans and get them by type.
 *
 * <p>
 *     Currently, we don't use external libraries for dependency injection, so we have to manage it manually.
 */
public class ApplicationContext {
    private static final ApplicationContext INSTANCE = new ApplicationContext();

    private ApplicationContext() {
    }

    public static ApplicationContext getInstance() {
        return INSTANCE;
    }

    private final Map<String, Object> context = new ConcurrentHashMap<>();

    public void registerBean(String beanName, Object bean) {
        context.compute(beanName, (key, value) -> {
            if (value != null) {
                throw new IllegalStateException("Bean with name " + beanName + " already exists");
            }
            return bean;
        });
    }

    public <T> T getFirstBean(Class<T> tClass) {
        return context.values().stream()
                .filter(tClass::isInstance)
                .map(tClass::cast)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Bean of type " + tClass.getName() + " not found"));
    }

    public void registerBeans() {
        DatabaseConfiguration.initializeContext(this);
        registerBean("employeeService", employeeService(this));
    }


    EmployeeService employeeService(ApplicationContext context) {
        return new EmployeeService(context.getFirstBean(EmployeeRepository.class));
    }


}
