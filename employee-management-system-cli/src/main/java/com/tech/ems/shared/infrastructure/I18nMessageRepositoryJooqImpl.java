package com.tech.ems.shared.infrastructure;

import com.tech.ems.i18n.I18nMessageRepository;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;

import java.util.Locale;
import java.util.Optional;

import static com.tech.ems.jooq.generated.Tables.I18N_MESSAGE;

@AllArgsConstructor
class I18nMessageRepositoryJooqImpl implements I18nMessageRepository {
    private final DSLContext dslContext;

    @Override
    public Optional<String> getTranslation(String key, Locale locale) {
        return dslContext.select(I18N_MESSAGE.VALUE)
                .from(I18N_MESSAGE)
                .where(I18N_MESSAGE.KEY.eq(key))
                .and(I18N_MESSAGE.LOCALE.eq(locale.toString()))
                .fetchOptionalInto(String.class);
    }
}
