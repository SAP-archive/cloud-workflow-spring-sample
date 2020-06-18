package com.sap.api.workflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
public class HomeController {

    @GetMapping("/")
    @ApiIgnore
    public String home() {
        return "redirect:swagger-ui.html";
    }

}
