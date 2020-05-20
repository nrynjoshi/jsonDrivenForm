package com.jsondriventemplate.logic;

import com.jsondriventemplate.AppInject;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
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

    private Object scriptProcess(Map<String,Object> requestDTO) {
        String uri = (String) requestDTO.get("uri");
        switch ((String)requestDTO.get("type")) {
            case "delete":
                AppInject.mongoClientProvider.delete((String)requestDTO.get("_id"), uri);
                break;
            case "search":
                removeBeforeOperation(requestDTO);
                return AppInject.mongoClientProvider.search(requestDTO, uri);
            case "retrieve":
                return  AppInject.mongoClientProvider.findAll(uri);
            case "retrieveByID":
                return AppInject.mongoClientProvider.findById((String)requestDTO.get("id"), uri);
            default:
                removeBeforeOperation(requestDTO);
                AppInject.mongoClientProvider.save(requestDTO, uri);
        }
        return null;
    }

    private void removeBeforeOperation(Map<String, Object> requestDTO) {
        requestDTO.remove("type");
        requestDTO.remove("uri");
    }
}
