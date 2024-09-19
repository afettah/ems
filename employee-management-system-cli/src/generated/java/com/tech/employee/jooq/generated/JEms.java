/*
 * This file is generated by jOOQ.
 */
package com.tech.employee.jooq.generated;


import com.tech.employee.jooq.generated.tables.JEmployeeHistory;
import com.tech.employee.jooq.generated.tables.JEmployeeTimeoff;
import com.tech.employee.jooq.generated.tables.JEmployees;
import com.tech.employee.jooq.generated.tables.JFlywaySchemaHistory;
import com.tech.employee.jooq.generated.tables.JTimeoffCategory;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JEms extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>ems</code>
     */
    public static final JEms EMS = new JEms();

    /**
     * The table <code>ems.employee_history</code>.
     */
    public final JEmployeeHistory EMPLOYEE_HISTORY = JEmployeeHistory.EMPLOYEE_HISTORY;

    /**
     * The table <code>ems.employee_timeoff</code>.
     */
    public final JEmployeeTimeoff EMPLOYEE_TIMEOFF = JEmployeeTimeoff.EMPLOYEE_TIMEOFF;

    /**
     * The table <code>ems.employees</code>.
     */
    public final JEmployees EMPLOYEES = JEmployees.EMPLOYEES;

    /**
     * The table <code>ems.flyway_schema_history</code>.
     */
    public final JFlywaySchemaHistory FLYWAY_SCHEMA_HISTORY = JFlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY;

    /**
     * The table <code>ems.timeoff_category</code>.
     */
    public final JTimeoffCategory TIMEOFF_CATEGORY = JTimeoffCategory.TIMEOFF_CATEGORY;

    /**
     * No further instances allowed
     */
    private JEms() {
        super("ems", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.asList(
            JEmployeeHistory.EMPLOYEE_HISTORY,
            JEmployeeTimeoff.EMPLOYEE_TIMEOFF,
            JEmployees.EMPLOYEES,
            JFlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY,
            JTimeoffCategory.TIMEOFF_CATEGORY
        );
    }
}
