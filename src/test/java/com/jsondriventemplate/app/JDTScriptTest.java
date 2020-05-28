package com.jsondriventemplate.app;

import com.jsondriventemplate.AppInject;
import com.jsondriventemplate.constant.AppConst;
import com.jsondriventemplate.logic.JDTScript;
import com.jsondriventemplate.logic.JsonTemplateService;
import com.jsondriventemplate.repo.MongoClientProvider;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * certain portion of code will be tested
 */
public class JDTScriptTest {

    @BeforeClass
    public static void setUp() throws Exception {
        AppInject.templateService=mock(JsonTemplateService.class);
        AppInject.mongoClientProvider=mock(MongoClientProvider.class);
        AppInject.mongoClientProvider=mock(MongoClientProvider.class);
        when(AppInject.mongoClientProvider.search(any(), any())).thenReturn(Collections.EMPTY_LIST);
        when(AppInject.templateService.getJSONOnlyFromURI("employee")).thenReturn(TestData.readEmployeeJSON());
    }

    @Test
    public void test() throws Exception {
        setUp();
        JDTScript jdtScript=new JDTScript();
        Map<String,Object> requestDTO=new HashMap<>();
        requestDTO.put("type","search");
        requestDTO.put("uri", "employee");
        requestDTO.put(AppConst.ID, "");
        jdtScript.processAndReturn(requestDTO);
        System.out.println("test");
    }

}
