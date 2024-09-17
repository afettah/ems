package com.tech.employee.exposition.cli;

import com.tech.employee.domain.*;
import com.tech.employee.domain.salary.Money;
import com.tech.employee.domain.salary.Salary;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@ShellComponent
@AllArgsConstructor
@Slf4j
class EmployeeManagementCommands {
    private final EmployeeService employeeService;
    private final EmployeeCliMapper employeeCliMapper;

    @ShellMethod(key = "create", value = "Create a new employee")
    public String createEmployee(@ShellOption(help = "employee email") String email, @ShellOption(help = "employee name") String name, @ShellOption(help = "employee position") String position, @ShellOption(help = "employee monthly base salary in euro") double salary) {
        try {
            var employeeCreateCommand = new EmployeeCreateCommand(email, name, position, Salary.fixedMonthlySalary(Money.euro(salary)));
            employeeService.create(employeeCreateCommand);
            return "Employee created successfully.";
        } catch (Exception e) {
            return "Error creating employee: " + e.getMessage();
        }
    }

    @ShellMethod(key = "list", value = "List all employees")
    public String listAllEmployees() {
        List<Employee> employees = employeeService.findAll();
        if (employees.isEmpty()) {
            return "No employees found.";
        }
        return employeeCliMapper.mapToTableString(employees);
    }

    @ShellMethod(key = "find", value = "Find an employee")
    public String findEmployee(
            @ShellOption(defaultValue = ShellOption.NULL) String id,
            @ShellOption(defaultValue = ShellOption.NULL) String name,
            @ShellOption(defaultValue = ShellOption.NULL) String email,
            @ShellOption(defaultValue = ShellOption.NULL) String position) {
        List<Employee> employees = employeeService.findAll(new EmployeeFilter(id != null ? employeeCliMapper.mapToEmployeeId(id) : null, name, email, position));
        if (employees.isEmpty()) {
            return "No employees found.";
        }
        return employeeCliMapper.mapToTableString(employees);
    }

    @ShellMethod(key = "delete", value = "Delete an employee by ID")
    public String deleteEmployee(String id) {
        try {
            employeeService.delete(employeeCliMapper.mapToEmployeeId(id));
            return "Employee deleted successfully.";
        } catch (Exception e) {
            return "Error deleting employee: " + e.getMessage();
        }
    }

    @ShellMethod(key = "update", value = "Update an employee's details")
    public String updateEmployee(String id, String position, Double salary) {
        try {
            employeeService.update(employeeCliMapper.mapToEmployeeId(id), new EmployeeUpdateCommand(position, salary != null ? Salary.fixedMonthlySalary(Money.euro(salary)) : null));
            return "Employee updated successfully.";
        } catch (Exception e) {
            return "Error updating employee: " + e.getMessage();
        }
    }

    @ShellMethod(key = "generate", value = "Generate a number of employees. Usage: generate <count> <threadCount>")
    public String generateEmployees(@ShellOption(help = "employee count") Integer employeeCount, @ShellOption(defaultValue = "1", help = "threadCount") Integer threadCountValue) throws InterruptedException {

        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger errorCount = new AtomicInteger();
        try (ExecutorService executorService = Executors.newFixedThreadPool(threadCountValue)) {
            for (int i = 0; i < employeeCount; i++) {
                int employeeNumber = i + 1;
                executorService.submit(() -> {
                    String email = "employee" + employeeNumber + "@domain.com";
                    String name = "Employee " + employeeNumber;
                    String position = "Position " + employeeNumber;
                    double salary = 1000d + (employeeNumber * 100);
                    try {
                        var employeeCreateCommand = new EmployeeCreateCommand(email, name, position, Salary.fixedMonthlySalary(Money.euro(salary)));
                        employeeService.create(employeeCreateCommand);
                        successCount.incrementAndGet();
                    } catch (Exception e) {
                        log.error("Error creating employee: " + e.getMessage());
                        errorCount.incrementAndGet();
                    }
                });
            }

            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        }
        return "Employees generated successfully. Success: " + successCount.get() + ", Errors: " + errorCount.get();
    }

}

