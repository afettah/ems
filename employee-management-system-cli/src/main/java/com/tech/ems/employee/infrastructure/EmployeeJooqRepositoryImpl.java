package com.tech.ems.employee.infrastructure;

import com.tech.ems.employee.DataNotFoundException;
import com.tech.ems.employee.Employee;
import com.tech.ems.employee.EmployeeFilter;
import com.tech.ems.employee.EmployeeId;
import com.tech.ems.employee.EmployeeRepository;
import com.tech.ems.employee.salary.Salary;
import com.tech.ems.jooq.generated.tables.records.JEmployeeRecord;
import com.tech.ems.shared.infrastructure.HistoryJooqRepository;
import com.tech.ems.shared.infrastructure.JsonbMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.tech.ems.jooq.generated.Tables.EMPLOYEE;
import static com.tech.ems.jooq.generated.Tables.EMPLOYEE_HISTORY;

@AllArgsConstructor
class EmployeeJooqRepositoryImpl implements EmployeeRepository {
    public static final String EMPLOYEE_ENTITY = "Employee";
    private final DSLContext dslContext;
    private final HistoryJooqRepository historyRepository;

    @Override
    @Transactional
    public void create(Employee employee) {
        JEmployeeRecord employeesRecord = new JEmployeeRecord(
                employee.getId().uuid(),
                employee.getEmail(),
                employee.getName(),
                employee.getPosition(),
                JsonbMapper.mapToJsonb(employee.getSalary()),
                Instant.now(),
                null
        );
        dslContext.insertInto(EMPLOYEE)
                .set(employeesRecord)
                .execute();
        historyRepository.historizeCreation(EMPLOYEE_HISTORY, employeesRecord);
    }

    @Override
    @Transactional
    public void update(Employee employee) {
        var beforeValue = dslContext.select(EMPLOYEE.fields()).from(EMPLOYEE)
                .where(EMPLOYEE.ID.eq(employee.getId().uuid()))
                .fetchOptionalInto(JEmployeeRecord.class);
        if (beforeValue.isEmpty()) {
            throw new DataNotFoundException(EMPLOYEE_ENTITY, employee.getId());
        }
        dslContext.update(EMPLOYEE)
                .set(EMPLOYEE.NAME, employee.getName())
                .set(EMPLOYEE.EMAIL, employee.getEmail())
                .set(EMPLOYEE.POSITION, employee.getPosition())
                .set(EMPLOYEE.SALARY, JsonbMapper.mapToJsonb(employee.getSalary()))
                .set(EMPLOYEE.UPDATED_AT, Instant.now())
                .where(EMPLOYEE.ID.eq(employee.getId().uuid()))
                .execute();

        var afterValue = dslContext.select(EMPLOYEE.fields()).from(EMPLOYEE)
                .where(EMPLOYEE.ID.eq(employee.getId().uuid()))
                .fetchOneInto(JEmployeeRecord.class);

        historyRepository.historizeUpdate(EMPLOYEE_HISTORY, beforeValue.get(), afterValue);
    }

    @Override
    public Optional<Employee> findById(EmployeeId id) {
        return dslContext.selectFrom(EMPLOYEE)
                .where(EMPLOYEE.ID.eq(id.uuid()))
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
        return dslContext.selectFrom(EMPLOYEE)
                .orderBy(EMPLOYEE.CREATED_AT)
                .fetch()
                .map(EmployeeJooqRepositoryImpl::mapToEmployee);
    }

    @Override
    public List<Employee> findAll(EmployeeFilter filter) {
        return dslContext.selectFrom(EMPLOYEE)
                .where(toCondition(filter))
                .orderBy(EMPLOYEE.CREATED_AT)
                .fetch()
                .map(EmployeeJooqRepositoryImpl::mapToEmployee);
    }

    private Condition toCondition(EmployeeFilter filter) {
        List<Condition> conditions = new ArrayList<>();
        filter.getId().ifPresent(id -> conditions.add(EMPLOYEE.ID.eq(id.uuid())));
        filter.getName().ifPresent(name -> conditions.add(EMPLOYEE.NAME.containsIgnoreCase(name)));
        filter.getEmail().ifPresent(email -> conditions.add(EMPLOYEE.EMAIL.eq(email.toLowerCase())));
        filter.getPosition().ifPresent(position -> conditions.add(EMPLOYEE.POSITION.eq(position)));
        return conditions.stream().reduce(Condition::and).orElse(null);
    }

    @Override
    public void delete(EmployeeId employeeId) {
        int deleted = dslContext.deleteFrom(EMPLOYEE)
                .where(EMPLOYEE.ID.eq(employeeId.uuid()))
                .execute();
        if (deleted == 0) {
            throw new DataNotFoundException(EMPLOYEE_ENTITY, employeeId);
        }
    }

    private static Employee mapToEmployee(JEmployeeRecord employeesRecord) {
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
