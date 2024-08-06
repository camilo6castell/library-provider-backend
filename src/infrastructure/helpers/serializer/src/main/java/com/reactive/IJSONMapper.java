package com.reactive;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IJSONMapper {
    String writeToJson(Object obj);
    Object readFromJson(String json, Class<?> clazz);

//    String serialize(Object object) throws JsonProcessingException;
//
//    <T> T deserialize(String json, Class<T> clazz) throws JsonProcessingException;

}
