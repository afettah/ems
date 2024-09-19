package com.tech.ems.preferences.cli;

import com.tech.ems.shared.user.User;
import com.tech.ems.shared.user.UserContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.time.ZoneId;

@ShellComponent
@AllArgsConstructor
@Slf4j
class UserPreferencesCommands {

    @ShellMethod(key = "timezone", value = "Get/Set the user's timezone. Example: set-timezone UTC+2")
    public String timezone(@ShellOption(defaultValue = ShellOption.NULL) String timezone) {
        try {
            if (timezone != null) {
                User user = UserContext.getOrSetNew();
                user.setZoneId(ZoneId.of(timezone));
            }
            return UserContext.getOrSetNew().getZoneId().getId();
        } catch (Exception e) {
            return "Error setting timezone: " + e.getMessage();
        }
    }
}
