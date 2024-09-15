package com.tech.employee.databse;

import lombok.Getter;

@Getter
public class DuplicatedKeyException extends RuntimeException {
    private final String key;
    private final String value;
    private final String table;

    public DuplicatedKeyException(String key, String value, String table) {
        super("Duplicated key " + key + " with value " + value + " in table " + table);
        this.key = key;
        this.value = value;
        this.table = table;
    }
}
