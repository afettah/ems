package com.tech.employee.domain;

public class EmployeeFixtures {

    /**
     * Create a new employee with fixed values
     * @return always the same employee
     */
    public static Employee johnDoe() {
        return Employee.create(EmployeeId.generate(), "john.doe@domain.com", "John Doe", "Software Engineer", Money.euro(50000));
    }

    public static Employee generate() {
        return generate(EmployeeId.generate());
    }

    public static Employee generate(EmployeeId id) {
        return Employee.create(id,  "email-" + id + "@domain.com", "name" + id, "position" + id, Money.euro(10000));
    }
}
