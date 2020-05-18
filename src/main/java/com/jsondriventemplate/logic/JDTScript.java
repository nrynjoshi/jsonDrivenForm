package com.jsondriventemplate.logic;

import com.jsondriventemplate.AppInject;
import com.jsondriventemplate.dto.RequestDTO;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@Component
public class JDTScript {

    private static final List<String> JDT_SCRIPT = Arrays.asList(
            "create", "update", "delete", "retrieve", "retrieveByID","search"
    );

    public void process(@NotNull RequestDTO requestDTO) throws Exception {
        processAndReturn(requestDTO);
    }

    public <T>T processAndReturn(@NotNull RequestDTO requestDTO) throws Exception {
        boolean isScriptProcessAble = JDT_SCRIPT.contains(requestDTO.getScript());
        if (!isScriptProcessAble) {
            throw new Exception("provided script is not available within this system");
        }
        AppInject.templateService.getURIID(requestDTO.getUri());
        return (T) scriptProcess(requestDTO);
    }

    private Object scriptProcess(RequestDTO requestDTO) {
        switch (requestDTO.getScript()) {
            case "delete":
                AppInject.mongoClientProvider.delete(requestDTO.getId(), requestDTO.getUri());
                break;
            case "search":
                return AppInject.mongoClientProvider.search(requestDTO.getData(), requestDTO.getUri());
            case "retrieve":
                return  AppInject.mongoClientProvider.findAll(requestDTO.getUri());
            case "retrieveByID":
                return AppInject.mongoClientProvider.findById(requestDTO.getId(), requestDTO.getUri());
            default:
                AppInject.mongoClientProvider.save(requestDTO.getData(), requestDTO.getUri());
        }
        return null;
    }
}
