package com.tech.ems.preferences.cli;

import com.tech.ems.shared.cli.TableDisplayMapper;
import com.tech.ems.shared.user.User;
import com.tech.ems.shared.user.UserContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.time.ZoneId;
import java.util.List;
import java.util.Locale;

@ShellComponent
@AllArgsConstructor
@Slf4j
class UserPreferencesCommands {
    private final TableDisplayMapper tableDisplayMapper;

    @ShellMethod(key = "timezone", value = "Get/Set the user's timezone. Example: timezone UTC+2")
    public String timezone(@ShellOption(defaultValue = ShellOption.NULL) String timezone) {
        try {
            if (timezone != null) {
                User user = UserContext.getOrSetNew();
                user.setZoneId(ZoneId.of(timezone));
            }
            String userValue = UserContext.getOrSetNew().getZoneId().getId();
            String serverValue = ZoneId.systemDefault().getId();
            return tableDisplayMapper.display(List.of(new PreferencesResponse(userValue, serverValue)), "user", "server");
        } catch (Exception e) {
            return "Error setting timezone: " + e.getMessage();
        }
    }

    @ShellMethod(key = "local", value = "Get/Set the user's local. Example: local en_US")
    public String local(@ShellOption(defaultValue = ShellOption.NULL) String local) {
        try {
            if (local != null) {
                User user = UserContext.getOrSetNew();
                user.setLocale(LocaleUtils.toLocale(local));
            }
            String userValue = UserContext.getOrSetNew().getLocale().toString();
            String serverValue = Locale.getDefault().toString();
            return tableDisplayMapper.display(List.of(new PreferencesResponse(userValue, serverValue)), "user", "server");
        } catch (Exception e) {
            return "Error setting local: " + e.getMessage();
        }
    }

    record PreferencesResponse(String user, String server) {
    }
}
