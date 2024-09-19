package com.tech.ems.shared.user;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserContext {
    private static final ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public static User getOrSetNew() {
        User user = userThreadLocal.get();
        if (user == null) {
            user = new User();
            userThreadLocal.set(user);
        }
        return user;
    }

    public static void set(User user) {
        userThreadLocal.set(user);
    }

    public static void clear() {
        userThreadLocal.remove();
    }
}
