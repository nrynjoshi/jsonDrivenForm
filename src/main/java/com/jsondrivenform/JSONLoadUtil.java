package com.jsondrivenform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JSONLoadUtil {

    protected static String laodFormDefinition(File jsonFile) throws IOException {
        return FileUtils.readFileToString(jsonFile, StandardCharsets.UTF_8);
    }

    protected static Map<String, Object> mapper(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map
                = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {

        });
        return map;
    }

}
