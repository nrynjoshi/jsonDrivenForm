package com.jsondriventemplate.app;

import com.jsondriventemplate.JSONTemplateConst;
import org.apache.commons.io.IOUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TestData {

    public static String readEmployeeJSON() throws IOException {
        File file = ResourceUtils.getFile("classpath:" + JSONTemplateConst.JSON_SCHEMA_ATTR + "employee.json");
        FileInputStream fisTargetFile = new FileInputStream(file);
        return IOUtils.toString(fisTargetFile, "UTF-8");
    }

}
