package ua.dolofinskyi.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    @GetMapping("/app")
    public String app(Model model) {
        return "app";
    }
}
