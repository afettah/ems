package com.tech.ems.timeoff.infrastructure;

import com.tech.ems.jooq.generated.tables.records.JTimeoffCategoryRecord;
import com.tech.ems.timeoff.category.CategoryId;
import com.tech.ems.timeoff.category.TimeOffCategory;
import com.tech.ems.timeoff.category.TimeOffCategoryRepository;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.tech.ems.jooq.generated.Tables.TIMEOFF_CATEGORY;

@AllArgsConstructor
class TimeOffCategoryJooqRepositoryImpl implements TimeOffCategoryRepository {
    private final DSLContext dslContext;

    @Override
    public void create(TimeOffCategory category) {
        dslContext.insertInto(TIMEOFF_CATEGORY)
                .set(TIMEOFF_CATEGORY.ID, category.id().uuid())
                .set(TIMEOFF_CATEGORY.NAME, category.name())
                .set(TIMEOFF_CATEGORY.DESCRIPTION, category.description())
                .set(TIMEOFF_CATEGORY.PAID, category.paid())
                .set(TIMEOFF_CATEGORY.AUTO_CANCELLABLE, category.autoCancellable())
                .set(TIMEOFF_CATEGORY.CREATED_AT, Instant.now())
                .execute();
    }

    @Override
    public Optional<TimeOffCategory> findById(CategoryId id) {
        return dslContext.selectFrom(TIMEOFF_CATEGORY)
                .where(TIMEOFF_CATEGORY.ID.eq(id.uuid()))
                .fetchOptional(TimeOffCategoryJooqRepositoryImpl::mapTimeOffCategory);
    }

    @Override
    public List<TimeOffCategory> findAll() {
        return dslContext.selectFrom(TIMEOFF_CATEGORY)
                .fetch(TimeOffCategoryJooqRepositoryImpl::mapTimeOffCategory);
    }

    private static TimeOffCategory mapTimeOffCategory(JTimeoffCategoryRecord jTimeoffCategoryRecord) {
        return new TimeOffCategory(
                new CategoryId(jTimeoffCategoryRecord.getId()),
                jTimeoffCategoryRecord.getName(),
                jTimeoffCategoryRecord.getDescription(),
                jTimeoffCategoryRecord.getPaid(),
                jTimeoffCategoryRecord.getAutoCancellable()
        );
    }
}
