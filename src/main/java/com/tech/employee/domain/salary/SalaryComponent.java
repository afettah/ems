package com.tech.employee.domain.salary;


public sealed interface SalaryComponent permits FixedSalaryComponent {
    String label();
}
