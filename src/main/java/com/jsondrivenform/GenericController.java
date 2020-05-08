package com.jsondrivenform;

import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Controller
public class GenericController {

    @Autowired
    TemplateParser templateParser;

    @GetMapping("/{uri}")
    public String loginPage(Model model, @PathVariable String uri) throws Exception {
        templateParser.validateURIJSON(uri);
        File file = Paths.get("json-schema", uri+".json").toFile();
        model.addAttribute("template",templateParser.pageDefinition(file));
        return "index";
    }

    @GetMapping("/process/{uri}")
    public String loginPage(Model model) throws IOException, TemplateException {
        File file = Paths.get("json-schema", "sample.json").toFile();
        String execute = templateParser.execute(file);
        model.addAttribute("JSONHtmlTemplate", execute);
        return "index";
    }

}
