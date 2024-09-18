/*
 * This file is generated by jOOQ.
 */
package com.tech.employee.jooq.generated.tables.records;


import com.tech.employee.jooq.generated.tables.JTimeoffCategory;

import java.time.Instant;
import java.util.UUID;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JTimeoffCategoryRecord extends UpdatableRecordImpl<JTimeoffCategoryRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>ems.timeoff_category.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>ems.timeoff_category.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>ems.timeoff_category.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>ems.timeoff_category.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>ems.timeoff_category.description</code>.
     */
    public void setDescription(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>ems.timeoff_category.description</code>.
     */
    public String getDescription() {
        return (String) get(2);
    }

    /**
     * Setter for <code>ems.timeoff_category.paid</code>.
     */
    public void setPaid(Boolean value) {
        set(3, value);
    }

    /**
     * Getter for <code>ems.timeoff_category.paid</code>.
     */
    public Boolean getPaid() {
        return (Boolean) get(3);
    }

    /**
     * Setter for <code>ems.timeoff_category.auto_cancellable</code>.
     */
    public void setAutoCancellable(Boolean value) {
        set(4, value);
    }

    /**
     * Getter for <code>ems.timeoff_category.auto_cancellable</code>.
     */
    public Boolean getAutoCancellable() {
        return (Boolean) get(4);
    }

    /**
     * Setter for <code>ems.timeoff_category.created_at</code>.
     */
    public void setCreatedAt(Instant value) {
        set(5, value);
    }

    /**
     * Getter for <code>ems.timeoff_category.created_at</code>.
     */
    public Instant getCreatedAt() {
        return (Instant) get(5);
    }

    /**
     * Setter for <code>ems.timeoff_category.updated_at</code>.
     */
    public void setUpdatedAt(Instant value) {
        set(6, value);
    }

    /**
     * Getter for <code>ems.timeoff_category.updated_at</code>.
     */
    public Instant getUpdatedAt() {
        return (Instant) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JTimeoffCategoryRecord
     */
    public JTimeoffCategoryRecord() {
        super(JTimeoffCategory.TIMEOFF_CATEGORY);
    }

    /**
     * Create a detached, initialised JTimeoffCategoryRecord
     */
    public JTimeoffCategoryRecord(UUID id, String name, String description, Boolean paid, Boolean autoCancellable, Instant createdAt, Instant updatedAt) {
        super(JTimeoffCategory.TIMEOFF_CATEGORY);

        setId(id);
        setName(name);
        setDescription(description);
        setPaid(paid);
        setAutoCancellable(autoCancellable);
        setCreatedAt(createdAt);
        setUpdatedAt(updatedAt);
        resetChangedOnNotNull();
    }
}
