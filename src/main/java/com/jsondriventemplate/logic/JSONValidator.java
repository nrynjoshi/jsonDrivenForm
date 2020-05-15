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

    public void process(String json) {
        String schemaFilePath = null;
        try (InputStream inputStream = new FileInputStream(schemaFilePath)) {
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            JSONObject jsonObject = new JSONObject(json);
            Schema schema = SchemaLoader.load(rawSchema);
            schema.validate(jsonObject);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
