package com.tech.employee.cmd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tech.employee.context.ApplicationContext;
import com.tech.employee.domain.Employee;
import com.tech.employee.domain.EmployeeId;
import com.tech.employee.domain.EmployeeService;
import com.tech.employee.domain.Money;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.tech.employee.cmd.TableDisplayUtils.displayTable;

public class EmsCliApp {
    private final EmployeeService employeeService;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public EmsCliApp(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public static void main(String[] args) {
        ApplicationContext context = ApplicationContext.getInstance();
        context.registerBeans();
        var employeeService = context.getFirstBean(EmployeeService.class);
        EmsCliApp app = new EmsCliApp(employeeService);

        app.runInteractiveMenu();
    }

    // Interactive menu loop
    public void runInteractiveMenu() {
        Scanner scanner = new Scanner(System.in);
        String option = "";

        while (!option.equalsIgnoreCase("Q")) {
            displayMenu();
            option = scanner.nextLine();

            try {
                switch (option.toUpperCase()) {
                    case "C":
                        createEmployee(scanner);
                        break;
                    case "U":
                        updateEmployee(scanner);
                        break;
                    case "L":
                        listAllEmployees();
                        break;
                    case "D":
                        deleteEmployee(scanner);
                        break;
                    case "G":
                        generateEmployees(scanner);
                        break;
                    case "Q":
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid option, please try again.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                e.printStackTrace();
            }
            if (!option.equalsIgnoreCase("Q")) {
                waitForKeyPress();
            }
        }
        scanner.close();
    }

    // Display the command menu
    private void displayMenu() {
        System.out.println("\nEmployee Management System - Interactive Mode");
        System.out.println("C - Create Employee");
        System.out.println("U - Update Employee");
        System.out.println("L - List Employees");
        System.out.println("D - Delete Employee");
        System.out.println("G - Generate Employees");
        System.out.println("Q - Exit");
        System.out.print("Please select an option: ");
    }

    // Wait for key press before displaying menu again
    private void waitForKeyPress() {
        System.out.println("Press Enter to return to the menu...");
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // List all employees
    private void listAllEmployees() {
        List<Map<String, Object>> employeeList = new ArrayList<>();
        var employees = employeeService.findAll();
        for (Employee employee : employees) {
            employeeList.add(OBJECT_MAPPER.convertValue(new EmployeeResponse(employee), Map.class));
        }

        displayTable(employeeList, "id", "email", "name", "position", "salary", "createdAt", "updatedAt");
        System.out.println("Total employees: " + employeeList.size());
    }

    // Create an employee
    private void createEmployee(Scanner scanner) {
        System.out.print("Enter employee email: ");
        String email = scanner.nextLine();
        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();
        System.out.print("Enter employee position: ");
        String position = scanner.nextLine();
        System.out.print("Enter employee salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline

        Employee newEmployee = Employee.create(EmployeeId.generate(), email, name, position, Money.euro(salary));
        employeeService.create(newEmployee);
        System.out.println("Employee created successfully.");
    }

    // Update an employee
    private void updateEmployee(Scanner scanner) {
        System.out.print("Enter employee ID to update: ");
        String id = scanner.nextLine();

    }

    // Delete an employee
    private void deleteEmployee(Scanner scanner) {
        System.out.print("Enter employee ID to delete: ");
        String id = scanner.nextLine();

    }

    // Generate employees with multithreading
    private void generateEmployees(Scanner scanner) {
        System.out.print("Enter the number of employees to generate: ");
        int count = scanner.nextInt();
        System.out.print("Enter the number of threads to use: ");
        int threadCount = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < count; i++) {
            int employeeNumber = i + 1;
            executorService.submit(() -> {
                String email = "employee" + employeeNumber + "@domain.com";
                String name = "Employee " + employeeNumber;
                String position = "Position " + employeeNumber;
                double salary = 1000 + (employeeNumber * 100);

                Employee newEmployee = Employee.create(EmployeeId.generate(), email, name, position, Money.euro(salary));
                employeeService.create(newEmployee);
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Generated " + count + " employees using " + threadCount + " threads.");
    }
}
