package com.tech.timeoff.exposition.cli;

import com.tech.shared.cli.TableDisplayMapper;
import com.tech.timeoff.category.TimeOffCategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellMethod;

@AllArgsConstructor
@Slf4j
class TimeOffCommands {

    private final TimeOffCategoryService timeOffCategoryService;
    private final TableDisplayMapper TableDisplayMapper;

    @ShellMethod(key = "categories", value = "List all time off categories")
    public String listTimeOffCategories() {
        var categories = timeOffCategoryService.findAll();
        return TableDisplayMapper.display(categories, "id", "name", "description", "paid", "autoCancellable");
    }
}
