package com.tech.ems.timeoff;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String categoryId) {
        super("Category not found: " + categoryId);
    }
}
