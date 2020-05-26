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
public class JDTScript {

    private static final List<String> JDT_SCRIPT = Arrays.asList(
            "create", "update", "delete", "retrieve", "retrieveByID","search"
    );

    public void process(@NotNull Map<String,Object> requestDTO) throws Exception {
        processAndReturn(requestDTO);
    }

    public <T>T processAndReturn(@NotNull Map<String,Object> requestDTO) throws Exception {
        boolean isScriptProcessAble = JDT_SCRIPT.contains(requestDTO.get("type"));
        if (!isScriptProcessAble) {
            throw new Exception("provided script is not available within this system");
        }
        AppInject.templateService.getURIID((String) requestDTO.get("uri"));
        return (T) scriptProcess(requestDTO);
    }

    private Object scriptProcess(Map<String,Object> requestDTO) throws Exception {
        String uri = (String) requestDTO.get("uri");
        String type = (String) requestDTO.get("type");
        switch (type) {
            case "delete":
                AppInject.mongoClientProvider.delete((String)requestDTO.get("_id"), uri);
                break;
            case "search":
                validation(uri,type,requestDTO);
                removeBeforeOperation(requestDTO);
                return AppInject.mongoClientProvider.search(requestDTO, uri);
            case "retrieve":
                return  AppInject.mongoClientProvider.findAll(uri);
            case "retrieveByID":
                return AppInject.mongoClientProvider.findById((String)requestDTO.get("_id"), uri);
            default:
                validation(uri,type,requestDTO);
                removeBeforeOperation(requestDTO);
                AppInject.mongoClientProvider.save(requestDTO, uri);
        }
        return null;
    }

    private void removeBeforeOperation(Map<String, Object> requestDTO) {
        requestDTO.remove("type");
        requestDTO.remove("uri");
    }

    private void validation(String uri,String type,Map<String,Object> dataMap) throws Exception {
        String jsonData = AppInject.templateService.getJSONOnlyFromURI(uri);
        LinkedHashMap jsonSchema = JsonPath.parse(jsonData).read("$['definitions']['"+type+"']['definitions']['fields']");

        JSONObject schema = new JSONObject(jsonSchema);
        StringBuilder error=new StringBuilder();
        for(String key : schema.keySet()){
            JSONObject value = (JSONObject) schema.get(key);
            if(value.has("required")){
                Boolean required = value.getBoolean("required");
                if((required && StringUtils.isBlank((CharSequence) dataMap.get(key)))){
                    error.append(key).append(" is a required field.");
                }
            }
            if(value.has("validation_regx") && StringUtils.isNotBlank(value.getString("validation_regx"))){
                String validation_regx = value.getString("validation_regx");
                boolean matches = ((String) dataMap.get(key)).matches(validation_regx);
                if(!matches ){
                    error.append(key).append(" does not contain valid data.");
                }
            }
        }
        if(StringUtils.isNotBlank(error.toString())){
            throw new ValidationException(error.toString());
        }
    }
}
