package com.jsondriventemplate.app;

import com.jsondriventemplate.constant.JSONTemplateConst;
import org.apache.commons.io.IOUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TestData {

    public static String readEmployeeJSON() throws IOException {
        File file = ResourceUtils.getFile("classpath:" + JSONTemplateConst.JSON_SCHEMA_ATTR + "employee.json");
        FileInputStream fisTargetFile = new FileInputStream(file);
        return IOUtils.toString(fisTargetFile, StandardCharsets.UTF_8);
    }

}
