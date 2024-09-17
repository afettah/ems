package com.tech.employee.domain;

import com.tech.employee.domain.salary.Money;
import com.tech.employee.domain.salary.Salary;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmployeeTest {

    @Nested
    class Create {
        @Test
        void should_create_instance_of_employee() {
            //given
            String email = "email@domai.com";
            String name = "Employee Name";
            String position = "Position";
            Salary salary = Salary.fixedMonthlySalary(Money.euro(10000.05));

            //when
            Employee employee = Employee.create(new EmployeeCreateCommand(email, name, position, salary));

            //then
            assertThat(employee).extracting(Employee::getId, Employee::getEmail, Employee::getName, Employee::getPosition, Employee::getSalary)
                    .containsExactly(employee.getId(), email, name, position, salary);
            assertThat(employee.getCreatedAt()).isNotNull();
            assertThat(employee.getUpdatedAt()).isNull();
        }

        @ParameterizedTest
        @MethodSource("newEmployeeWithoutRequiredFields")
        void new_employee_without_required_fields_should_throw_exception(String filed, EmployeeId id, String email, String name, String position, Salary salary) {
            //when
            //then
            assertThatThrownBy(() -> new Employee(id, email, name, position, salary, Instant.now(), Instant.now()))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessageContaining(filed);
        }

        @ParameterizedTest
        @MethodSource("createEmployeeWithoutRequiredFields")
        void create_employee_without_required_fields_should_throw_exception(String filed, EmployeeId id, String email, String name, String position, Salary salary) {
            //when
            //then
            assertThatThrownBy(() -> Employee.create(new EmployeeCreateCommand(email, name, position, salary)))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessageContaining(filed);
        }

        static Stream<Arguments> createEmployeeWithoutRequiredFields() {
            EmployeeId id = EmployeeId.generate();
            String email = "email@domain.com";
            String name = "Employee Name";
            String position = "Position";
            Salary salary = Salary.fixedMonthlySalary(Money.euro(10000));
            return Stream.of(
                    Arguments.of("email", id, null, name, position, salary),
                    Arguments.of("name", id, email, null, position, salary),
                    Arguments.of("position", id, email, name, null, salary),
                    Arguments.of("salary", id, email, name, position, null));
        }

        static Stream<Arguments> newEmployeeWithoutRequiredFields() {
            String email = "email@domain.com";
            String name = "Employee Name";
            String position = "Position";
            Salary salary = Salary.fixedMonthlySalary(Money.euro(10000));
            return Stream.concat(
                    createEmployeeWithoutRequiredFields(),
                    Stream.of(
                            Arguments.of("id", null, email, name, position, salary)
                    ));
        }
    }

    @Nested
    class Update {
        @Test
        void update_salary_should_update_salary_and_updated_at() {
            //given
            Employee employee = EmployeeFixtures.johnDoe();
            var newSalary = Salary.fixedMonthlySalary(Money.euro(60000));

            //when
            employee.updateSalary(newSalary);

            //then
            assertThat(employee.getSalary()).isEqualTo(newSalary);
            assertThat(employee.getUpdatedAt()).isNotNull();
        }

        @Test
        void update_position_should_update_position_and_updated_at() {
            //given
            Employee employee = EmployeeFixtures.johnDoe();
            String newPosition = "New Position";

            //when
            employee.updatePosition(newPosition);

            //then
            assertThat(employee.getPosition()).isEqualTo(newPosition);
            assertThat(employee.getUpdatedAt()).isNotNull();
        }

        @Test
        void update_should_update_position_and_salary() {
            //given
            Employee employee = EmployeeFixtures.johnDoe();
            var updateSalary = Salary.fixedMonthlySalary(Money.euro(60000));
            var updatePosition = "New Position";
            EmployeeUpdateCommand employeeUpdateCommand = new EmployeeUpdateCommand(updatePosition, updateSalary);

            //when
            employee.update(employeeUpdateCommand);

            //then
            assertThat(employee.getPosition()).isEqualTo(updatePosition);
            assertThat(employee.getSalary()).isEqualTo(updateSalary);
            assertThat(employee.getUpdatedAt()).isNotNull();
        }

        @Test
        void update_should_not_update_position_and_salary_when_command_is_empty() {
            //given
            Employee employee = EmployeeFixtures.johnDoe();
            var internalSalary = employee.getSalary();
            var internalPosition = employee.getPosition();
            EmployeeUpdateCommand employeeUpdateCommand = new EmployeeUpdateCommand(null, null);

            //when
            employee.update(employeeUpdateCommand);

            //then
            assertThat(employee.getPosition()).isEqualTo(internalPosition);
            assertThat(employee.getSalary()).isEqualTo(internalSalary);
            assertThat(employee.getUpdatedAt()).isNull();
        }
    }

    @Nested
    class EqualsAndHashCode {
        @Test
        void equals_should_return_true_when_employee_ids_are_equal() {
            //given
            Employee employee = EmployeeFixtures.johnDoe();
            Employee employee1 = new Employee(employee.getId(), employee.getEmail(), employee.getName(), employee.getPosition(), employee.getSalary(), employee.getCreatedAt(), employee.getUpdatedAt());
            Employee employee2 = new Employee(employee.getId(), "other-email@domain.com", "Other Name", "Other Position", Salary.fixedMonthlySalary(Money.euro(60000)), Instant.now(), Instant.now());


            //then
            assertThat(employee).isEqualTo(employee);
            assertThat(employee1).isEqualTo(employee2);
            assertThat(employee2).isEqualTo(employee1);
        }

        @Test
        void equals_should_return_false_when_comparing_with_null() {
            //given
            Employee employee = EmployeeFixtures.johnDoe();

            //when
            //then
            assertThat(employee).isNotEqualTo(null);
        }

        @Test
        void equals_should_return_false_when_comparing_with_different_class() {
            //given
            Employee employee = EmployeeFixtures.johnDoe();

            //when
            //then
            assertThat(employee).isNotEqualTo(new Object());
        }

        @Test
        void equals_should_return_false_when_employee_ids_are_different() {
            //given
            Employee employee1 = EmployeeFixtures.generate();
            Employee employee2 = EmployeeFixtures.generate();

            //when
            //then
            assertThat(employee1).isNotEqualTo(employee2);
            assertThat(employee2).isNotEqualTo(employee1);
        }

        @Test
        void hash_code_should_return_same_value_when_employee_ids_are_equal() {
            //given
            EmployeeId id = EmployeeId.generate();
            Employee employee1 = EmployeeFixtures.generate(id);
            Employee employee2 = EmployeeFixtures.generate(id);

            //then
            assertThat(employee1).hasSameHashCodeAs(employee2);
        }

        @Test
        void hash_code_should_return_different_value_when_employee_ids_are_different() {
            //given
            Employee employee1 = EmployeeFixtures.generate();
            Employee employee2 = EmployeeFixtures.generate();

            //then
            assertThat(employee1.hashCode()).isNotEqualTo(employee2.hashCode());
        }
    }
}
