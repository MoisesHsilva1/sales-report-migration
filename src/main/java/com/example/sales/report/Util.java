package com.example.sales.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Util {

    @Autowired
    private final ObjectMapper objectMapper;

    public <T> T parseJsonToType(final String json, final Class<T> tClass) {
        T objectType = null;

        try {
            if (json != null) {
                String sanitizedJson = removeUtf8Bom(json);
                objectType = objectMapper.readValue(sanitizedJson, tClass);
            }
        } catch (Exception e) {
            e.getMessage();
        }

        return objectType;
    }

    private String removeUtf8Bom(String value) {
        if (value != null && !value.isEmpty() && value.charAt(0) == '\uFEFF') {
            return value.substring(1);
        }

        return value;
    }

}
