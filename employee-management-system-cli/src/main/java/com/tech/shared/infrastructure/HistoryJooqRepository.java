package com.tech.shared.infrastructure;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Table;
import org.jooq.TableRecord;
import org.jooq.impl.DSL;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HistoryJooqRepository {
    private final DSLContext dslContext;

    public HistoryJooqRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }


    /**
     * This method takes a table record, checks if a history table exists, and logs the creation of the record in the history table.
     *
     * @param tableRecord the table record to be historized
     * @param <T>         a type extending TableRecord
     */
    public <T extends TableRecord<T>> void historizeCreation(Table<?> historyTable, TableRecord<T> tableRecord) {
        // Collect changes (for a "creation", all fields are treated as new values)
        List<Change> changes = new ArrayList<>();
        for (Field<?> field : tableRecord.fields()) {
            Object fieldValue = tableRecord.get(field);
            changes.add(new Change(field.getName(), null, fieldValue != null ? fieldValue.toString() : null));
        }

        insertHistoryChanges(historyTable, "CREATE", changes);
    }

    public <T extends TableRecord<T>> void historizeUpdate(Table<?> historyTable, TableRecord<T> beforeRecord, TableRecord<T> afterRecord) {

        // Collect changes
        List<Change> changes = new ArrayList<>();
        for (Field<?> field : beforeRecord.fields()) {
            Object beforeValue = beforeRecord.get(field);
            Object afterValue = afterRecord.get(field);
            if (!Objects.equals(beforeValue, afterValue)) {
                changes.add(new Change(field.getName(), beforeValue != null ? beforeValue.toString() : null, afterValue != null ? afterValue.toString() : null));
            }
        }

        insertHistoryChanges(historyTable, "UPDATE", changes);
    }

    private void insertHistoryChanges(Table<?> historyTable, String action, List<Change> changes) {
        // Insert the history record into the history table
        dslContext.insertInto(historyTable)
                .set(DSL.field(DSL.name("username")), "system")
                .set(DSL.field(DSL.name("action")), action)
                .set(DSL.field(DSL.name("changes")), JsonbMapper.mapToJsonb(changes))
                .set(DSL.field(DSL.name("created_at")), Instant.now())
                .execute();
    }

    // Record for storing individual field changes
    public record Change(String field, String before, String after) {
    }
}

