package com.jsondriventemplate.controller;

import com.jsondriventemplate.AppInject;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class TemplateManipulateController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/editor")
    public String editor(Model model, @RequestParam(name = "query", defaultValue = "login", required = false) String query) throws Exception {
        AppInject.templateParser.validateURIJSON(query);
        File file = Paths.get("json-schema", query + ".json").toFile();
        String execute = AppInject.templateParser.pageDefinition(file);
        String unProcessedJSON = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

        model.addAttribute("JSONForm", execute);
        model.addAttribute("unProcessedJSON", unProcessedJSON);
        List jsonList = Files.walk(Paths.get("json-schema"))
                .filter(Files::isRegularFile)
                .map(path -> StringUtils.replace(path.getFileName().toString(), "json-schema\\", ""))
                .collect(Collectors.toList());
        model.addAttribute("jsonList", jsonList);

        return "admin/editor";
    }

    @PostMapping("/editor")
    public @ResponseBody
    String pageUpdate(@RequestBody MultiValueMap jsonMap) throws IOException, TemplateException {
        String json = (String) jsonMap.toSingleValueMap().get("json");
        if (StringUtils.isBlank(json)) {
            return "Non thing to display..";
        }
        return AppInject.templateParser.execute(json);
    }


}
