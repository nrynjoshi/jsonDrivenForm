package com.jsondrivenform;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

@Controller
public class JSONFormController {

    @GetMapping
    public String jsonForm(Model model) throws IOException {
        model.addAttribute("json_form_defination", FileUtils.readFileToString(Paths.get("json-schema","form.json").toFile(), StandardCharsets.UTF_8));
        return "test";
    }

}
