package com.tech.employee.databse;

import com.tech.employee.domain.Employee;
import com.tech.employee.domain.EmployeeFixtures;
import com.tech.employee.domain.salary.Money;
import com.tech.employee.domain.salary.Salary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmployeeRepositoryImplTest {

    private EmployeeRepositoryImpl employeeRepositoryImpl;

    @BeforeEach
    void setUp() {
        employeeRepositoryImpl = new EmployeeRepositoryImpl(new InMemoryEmployeeDatabase());
    }

    @Nested
    class Create {
        @Test
        void create_should_create_employee() {
            //given
            Employee expectedEmployee = EmployeeFixtures.generate();

            //when
            employeeRepositoryImpl.create(expectedEmployee);
            Employee actualEmployee = employeeRepositoryImpl.getById(expectedEmployee.getId());

            //then
            assertThat(actualEmployee)
                    .isEqualTo(expectedEmployee)
                    .extracting(Employee::getId, Employee::getEmail, Employee::getName, Employee::getPosition, Employee::getSalary, Employee::getCreatedAt, Employee::getUpdatedAt)
                    .containsExactly(expectedEmployee.getId(), expectedEmployee.getEmail(), expectedEmployee.getName(), expectedEmployee.getPosition(), expectedEmployee.getSalary(), expectedEmployee.getCreatedAt(), expectedEmployee.getUpdatedAt());
        }

        @Test
        void create_should_throw_exception_when_employee_already_exists() {
            //given
            Employee employee = EmployeeFixtures.generate();
            employeeRepositoryImpl.create(employee);

            //when
            //then
            var exception = assertThrows(DuplicatedKeyException.class, () -> employeeRepositoryImpl.create(employee));
            assertThat(exception).hasMessage("Duplicated key id with value " + employee.getId().id() + " in table employee")
                    .extracting(DuplicatedKeyException::getKey, DuplicatedKeyException::getValue, DuplicatedKeyException::getTable)
                    .containsExactly("id", employee.getId().id(), "employee");
        }
    }

    @Nested
    class Update {
        @Test
        void update_should_update_employee() {
            //given
            Employee employee = EmployeeFixtures.generate();
            employeeRepositoryImpl.create(employee);
            Employee updatedEmployee = new Employee(employee.getId(), "new-email@domai.com", "new-name", "new-position", Salary.fixedMonthlySalary(Money.euro(1)), Instant.now(), Instant.now());

            //when
            employeeRepositoryImpl.update(updatedEmployee);
            Employee actualEmployee = employeeRepositoryImpl.getById(employee.getId());

            //then
            assertThat(actualEmployee)
                    .isEqualTo(updatedEmployee)
                    .extracting(Employee::getId, Employee::getEmail, Employee::getName, Employee::getPosition, Employee::getSalary, Employee::getCreatedAt, Employee::getUpdatedAt)
                    .containsExactly(updatedEmployee.getId(), updatedEmployee.getEmail(), updatedEmployee.getName(), updatedEmployee.getPosition(), updatedEmployee.getSalary(), updatedEmployee.getCreatedAt(), updatedEmployee.getUpdatedAt());
        }

        @Test
        void update_should_throw_exception_when_employee_does_not_exist() {
            //given
            Employee employee = EmployeeFixtures.generate();

            //when
            //then
            var exception = assertThrows(DataNotFoundException.class, () -> employeeRepositoryImpl.update(employee));
            assertThat(exception).hasMessage("Data not found with key id and value " + employee.getId().id() + " in table employee")
                    .extracting(DataNotFoundException::getKey, DataNotFoundException::getValue, DataNotFoundException::getTable)
                    .containsExactly("id", employee.getId().id(), "employee");
        }
    }

    @Nested
    class FindAll {
        @Test
        void findAll_should_return_all_employees() {
            //given
            Employee employee1 = EmployeeFixtures.generate();
            Employee employee2 = EmployeeFixtures.generate();
            employeeRepositoryImpl.create(employee1);
            employeeRepositoryImpl.create(employee2);

            //when
            var employees = employeeRepositoryImpl.findAll();

            //then
            assertThat(employees)
                    .containsExactlyInAnyOrder(employee1, employee2);
        }
    }

}
