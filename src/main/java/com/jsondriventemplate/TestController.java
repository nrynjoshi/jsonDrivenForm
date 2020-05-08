package com.jsondriventemplate;

import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

@Controller
public class TestController {

    @Autowired
    TemplateParser templateParser;

    @GetMapping("/")
    public String page(Model model, @RequestParam(name = "query",defaultValue = "sample",required = false) String query) throws Exception {
        templateParser.validateURIJSON(query);
        File file = Paths.get("json-schema", query+".json").toFile();
        String execute = templateParser.execute(file);
        String unProcessedJSON = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

        model.addAttribute("JSONForm", execute);
        model.addAttribute("unProcessedJSON", unProcessedJSON);
        return "test";
    }



    @PostMapping("/")
    public @ResponseBody
    String pageUpdate(@RequestBody MultiValueMap jsonMap) throws IOException, TemplateException {
        String json= (String) jsonMap.toSingleValueMap().get("json");
        if (StringUtils.isBlank(json)) {
            return "test";
        }
        String  execute = templateParser.execute(json);
        return execute;
    }


}
