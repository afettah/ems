package com.tech.ems.i18n;

import java.util.Locale;
import java.util.Optional;

public class I18nMessageService {

    private final I18nMessageRepository i18nMessageRepository;

    public I18nMessageService(I18nMessageRepository i18nMessageRepository) {
        this.i18nMessageRepository = i18nMessageRepository;
    }

    public Optional<String> getMessage(String key, Locale locale) {
        return i18nMessageRepository.getTranslation(key, locale);
    }
}
