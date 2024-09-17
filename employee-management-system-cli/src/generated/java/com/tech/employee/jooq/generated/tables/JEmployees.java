/*
 * This file is generated by jOOQ.
 */
package com.tech.employee.jooq.generated.tables;


import com.tech.employee.infrastructure.InstantConverter;
import com.tech.employee.jooq.generated.JEms;
import com.tech.employee.jooq.generated.Keys;
import com.tech.employee.jooq.generated.tables.records.JEmployeesRecord;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.JSONB;
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
public class JEmployees extends TableImpl<JEmployeesRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>ems.employees</code>
     */
    public static final JEmployees EMPLOYEES = new JEmployees();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JEmployeesRecord> getRecordType() {
        return JEmployeesRecord.class;
    }

    /**
     * The column <code>ems.employees.id</code>.
     */
    public final TableField<JEmployeesRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>ems.employees.email</code>.
     */
    public final TableField<JEmployeesRecord, String> EMAIL = createField(DSL.name("email"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>ems.employees.name</code>.
     */
    public final TableField<JEmployeesRecord, String> NAME = createField(DSL.name("name"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>ems.employees.position</code>.
     */
    public final TableField<JEmployeesRecord, String> POSITION = createField(DSL.name("position"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>ems.employees.salary</code>.
     */
    public final TableField<JEmployeesRecord, JSONB> SALARY = createField(DSL.name("salary"), SQLDataType.JSONB, this, "");

    /**
     * The column <code>ems.employees.created_at</code>.
     */
    public final TableField<JEmployeesRecord, Instant> CREATED_AT = createField(DSL.name("created_at"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false), this, "", new InstantConverter());

    /**
     * The column <code>ems.employees.updated_at</code>.
     */
    public final TableField<JEmployeesRecord, Instant> UPDATED_AT = createField(DSL.name("updated_at"), SQLDataType.TIMESTAMPWITHTIMEZONE(6), this, "", new InstantConverter());

    private JEmployees(Name alias, Table<JEmployeesRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private JEmployees(Name alias, Table<JEmployeesRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>ems.employees</code> table reference
     */
    public JEmployees(String alias) {
        this(DSL.name(alias), EMPLOYEES);
    }

    /**
     * Create an aliased <code>ems.employees</code> table reference
     */
    public JEmployees(Name alias) {
        this(alias, EMPLOYEES);
    }

    /**
     * Create a <code>ems.employees</code> table reference
     */
    public JEmployees() {
        this(DSL.name("employees"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : JEms.EMS;
    }

    @Override
    public UniqueKey<JEmployeesRecord> getPrimaryKey() {
        return Keys.EMPLOYEES_PKEY;
    }

    @Override
    public JEmployees as(String alias) {
        return new JEmployees(DSL.name(alias), this);
    }

    @Override
    public JEmployees as(Name alias) {
        return new JEmployees(alias, this);
    }

    @Override
    public JEmployees as(Table<?> alias) {
        return new JEmployees(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public JEmployees rename(String name) {
        return new JEmployees(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JEmployees rename(Name name) {
        return new JEmployees(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public JEmployees rename(Table<?> name) {
        return new JEmployees(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JEmployees where(Condition condition) {
        return new JEmployees(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JEmployees where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JEmployees where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JEmployees where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JEmployees where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JEmployees where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JEmployees where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JEmployees where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JEmployees whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JEmployees whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}