package com.tech.timeoff.infrastructure;

import com.tech.employee.domain.EmployeeId;
import com.tech.employee.jooq.generated.tables.records.JEmployeeTimeoffRecord;
import com.tech.timeoff.*;
import com.tech.timeoff.category.CategoryId;
import com.tech.timeoff.category.TimeOffCategoryRepository;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;

import java.time.Instant;
import java.util.List;

import static com.tech.employee.jooq.generated.Tables.EMPLOYEE_TIMEOFF;

@AllArgsConstructor
class TimeOffJooqRepositoryImpl implements TimeOffRepository {
    private final DSLContext dslContext;
    private final TimeOffCategoryRepository timeOffCategoryRepository;

    @Override
    public void create(TimeOff expectedTimeOff) {
        dslContext.insertInto(EMPLOYEE_TIMEOFF)
                .set(EMPLOYEE_TIMEOFF.ID, expectedTimeOff.getId().uuid())
                .set(EMPLOYEE_TIMEOFF.EMPLOYEE_ID, expectedTimeOff.getEmployeeId().uuid())
                .set(EMPLOYEE_TIMEOFF.CATEGORY_ID, expectedTimeOff.getCategory().id().uuid())
                .set(EMPLOYEE_TIMEOFF.START_DATE, expectedTimeOff.getDateRange().start())
                .set(EMPLOYEE_TIMEOFF.END_DATE, expectedTimeOff.getDateRange().end())
                .set(EMPLOYEE_TIMEOFF.STATUS, expectedTimeOff.getStatus().name())
                .set(EMPLOYEE_TIMEOFF.COMMENT, expectedTimeOff.getComment())
                .set(EMPLOYEE_TIMEOFF.CREATED_AT, Instant.now())
                .execute();
    }

    @Override
    public List<TimeOff> findOverlappingDateRange(EmployeeId employeeId, DateRange dateRange) {
        return dslContext.selectFrom(EMPLOYEE_TIMEOFF)
                .where(EMPLOYEE_TIMEOFF.EMPLOYEE_ID.eq(employeeId.uuid())
                        .and(EMPLOYEE_TIMEOFF.START_DATE
                                .between(dateRange.start(), dateRange.end())
                                .or(EMPLOYEE_TIMEOFF.END_DATE.between(dateRange.start(), dateRange.end()))))
                .fetch(this::mapTimeOff);
    }

    @Override
    public void cancel(TimeOffId id) {
        dslContext.update(EMPLOYEE_TIMEOFF)
                .set(EMPLOYEE_TIMEOFF.STATUS, TimeOff.TimeOffStatus.CANCELLED.name())
                .where(EMPLOYEE_TIMEOFF.ID.eq(id.uuid()))
                .execute();
    }

    @Override
    public List<TimeOff> findAll(TimeOffFilter timeOffFilter) {
        var query = dslContext.selectFrom(EMPLOYEE_TIMEOFF);
        timeOffFilter.getEmployeeId().ifPresent(employeeId -> query.where(EMPLOYEE_TIMEOFF.EMPLOYEE_ID.eq(employeeId.uuid())));
        return query.fetch(this::mapTimeOff);
    }

    private TimeOff mapTimeOff(JEmployeeTimeoffRecord jEmployeeTimeoffRecord) {
        return new TimeOff(
                new TimeOffId(jEmployeeTimeoffRecord.getId()),
                new EmployeeId(jEmployeeTimeoffRecord.getEmployeeId()),
                timeOffCategoryRepository.findById(new CategoryId(jEmployeeTimeoffRecord.getCategoryId())).orElseThrow(),
                new DateRange(jEmployeeTimeoffRecord.getStartDate(), jEmployeeTimeoffRecord.getEndDate()),
                jEmployeeTimeoffRecord.getComment(),
                TimeOff.TimeOffStatus.valueOf(jEmployeeTimeoffRecord.getStatus())
        );
    }

}
