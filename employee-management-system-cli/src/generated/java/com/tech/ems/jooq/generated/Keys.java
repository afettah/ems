/*
 * This file is generated by jOOQ.
 */
package com.tech.ems.jooq.generated;


import com.tech.ems.jooq.generated.tables.JEmployee;
import com.tech.ems.jooq.generated.tables.JEmployeeHistory;
import com.tech.ems.jooq.generated.tables.JEmployeeTimeoff;
import com.tech.ems.jooq.generated.tables.JFlywaySchemaHistory;
import com.tech.ems.jooq.generated.tables.JTimeoffCategory;
import com.tech.ems.jooq.generated.tables.records.JEmployeeHistoryRecord;
import com.tech.ems.jooq.generated.tables.records.JEmployeeRecord;
import com.tech.ems.jooq.generated.tables.records.JEmployeeTimeoffRecord;
import com.tech.ems.jooq.generated.tables.records.JFlywaySchemaHistoryRecord;
import com.tech.ems.jooq.generated.tables.records.JTimeoffCategoryRecord;

import org.jooq.ForeignKey;
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

    public static final UniqueKey<JEmployeeRecord> EMPLOYEE_PKEY = Internal.createUniqueKey(JEmployee.EMPLOYEE, DSL.name("employee_pkey"), new TableField[] { JEmployee.EMPLOYEE.ID }, true);
    public static final UniqueKey<JEmployeeHistoryRecord> EMPLOYEE_HISTORY_PKEY = Internal.createUniqueKey(JEmployeeHistory.EMPLOYEE_HISTORY, DSL.name("employee_history_pkey"), new TableField[] { JEmployeeHistory.EMPLOYEE_HISTORY.ID }, true);
    public static final UniqueKey<JEmployeeTimeoffRecord> EMPLOYEE_TIMEOFF_PKEY = Internal.createUniqueKey(JEmployeeTimeoff.EMPLOYEE_TIMEOFF, DSL.name("employee_timeoff_pkey"), new TableField[] { JEmployeeTimeoff.EMPLOYEE_TIMEOFF.ID }, true);
    public static final UniqueKey<JFlywaySchemaHistoryRecord> FLYWAY_SCHEMA_HISTORY_PK = Internal.createUniqueKey(JFlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, DSL.name("flyway_schema_history_pk"), new TableField[] { JFlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.INSTALLED_RANK }, true);
    public static final UniqueKey<JTimeoffCategoryRecord> TIMEOFF_CATEGORY_PKEY = Internal.createUniqueKey(JTimeoffCategory.TIMEOFF_CATEGORY, DSL.name("timeoff_category_pkey"), new TableField[] { JTimeoffCategory.TIMEOFF_CATEGORY.ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<JEmployeeTimeoffRecord, JTimeoffCategoryRecord> EMPLOYEE_TIMEOFF__EMPLOYEE_TIMEOFF_CATEGORY_FKEY = Internal.createForeignKey(JEmployeeTimeoff.EMPLOYEE_TIMEOFF, DSL.name("employee_timeoff_category_fkey"), new TableField[] { JEmployeeTimeoff.EMPLOYEE_TIMEOFF.CATEGORY_ID }, Keys.TIMEOFF_CATEGORY_PKEY, new TableField[] { JTimeoffCategory.TIMEOFF_CATEGORY.ID }, true);
    public static final ForeignKey<JEmployeeTimeoffRecord, JEmployeeRecord> EMPLOYEE_TIMEOFF__EMPLOYEE_TIMEOFF_EMPLOYEE_ID_FKEY = Internal.createForeignKey(JEmployeeTimeoff.EMPLOYEE_TIMEOFF, DSL.name("employee_timeoff_employee_id_fkey"), new TableField[] { JEmployeeTimeoff.EMPLOYEE_TIMEOFF.EMPLOYEE_ID }, Keys.EMPLOYEE_PKEY, new TableField[] { JEmployee.EMPLOYEE.ID }, true);
}