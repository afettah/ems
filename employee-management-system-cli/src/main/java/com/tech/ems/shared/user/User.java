package com.tech.ems.shared.user;

import lombok.Data;

import java.time.ZoneId;

@Data
public class User {
    private ZoneId zoneId = ZoneId.systemDefault();
}
