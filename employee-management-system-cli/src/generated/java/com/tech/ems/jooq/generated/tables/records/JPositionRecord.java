/*
 * This file is generated by jOOQ.
 */
package com.tech.ems.jooq.generated.tables.records;


import com.tech.ems.jooq.generated.tables.JPosition;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JPositionRecord extends UpdatableRecordImpl<JPositionRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>ems.position.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>ems.position.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>ems.position.code</code>.
     */
    public void setCode(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>ems.position.code</code>.
     */
    public String getCode() {
        return (String) get(1);
    }

    /**
     * Setter for <code>ems.position.name</code>.
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>ems.position.name</code>.
     */
    public String getName() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JPositionRecord
     */
    public JPositionRecord() {
        super(JPosition.POSITION);
    }

    /**
     * Create a detached, initialised JPositionRecord
     */
    public JPositionRecord(Integer id, String code, String name) {
        super(JPosition.POSITION);

        setId(id);
        setCode(code);
        setName(name);
        resetChangedOnNotNull();
    }
}