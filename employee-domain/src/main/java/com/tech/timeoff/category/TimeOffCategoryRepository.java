package com.tech.timeoff.category;

import java.util.List;
import java.util.Optional;

interface TimeOffCategoryRepository {
    void create(TimeOffCategory category);
    Optional<TimeOffCategory> findById(CategoryId id);
    List<TimeOffCategory> findAll();
}
