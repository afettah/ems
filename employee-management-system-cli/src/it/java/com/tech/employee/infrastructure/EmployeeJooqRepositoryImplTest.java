package com.tech.employee.infrastructure;

import com.tech.employee.AbstractITest;
import com.tech.employee.domain.DataNotFoundException;
import com.tech.employee.domain.Employee;
import com.tech.employee.domain.EmployeeFilter;
import com.tech.employee.domain.salary.Money;
import com.tech.employee.domain.salary.Salary;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmployeeJooqRepositoryImplTest extends AbstractITest {

    @Autowired
    private EmployeeJooqRepositoryImpl employeeJooqRepositoryImpl;

    @Nested
    class Create {
        @Test
        void create_should_create_employee() {
            //given
            Employee expectedEmployee = EmployeeFixtures.generate();

            //when
            employeeJooqRepositoryImpl.create(expectedEmployee);
            Employee actualEmployee = employeeJooqRepositoryImpl.getById(expectedEmployee.getId());

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
            employeeJooqRepositoryImpl.create(employee);

            //when
            //then
            assertThrows(DuplicateKeyException.class, () -> employeeJooqRepositoryImpl.create(employee));
        }
    }

    @Nested
    class Update {
        @Test
        @Transactional
        void update_should_update_employee() {
            //given
            Employee employee = EmployeeFixtures.generate();
            employeeJooqRepositoryImpl.create(employee);
            Employee updatedEmployee = new Employee(employee.getId(), "new-email@domai.com", "new-name", "new-position", Salary.fixedMonthlySalary(Money.euro(1)), Instant.now(), Instant.now());

            //when
            employeeJooqRepositoryImpl.update(updatedEmployee);
            Employee actualEmployee = employeeJooqRepositoryImpl.getById(employee.getId());

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
            var exception = assertThrows(DataNotFoundException.class, () -> employeeJooqRepositoryImpl.update(employee));
            assertThat(exception).hasMessage("Employee with id " + employee.getId() + " not found");
        }
    }

    @Nested
    class FindAll {
        @Test
        void findAll_should_return_all_employees() {
            //given
            Employee employee1 = EmployeeFixtures.generate();
            Employee employee2 = EmployeeFixtures.generate();
            employeeJooqRepositoryImpl.create(employee1);
            employeeJooqRepositoryImpl.create(employee2);

            //when
            var employees = employeeJooqRepositoryImpl.findAll();

            //then
            assertThat(employees)
                    .containsExactlyInAnyOrder(employee1, employee2);
        }

        @Test
        void findAll_should_filter_by_name_part_case_insensitive() {
            //given
            String name = "expected Name";
            List<Employee> expectedEmployees = List.of(
                    EmployeeFixtures.generate(name),
                    EmployeeFixtures.generate(name + " suffix"),
                    EmployeeFixtures.generate("prefix " + name + " suffix"),
                    EmployeeFixtures.generate("prefix " + name.toUpperCase())
            );

            for (Employee employee : expectedEmployees) {
                create(employee);
            }

            //other employees
            create(EmployeeFixtures.generate(name.substring(2)));
            create(EmployeeFixtures.generate());
            create(EmployeeFixtures.generate());

            //when
            var employees = employeeJooqRepositoryImpl.findAll(new EmployeeFilter(null, name, null, null));

            //then
            assertThat(employees)
                    .containsExactlyElementsOf(expectedEmployees);
        }

        @Test
        void findAll_should_filter_by_id() {
            //given
            Employee employee1 = EmployeeFixtures.generate();
            Employee employee2 = EmployeeFixtures.generate();
            employeeJooqRepositoryImpl.create(employee1);
            employeeJooqRepositoryImpl.create(employee2);

            //when
            var employees = employeeJooqRepositoryImpl.findAll(new EmployeeFilter(employee1.getId(), null, null, null));

            //then
            assertThat(employees)
                    .containsExactly(employee1);
        }

        @Test
        void findAll_should_filter_by_position() {
            //given
            String position = "Search-Position";
            List<Employee> expectedEmployees = List.of(
                    EmployeeFixtures.generate(),
                    EmployeeFixtures.generate(),
                    EmployeeFixtures.generate(),
                    EmployeeFixtures.generate()
            );


            for (Employee employee : expectedEmployees) {
                employee.updatePosition(position);
                create(employee);
            }

            //other employees
            create(EmployeeFixtures.generate());
            create(EmployeeFixtures.generate());
            create(EmployeeFixtures.generate());

            //when
            var employees = employeeJooqRepositoryImpl.findAll(new EmployeeFilter(null, null, null, position));

            //then
            assertThat(employees)
                    .containsExactlyElementsOf(expectedEmployees);
        }

        @Test
        void findAll_should_filter_by_email_case_insensitive() {
            //given
            String email = "email@domai.com";

            Employee employee = new Employee(EmployeeFixtures.generate().getId(), email, "name", "position", Salary.fixedMonthlySalary(Money.euro(10000)), Instant.now(), Instant.now());
            create(employee);

            //other employees
            create(EmployeeFixtures.generate());
            create(EmployeeFixtures.generate());

            //when
            var employees = employeeJooqRepositoryImpl.findAll(new EmployeeFilter(null, null, email, null));

            //then
            assertThat(employees)
                    .containsExactly(employee);

            //when
            employees = employeeJooqRepositoryImpl.findAll(new EmployeeFilter(null, null, email.toUpperCase(), null));

            //then
            assertThat(employees)
                    .containsExactly(employee);

        }
    }

    @Nested
    class Delete {
        @Test
        void delete_should_delete_employee() {
            //given
            Employee employee = EmployeeFixtures.generate();
            employeeJooqRepositoryImpl.create(employee);

            //when
            employeeJooqRepositoryImpl.delete(employee.getId());

            //then
            assertThrows(DataNotFoundException.class, () -> employeeJooqRepositoryImpl.getById(employee.getId()));
        }

        @Test
        void delete_should_throw_exception_when_employee_does_not_exist() {
            //given
            Employee employee = EmployeeFixtures.generate();

            //when
            //then
            var exception = assertThrows(DataNotFoundException.class, () -> employeeJooqRepositoryImpl.delete(employee.getId()));
            assertThat(exception).hasMessage("Employee with id " + employee.getId() + " not found");
        }
    }

    private Employee create(Employee employee) {
        employeeJooqRepositoryImpl.create(employee);
        return employee;
    }

}
