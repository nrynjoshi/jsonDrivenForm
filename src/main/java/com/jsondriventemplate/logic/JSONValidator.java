package com.jsondriventemplate.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsondriventemplate.exception.JSONValidationException;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Component
public class JSONValidator {

    public String validJSONFormat(String json) throws JSONValidationException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(json);
            return jsonNode.toString();
        } catch (Exception x) {
            x.printStackTrace();
            throw new JSONValidationException("not a valid json data");
        }

    }

}
