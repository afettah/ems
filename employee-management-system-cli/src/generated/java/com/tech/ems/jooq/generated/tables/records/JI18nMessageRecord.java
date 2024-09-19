/*
 * This file is generated by jOOQ.
 */
package com.tech.ems.jooq.generated.tables.records;


import com.tech.ems.jooq.generated.tables.JI18nMessage;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JI18nMessageRecord extends UpdatableRecordImpl<JI18nMessageRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>ems.i18n_message.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>ems.i18n_message.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>ems.i18n_message.key</code>.
     */
    public void setKey(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>ems.i18n_message.key</code>.
     */
    public String getKey() {
        return (String) get(1);
    }

    /**
     * Setter for <code>ems.i18n_message.locale</code>.
     */
    public void setLocale(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>ems.i18n_message.locale</code>.
     */
    public String getLocale() {
        return (String) get(2);
    }

    /**
     * Setter for <code>ems.i18n_message.value</code>.
     */
    public void setValue(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>ems.i18n_message.value</code>.
     */
    public String getValue() {
        return (String) get(3);
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
     * Create a detached JI18nMessageRecord
     */
    public JI18nMessageRecord() {
        super(JI18nMessage.I18N_MESSAGE);
    }

    /**
     * Create a detached, initialised JI18nMessageRecord
     */
    public JI18nMessageRecord(Integer id, String key, String locale, String value) {
        super(JI18nMessage.I18N_MESSAGE);

        setId(id);
        setKey(key);
        setLocale(locale);
        setValue(value);
        resetChangedOnNotNull();
    }
}