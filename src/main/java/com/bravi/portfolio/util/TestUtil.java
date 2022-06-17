package com.bravi.portfolio.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class TestUtil {

    public static <T> String asJsonString(T obj) throws JsonProcessingException {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
        return objectMapper.writeValueAsString(obj);
    }

    public static <T> T deserializeJsonString(String obj, Class<T> classType) throws JsonProcessingException {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
        return objectMapper.readValue(obj, classType);
    }

}
