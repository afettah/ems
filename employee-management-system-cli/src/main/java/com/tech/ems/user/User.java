package com.tech.ems.user;

import lombok.Data;

import java.time.ZoneId;
import java.util.Locale;

@Data
public class User {
    private ZoneId zoneId = ZoneId.systemDefault();
    private Locale locale = Locale.getDefault();

    public Locale getLocale() {
        return locale;
    }
}
