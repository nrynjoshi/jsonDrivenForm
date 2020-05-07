package com.jsondrivenform;

import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller
public class TestController {

    @Autowired TemplateParser templateParser;


    @GetMapping("/")
    public String page(Model model) throws IOException, TemplateException {
        String execute = templateParser.execute(new File("C:\\narayan-joshi-files\\subodh\\jsondrivenform\\jsondrivenform\\json-schema\\form.json"));
        model.addAttribute("JSONForm",execute);
        String unProcessedJSON = FileUtils.readFileToString(new File("C:\\narayan-joshi-files\\subodh\\jsondrivenform\\jsondrivenform\\json-schema\\form.json"), StandardCharsets.UTF_8);
        model.addAttribute("unProcessedJSON",unProcessedJSON);
        return "test";
    }
}
