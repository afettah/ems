
# Employee Database Management System

## Description

This is a simple Employee Database Management System. It is a console application that allows users to add, view, update, and delete employees from a database.
This project is written in java.

## Getting Started

## Jooq

### jOOQ generation
```bash

mvn clean compile -DskipTests -Pjooq-gen
```

## Business logic

### Employee

I made some improvements to the Employee domain model to enhance scalability and support for internationalization:

- Salary as Money: The salary is represented using a Money type to accommodate different currencies.
- Salary with SalaryComponent list: The salary is defined class containing a list of salary components. This demonstrates how different salary elements can be structured.
- FixedSalaryComponent: A concrete implementation of a SalaryComponent, which includes a PaymentRate and a PaymentSchedule.

These improvements are just examples of how the domain can be optimized.

In a real-world scenario, the design should be refined in collaboration with the Product team to ensure all the requirements for a complex domain like salary management are met.


### Time Off

Business Requirements :

#### 1. Categories

Each TimeOff request must have an associated category.

Admins can create new TimeOff categories.

   Categories can be of two types:

- **Exclusive**: Categories marked as "Exclusive" do not allow other categories to be requested for the same period. Overlapping date ranges are not allowed. (e.g. Annual leave).

- **Permissive**: Categories marked as "Permissive" allow other categories to be requested for the same period. Overlapping requests are allowed. (Work Remotely).

#### 2. Overlapping Requests

The general rule is that two categories cannot be requested for the same period unless at least one of the categories is marked as "Permissive".

For example, you can have Work Remotely applicable and you want to request Annual leave.

#### 3. Same Period Rules

   Two categories are considered to be in the same period if their requested date ranges overlap.

#### 4. TimeOff Request Validation:

A TimeOff request will fail (i.e., it will not be created) if it is not valid according to the following validation rules:

Valid scenarios include:
- No other requests exist at all for the period.
- All existing requests (active or inactive) are outside the requested period.
- Only inactive requests (e.g., canceled or rejected) exist for the same period.

   If the only existing requests for the same period are "Permissive," the new request is allowed, but those permissive requests will be automatically canceled.

#### 5. TimeOff Request Statuses (Out of Scope):

TimeOff requests can have the following statuses:
 - **REQUESTED** (active): The request is pending approval.
 - **APPROVED** (active): The request has been approved.
 - **REJECTED** (inactive): The request was rejected.
 - **CANCELLED** (inactive): The request was canceled.

#### 5. Balance (Out of Scope):

The balance aspect of the TimeOff system is outside the scope of this specification.


## Dependencies
