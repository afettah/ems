
# Employee Database Management System

## Description

This is a simple Employee Database Management System. It is a console application that allows to manage employees and time off requests.

![EMS](./ems.gif)


## Getting Started

### Dependencies
* Java 21+
* Maven 3.6.3+
* Docker (optional)

### Run Modes

This project supports two modes: **H2 (in-memory)** mode and **PostgreSQL** mode. You can easily switch between them using Spring profiles depending on your needs.

You can specify which mode to run by using Spring profiles:

- To run the application in **H2 mode**, use the `h2` profile:
  ```bash
    SPRING_PROFILES_ACTIVE=h2 ./mvnw -pl employee-management-system-cli spring-boot:run -DskipTests
    ```

- To run the application in PostgreSQL mode, use the postgres profile:
  ```bash
    docker-compose up -d && SPRING_PROFILES_ACTIVE=postgres ./mvnw -pl employee-management-system-cli spring-boot:run -DskipTests
    ```

## Project Structure

The project is divided into the following modules:

- **employee-domain**: Contains the domain model and business logic.
- **employee-management-system-cli**: Spring Boot implementation that uses the domain model to expose a CLI interface. We find also the implementation domain dependencies.

## Business logic

### Employee

I made some improvements to the Employee domain model to enhance scalability and support for internationalization:

- Position as entity: The Position is now an entity with a code and a name.
- Support i18n translations: The Position name can be translated into multiple languages.
- Salary as Money: The salary is represented using a Money type to accommodate different currencies.
- Salary with SalaryComponent list: The salary is defined class containing a list of salary components. This demonstrates how different salary elements can be structured.
- FixedSalaryComponent: A concrete implementation of a SalaryComponent, which includes a PaymentRate and a PaymentSchedule.

These improvements are just examples of how the domain can be optimized.

In a real-world scenario, the design should be refined in collaboration with the Product team to ensure all the requirements for a complex domain like employee management are met.


### Time Off

Business Requirements :

#### 1. Categories

Each TimeOff request must have an associated category.

Admins can create new TimeOff categories.

Categories can be of two types:

- **Not Auto cancel**: Categories marked as "not auto cancel" do not allow other categories to be requested for the same period. (e.g. Annual leave).

- **Auto cancel**: Categories marked as "autoCancel allow other categories to be requested for the same period. The existing requests will be canceled. (Work Remotely).

#### 2. Overlapping Requests

The general rule is that two categories cannot be requested for the same period;

There are exceptions to this rule for auto cancel categories:
For example, you can have Work Remotely applicable and you want to request Annual leave.

#### 3. Same Period Rules

Two categories are considered to be in the same period if their requested date ranges overlap.

#### 4. TimeOff Request Validation:

A TimeOff request will fail (i.e., it will not be created) if it is not valid according to the following validation rules:

Valid scenarios include:
- No other requests exist at all for the period.
- All existing requests (active or inactive) are outside the requested period.
- Only inactive requests (e.g., canceled or rejected) exist for the same period.

  If the only existing requests for the same period are "auto cancel," the new request is allowed, but those requests will be automatically canceled.

#### 5. TimeOff Request Statuses (Out of Scope):

TimeOff requests can have the following statuses:
- **REQUESTED** (active): The request is pending approval.
- **APPROVED** (active): The request has been approved.
- **REJECTED** (inactive): The request was rejected.
- **CANCELLED** (inactive): The request was canceled.

#### 5. Balance (Out of Scope):



## Development

### jOOQ generation
In this project we use [JOOQ](https://www.jooq.org/) to generate the database access layer.

When modifying the database schema, you should : 

1. Add migration scripts to the `src/main/resources/db/migration` folder.
2. Run the application so flyway can apply the migration scripts.
3. Run the following command to regenerate the jOOQ classes.
4. The jOOQ classes will be generated in the `employee-management-system-cli/src/generated` folder.
```bash

mvn clean compile -DskipTests -Pjooq-gen
```
