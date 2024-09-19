/*
 * This file is generated by jOOQ.
 */
package com.tech.ems.jooq.generated.tables;


import com.tech.ems.jooq.generated.JEms;
import com.tech.ems.jooq.generated.Keys;
import com.tech.ems.jooq.generated.tables.records.JI18nMessageRecord;

import java.util.Collection;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JI18nMessage extends TableImpl<JI18nMessageRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>ems.i18n_message</code>
     */
    public static final JI18nMessage I18N_MESSAGE = new JI18nMessage();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JI18nMessageRecord> getRecordType() {
        return JI18nMessageRecord.class;
    }

    /**
     * The column <code>ems.i18n_message.id</code>.
     */
    public final TableField<JI18nMessageRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>ems.i18n_message.key</code>.
     */
    public final TableField<JI18nMessageRecord, String> KEY = createField(DSL.name("key"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>ems.i18n_message.locale</code>.
     */
    public final TableField<JI18nMessageRecord, String> LOCALE = createField(DSL.name("locale"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>ems.i18n_message.value</code>.
     */
    public final TableField<JI18nMessageRecord, String> VALUE = createField(DSL.name("value"), SQLDataType.CLOB.nullable(false), this, "");

    private JI18nMessage(Name alias, Table<JI18nMessageRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private JI18nMessage(Name alias, Table<JI18nMessageRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>ems.i18n_message</code> table reference
     */
    public JI18nMessage(String alias) {
        this(DSL.name(alias), I18N_MESSAGE);
    }

    /**
     * Create an aliased <code>ems.i18n_message</code> table reference
     */
    public JI18nMessage(Name alias) {
        this(alias, I18N_MESSAGE);
    }

    /**
     * Create a <code>ems.i18n_message</code> table reference
     */
    public JI18nMessage() {
        this(DSL.name("i18n_message"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : JEms.EMS;
    }

    @Override
    public Identity<JI18nMessageRecord, Integer> getIdentity() {
        return (Identity<JI18nMessageRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<JI18nMessageRecord> getPrimaryKey() {
        return Keys.I18N_MESSAGE_PKEY;
    }

    @Override
    public JI18nMessage as(String alias) {
        return new JI18nMessage(DSL.name(alias), this);
    }

    @Override
    public JI18nMessage as(Name alias) {
        return new JI18nMessage(alias, this);
    }

    @Override
    public JI18nMessage as(Table<?> alias) {
        return new JI18nMessage(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public JI18nMessage rename(String name) {
        return new JI18nMessage(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JI18nMessage rename(Name name) {
        return new JI18nMessage(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public JI18nMessage rename(Table<?> name) {
        return new JI18nMessage(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JI18nMessage where(Condition condition) {
        return new JI18nMessage(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JI18nMessage where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JI18nMessage where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JI18nMessage where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JI18nMessage where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JI18nMessage where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JI18nMessage where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JI18nMessage where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JI18nMessage whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JI18nMessage whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
