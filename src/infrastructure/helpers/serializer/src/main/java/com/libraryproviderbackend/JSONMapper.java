package com.libraryproviderbackend;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * JSON serialization/deserialization utility for domain events.
 * Uses Jackson with field-level visibility so that encapsulated (private) fields
 * are correctly serialized without requiring public getters on the event classes.
 */
@Component
public class JSONMapper implements IJSONMapper {

    private static final Logger log = LoggerFactory.getLogger(JSONMapper.class);

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Override
    public String writeToJson(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize object of type {}: {}", obj.getClass().getName(), e.getMessage(), e);
            throw new SerializationException("Failed to serialize event", e);
        }
    }

    @Override
    public Object readFromJson(String json, Class<?> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize JSON to type {}: {}", clazz.getName(), e.getMessage(), e);
            throw new SerializationException("Failed to deserialize event", e);
        }
    }
}
