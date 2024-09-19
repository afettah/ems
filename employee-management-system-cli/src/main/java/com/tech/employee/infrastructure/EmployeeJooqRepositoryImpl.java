package com.tech.employee.infrastructure;

import com.tech.employee.domain.*;
import com.tech.employee.domain.salary.Salary;
import com.tech.shared.infrastructure.HistoryJooqRepository;
import com.tech.shared.infrastructure.JsonbMapper;
import com.tech.employee.jooq.generated.tables.records.JEmployeesRecord;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.tech.employee.jooq.generated.Tables.EMPLOYEES;
import static com.tech.employee.jooq.generated.Tables.EMPLOYEE_HISTORY;

@AllArgsConstructor
class EmployeeJooqRepositoryImpl implements EmployeeRepository {
    public static final String EMPLOYEE_ENTITY = "Employee";
    private final DSLContext dslContext;
    private final HistoryJooqRepository historyRepository;

    @Override
    @Transactional
    public void create(Employee employee) {
        JEmployeesRecord employeesRecord = new JEmployeesRecord(
                employee.getId().uuid(),
                employee.getEmail(),
                employee.getName(),
                employee.getPosition(),
                JsonbMapper.mapToJsonb(employee.getSalary()),
                Instant.now(),
                null
        );
        dslContext.insertInto(EMPLOYEES)
                .set(employeesRecord)
                .execute();
        historyRepository.historizeCreation(EMPLOYEE_HISTORY, employeesRecord);
    }

    @Override
    @Transactional
    public void update(Employee employee) {
        var beforeValue = dslContext.select(EMPLOYEES.fields()).from(EMPLOYEES)
                .where(EMPLOYEES.ID.eq(employee.getId().uuid()))
                .fetchOptionalInto(JEmployeesRecord.class);
        if (beforeValue.isEmpty()) {
            throw new DataNotFoundException(EMPLOYEE_ENTITY, employee.getId());
        }
        dslContext.update(EMPLOYEES)
                .set(EMPLOYEES.NAME, employee.getName())
                .set(EMPLOYEES.EMAIL, employee.getEmail())
                .set(EMPLOYEES.POSITION, employee.getPosition())
                .set(EMPLOYEES.SALARY, JsonbMapper.mapToJsonb(employee.getSalary()))
                .set(EMPLOYEES.UPDATED_AT, Instant.now())
                .where(EMPLOYEES.ID.eq(employee.getId().uuid()))
                .execute();

        var afterValue = dslContext.select(EMPLOYEES.fields()).from(EMPLOYEES)
                .where(EMPLOYEES.ID.eq(employee.getId().uuid()))
                .fetchOneInto(JEmployeesRecord.class);

        historyRepository.historizeUpdate(EMPLOYEE_HISTORY, beforeValue.get(), afterValue);
    }

    @Override
    public Optional<Employee> findById(EmployeeId id) {
        return dslContext.selectFrom(EMPLOYEES)
                .where(EMPLOYEES.ID.eq(id.uuid()))
                .fetchOptional()
                .map(EmployeeJooqRepositoryImpl::mapToEmployee);
    }

    @Override
    public Employee getById(EmployeeId id) {
        return findById(id)
                .orElseThrow(() -> new DataNotFoundException(EMPLOYEE_ENTITY, id));
    }

    @Override
    public List<Employee> findAll() {
        return dslContext.selectFrom(EMPLOYEES)
                .orderBy(EMPLOYEES.CREATED_AT)
                .fetch()
                .map(EmployeeJooqRepositoryImpl::mapToEmployee);
    }

    @Override
    public List<Employee> findAll(EmployeeFilter filter) {
        return dslContext.selectFrom(EMPLOYEES)
                .where(toCondition(filter))
                .orderBy(EMPLOYEES.CREATED_AT)
                .fetch()
                .map(EmployeeJooqRepositoryImpl::mapToEmployee);
    }

    private Condition toCondition(EmployeeFilter filter) {
        List<Condition> conditions = new ArrayList<>();
        filter.getId().ifPresent(id -> conditions.add(EMPLOYEES.ID.eq(id.uuid())));
        filter.getName().ifPresent(name -> conditions.add(EMPLOYEES.NAME.containsIgnoreCase(name)));
        filter.getEmail().ifPresent(email -> conditions.add(EMPLOYEES.EMAIL.eq(email.toLowerCase())));
        filter.getPosition().ifPresent(position -> conditions.add(EMPLOYEES.POSITION.eq(position)));
        return conditions.stream().reduce(Condition::and).orElse(null);
    }

    @Override
    public void delete(EmployeeId employeeId) {
        int deleted = dslContext.deleteFrom(EMPLOYEES)
                .where(EMPLOYEES.ID.eq(employeeId.uuid()))
                .execute();
        if (deleted == 0) {
            throw new DataNotFoundException(EMPLOYEE_ENTITY, employeeId);
        }
    }

    private static Employee mapToEmployee(JEmployeesRecord employeesRecord) {
        return new Employee(
                new EmployeeId(employeesRecord.getId()),
                employeesRecord.getEmail(),
                employeesRecord.getName(),
                employeesRecord.getPosition(),
                JsonbMapper.mapFromJsonb(employeesRecord.getSalary(), Salary.class),
                employeesRecord.getCreatedAt(),
                employeesRecord.getUpdatedAt()
        );
    }
}
