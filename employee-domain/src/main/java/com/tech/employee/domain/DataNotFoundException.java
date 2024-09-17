package com.tech.employee.domain;

import lombok.Getter;

@Getter
public class DataNotFoundException extends RuntimeException {
    private final String entity;
    private final Object id;

    public DataNotFoundException(final String entity, final Object id) {
        super(entity + " with id " + id + " not found");
        this.entity = entity;
        this.id = id;
    }
}
