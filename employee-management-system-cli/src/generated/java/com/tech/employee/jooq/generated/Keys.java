/*
 * This file is generated by jOOQ.
 */
package com.tech.employee.jooq.generated;


import com.tech.employee.jooq.generated.tables.JEmployees;
import com.tech.employee.jooq.generated.tables.records.JEmployeesRecord;

import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in ems.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<JEmployeesRecord> EMPLOYEES_PKEY = Internal.createUniqueKey(JEmployees.EMPLOYEES, DSL.name("employees_pkey"), new TableField[] { JEmployees.EMPLOYEES.ID }, true);
}
