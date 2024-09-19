package com.tech.ems.i18n;

import java.util.Locale;
import java.util.Optional;

public interface I18nMessageRepository {
    Optional<String> getTranslation(String key, Locale locale);
}
