
# Employee Database Management System

## Description

This is a simple Employee Database Management System. It is a console application that allows users to add, view, update, and delete employees from a database.
This project is written in java.

## Getting Started

## Implementation

I made some improvements to the Employee domain model to enhance scalability and support for internationalization:

- Salary as Money: The salary is represented using a Money type to accommodate different currencies.
- Salary with SalaryComponent list: The salary is defined class containing a list of salary components. This demonstrates how different salary elements can be structured.
- FixedSalaryComponent: A concrete implementation of a SalaryComponent, which includes a PaymentRate and a PaymentSchedule.

These improvements are just examples of how the domain can be optimized.

In a real-world scenario, the design should be refined in collaboration with the Product team to ensure all the requirements for a complex domain like salary management are met.
### Dependencies

