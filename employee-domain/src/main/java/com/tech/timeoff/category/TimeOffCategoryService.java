package com.tech.timeoff.category;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TimeOffCategoryService {

    private final TimeOffCategoryRepository timeOffCategoryRepository;

    public TimeOffCategory createCategory(String name, String description, boolean paid, boolean autoCancellable) {
        TimeOffCategory category = TimeOffCategory.create(name, description, paid, autoCancellable);
        timeOffCategoryRepository.create(category);
        return category;
    }

    public List<TimeOffCategory> findAll() {
        return timeOffCategoryRepository.findAll();
    }
}
