package com.tech.timeoff.exposition.cli;

import com.tech.shared.cli.TableDisplayMapper;
import com.tech.timeoff.category.TimeOffCategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
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


    @ShellMethod(key = "categories generate", value = "Generate time off categories")
    public String generateTimeOffCategories() {
        timeOffCategoryService.createCategory("Annual Leave", "Paid time off work", true, false);
        timeOffCategoryService.createCategory("Remote Work", "Work from home", false, true);
        timeOffCategoryService.createCategory("Sick Leave", "Paid time off work due to illness", true, false);
        return "Time off categories generated.";
    }

    @ShellMethod(key = "categories create", value = "Create a new time off category")
    public String createTimeOffCategory(String name, @ShellOption(defaultValue = ShellOption.NULL) String description, @ShellOption(defaultValue = "true") boolean paid, @ShellOption(defaultValue = "false") boolean autoCancellable) {
        timeOffCategoryService.createCategory(name, description, paid, autoCancellable);
        return "Time off category created successfully.";
    }
}
