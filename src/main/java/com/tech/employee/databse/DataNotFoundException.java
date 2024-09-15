package com.tech.employee.databse;

import lombok.Getter;

@Getter
public class DataNotFoundException extends RuntimeException {
    private final String key;
    private final String value;
    private final String table;

    public DataNotFoundException(String key, String value, String table) {
        super("Data not found with key " + key + " and value " + value + " in table " + table);
        this.key = key;
        this.value = value;
        this.table = table;
    }
}
