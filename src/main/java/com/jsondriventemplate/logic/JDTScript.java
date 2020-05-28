package com.jsondriventemplate.logic;

import com.jayway.jsonpath.JsonPath;
import com.jsondriventemplate.AppInject;
import com.jsondriventemplate.exception.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public final class JDTScript {

    private static final String MASK_PASSWORD = "passwordCannotBeVisible";

    private static final List<String> JDT_SCRIPT = Arrays.asList(
            "create", "update", "delete", "retrieve", "retrieveByID", "search"
    );

    public void process(@NotNull Map<String, Object> requestDTO) throws Exception {
        processAndReturn(requestDTO);
    }

    public <T> T processAndReturn(@NotNull Map<String, Object> requestDTO) throws Exception {
        boolean isScriptProcessAble = JDT_SCRIPT.contains(requestDTO.get("type"));
        if (!isScriptProcessAble) {
            throw new Exception("provided script is not available within this system");
        }
        AppInject.templateService.getURIID((String) requestDTO.get("uri"));
        return (T) scriptProcess(requestDTO);
    }

    private Object scriptProcess(Map<String, Object> requestDTO) throws Exception {
        String uri = (String) requestDTO.get("uri");
        String type = (String) requestDTO.get("type");
        String id = (String) requestDTO.get("_id");
        removeBeforeOperation(requestDTO);
        switch (type) {
            case "delete":
                AppInject.mongoClientProvider.delete(id, uri);
                break;
            case "search":
                validation(uri, type, requestDTO);
                return AppInject.mongoClientProvider.search(requestDTO, uri);
            case "retrieve":
                return AppInject.mongoClientProvider.findAll(uri);
            case "retrieveByID":
                Map record = AppInject.mongoClientProvider.findById(id, uri);
                if (record.containsKey("password")) {
                    record.put("password", MASK_PASSWORD);
                }
                return record;
            default:
                validation(uri, type, requestDTO);
                checkUserNamePassword(uri, type, requestDTO);
                AppInject.mongoClientProvider.save(requestDTO, uri);
        }
        return null;
    }

    private void removeBeforeOperation(Map<String, Object> requestDTO) {
        requestDTO.remove("type");
        requestDTO.remove("uri");
    }

    private void checkUserNamePassword(String uri, String type, Map<String, Object> dataMap) throws ValidationException {

        if (type.equals("create")) {
            if (dataMap.containsKey("username")) {

                Map user = AppInject.mongoClientProvider.findByAtt("username", (String) dataMap.get("username"), uri);
                if (user != null && !user.isEmpty()) {
                    throw new ValidationException("username already exits");
                }
            }
            if (dataMap.containsKey("password")) {
                String encodedPassword = AppInject.passwordEncoder.encode((String) dataMap.get("password"));
                dataMap.put("password", encodedPassword);
            }
        }
        if (type.equals("update")) {
            Map user = null;
            if (dataMap.containsKey("username")) {
                user = AppInject.mongoClientProvider.findByAtt("username", (String) dataMap.get("username"), uri);
                if (user != null && !user.isEmpty()) {
                    String _id = (String) user.get("_id");
                    if (!dataMap.containsKey("_id") || !_id.equals(dataMap.get("_id"))) {
                        throw new ValidationException("username already exits");
                    }
                }
            }
            if (dataMap.containsKey("password")) {
                if (user != null && !user.isEmpty()) {
                    String dbpassword = (String) user.get("password");
                    if (!dbpassword.equals(dataMap.get("password")) && !StringUtils.equalsAnyIgnoreCase((String) dataMap.get("password"), MASK_PASSWORD)) {
                        String encodedPassword = AppInject.passwordEncoder.encode((String) dataMap.get("password"));
                        dataMap.put("password", encodedPassword);
                    }else{
                        dataMap.put("password",dbpassword);
                    }
                }
            }
            if (user.containsKey("role")) {
                dataMap.put("role", user.get("role"));
            }
        }


    }

    private void validation(String uri, String type, Map<String, Object> dataMap) throws Exception {
        String jsonData = AppInject.templateService.getJSONOnlyFromURI(uri);
        LinkedHashMap jsonSchema = JsonPath.parse(jsonData).read("$['definitions']['" + type + "']['definitions']['fields']");

        JSONObject schema = new JSONObject(jsonSchema);
        StringBuilder error = new StringBuilder();
        for (String key : schema.keySet()) {
            JSONObject value = (JSONObject) schema.get(key);
            if (value.has("required")) {
                Boolean required = value.getBoolean("required");
                if ((required && StringUtils.isBlank((CharSequence) dataMap.get(key)))) {
                    error.append(key).append(" is a required field.");
                }
            }
            if (value.has("validation_regx") && StringUtils.isNotBlank(value.getString("validation_regx"))) {
                String validation_regx = value.getString("validation_regx");
                boolean matches = ((String) dataMap.get(key)).matches(validation_regx);
                if (!matches) {
                    error.append(key).append(" does not contain valid data.");
                }
            }
        }
        if (StringUtils.isNotBlank(error.toString())) {
            throw new ValidationException(error.toString());
        }
    }
}
