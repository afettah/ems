package com.tech.timeoff.exposition.cli;

import com.tech.employee.domain.EmployeeId;
import com.tech.shared.cli.TableDisplayMapper;
import com.tech.timeoff.DateRange;
import com.tech.timeoff.TimeOffFilter;
import com.tech.timeoff.TimeOffRequest;
import com.tech.timeoff.TimeOffService;
import com.tech.timeoff.category.CategoryId;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.time.LocalDate;
import java.util.UUID;

@ShellComponent
@AllArgsConstructor
@Slf4j
class TimeOffCommands {

    private final TimeOffService timeOffService;
    private final TableDisplayMapper tableDisplayMapper;

    @ShellMethod(key = "timeoff", value = "list all time off")
    public String listAllTimeOff(@ShellOption(defaultValue = ShellOption.NULL) String employeeId) {
        var timeOff = timeOffService.findAll(new TimeOffFilter(employeeId != null ? new EmployeeId(UUID.fromString(employeeId)) : null))
                .stream().map(TimeOffResponse::new)
                .toList();
        return tableDisplayMapper.display(timeOff,
                "id", "employeeId", "category", "startDate", "endDate", "status");
    }

    @ShellMethod(key = "timeoff request", value = "Request time off")
    public String requestTimeOff(@ShellOption(help = "employee id") UUID employeeId, @ShellOption(help = "category id") UUID categoryId, @ShellOption(help = "start date") LocalDate startDate, @ShellOption(help = "end date") LocalDate endDate) {
        try {
            timeOffService.request(new TimeOffRequest(new EmployeeId(employeeId), new CategoryId(categoryId), new DateRange(startDate, endDate), ""));
            return "Time off requested successfully.";
        } catch (Exception e) {
            return "Error requesting time off: " + e.getMessage();
        }
    }
}
