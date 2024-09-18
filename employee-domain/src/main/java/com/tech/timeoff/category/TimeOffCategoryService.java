package com.tech.timeoff.category;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class TimeOffCategoryService {

    private final TimeOffCategoryRepository timeOffCategoryRepository;

    public TimeOffCategory createCategory(String name, String description, boolean paid) {
        TimeOffCategory category = TimeOffCategory.create(name, description, paid);
        timeOffCategoryRepository.create(category);
        return category;
    }

    public Optional<TimeOffCategory> findById(CategoryId id) {
        return timeOffCategoryRepository.findById(id);
    }

    public List<TimeOffCategory> findAll() {
        return timeOffCategoryRepository.findAll();
    }
}
