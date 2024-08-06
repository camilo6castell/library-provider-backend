package com.reactive;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;


@Component
public class JSONMapper implements IJSONMapper {

    Logger logger = Logger.getLogger("json-mapper-logger");


    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);


    @Override
    public String writeToJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException | ClassCastException e) {
            logger.warning(e.getMessage());
            return "Error writing to JSON";
        }
    }

    @Override
    public Object readFromJson(String json, Class<?> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException | ClassCastException e) {
            logger.warning(e.getMessage());
            return "Error reading from JSON";
        }
    }
}


//    private final ObjectMapper objectMapper;
//
//    public JSONMapper(ObjectMapper objectMapper) {
//        this.objectMapper = objectMapper;
//    }
//
//    @Override
//    public String serialize(Object object) throws JsonProcessingException {
//        return objectMapper.writeValueAsString(object);
//    }
//
//    @Override
//    public <T> T deserialize(String json, Class<T> clazz) throws JsonProcessingException {
//        return objectMapper.readValue(json, clazz);
//    }
//}