package com.tech.timeoff.infrastructure;

import com.tech.timeoff.DateRange;
import com.tech.timeoff.TimeOff;
import com.tech.timeoff.TimeOffId;
import com.tech.timeoff.TimeOffRepository;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;

import java.time.Instant;
import java.util.List;

import static com.tech.employee.jooq.generated.Tables.EMPLOYEE_TIMEOFF;

@AllArgsConstructor
class TimeOffJooqRepositoryImpl implements TimeOffRepository {
    private final DSLContext dslContext;

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
    public List<TimeOff> findOverlappingDateRange(DateRange dateRange) {
        return dslContext.selectFrom(EMPLOYEE_TIMEOFF)
                .where(EMPLOYEE_TIMEOFF.START_DATE.between(dateRange.start(), dateRange.end())
                        .or(EMPLOYEE_TIMEOFF.END_DATE.between(dateRange.start(), dateRange.end())))
                .fetchInto(TimeOff.class);
    }

    @Override
    public void cancel(TimeOffId id) {
        dslContext.update(EMPLOYEE_TIMEOFF)
                .set(EMPLOYEE_TIMEOFF.STATUS, TimeOff.TimeOffStatus.CANCELLED.name())
                .where(EMPLOYEE_TIMEOFF.ID.eq(id.uuid()))
                .execute();
    }
}
