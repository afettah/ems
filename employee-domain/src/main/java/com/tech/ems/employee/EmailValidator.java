package com.tech.ems.employee;

import lombok.experimental.UtilityClass;

@UtilityClass
class EmailValidator {

    private static final org.apache.commons.validator.routines.EmailValidator EMAIL_VALIDATOR = org.apache.commons.validator.routines.EmailValidator.getInstance();

    public static boolean isValidEmail(String email) {
        return EMAIL_VALIDATOR.isValid(email);
    }
}
