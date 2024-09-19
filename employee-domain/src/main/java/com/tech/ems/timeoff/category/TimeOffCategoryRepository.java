package com.tech.ems.timeoff.category;

import java.util.List;
import java.util.Optional;

public interface TimeOffCategoryRepository {
    void create(TimeOffCategory category);
    Optional<TimeOffCategory> findById(CategoryId id);
    List<TimeOffCategory> findAll();
}
