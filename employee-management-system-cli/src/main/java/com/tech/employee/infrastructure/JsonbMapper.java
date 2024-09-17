package com.tech.employee.infrastructure;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;
import org.jooq.JSONB;

import java.io.UncheckedIOException;
import java.util.List;
import java.util.Optional;

@UtilityClass
public final class JsonbMapper {

    public static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.registerModule(new Jdk8Module());
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NON_PRIVATE);
        OBJECT_MAPPER.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NON_PRIVATE);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public static JSONB mapToJsonb(Object o) {
        if (o == null) {
            return null;
        }
        try {
            return JSONB.jsonb(OBJECT_MAPPER.writeValueAsString(o));
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException("Could not serialize ", e);
        }
    }

    public static <T> List<T> mapListFromJsonb(JSONB jsonb, Class<T> cls) {
        try {
            return OBJECT_MAPPER.readerForListOf(cls).readValue(from(jsonb));
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException("Could not deserialize to %s".formatted(cls), e);
        }
    }

    public static <T> T mapFromJsonb(JSONB jsonb, Class<T> cls) {
        try {
            return OBJECT_MAPPER.readValue(from(jsonb), cls);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException("Could not deserialize to %s".formatted(cls), e);
        }
    }

    public static String from(JSONB jsonb) {
        return Optional.ofNullable(jsonb).map(JSONB::data).orElse("null");
    }
}
