package com.tech.employee.domain.salary;

public record FixedSalaryComponent(String label, PaymentRate rate, PaymentSchedule paymentSchedule) implements SalaryComponent {

    @Override
    public String toString() {
        return label + ": " + rate.amount() + "/" + rate.base() ;
    }
}
