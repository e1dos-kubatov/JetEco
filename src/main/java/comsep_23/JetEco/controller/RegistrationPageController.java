package comsep_23.JetEco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrationPageController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Добро пожаловать на сайт!");
        return "index"; // ищет шаблон register.html в папке templates
    }
}

