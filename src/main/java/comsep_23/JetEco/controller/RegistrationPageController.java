package comsep_23.JetEco.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class RegistrationPageController {

    @GetMapping("/register-business")
    public String showBusinessRegistrationPage() {
        return "register-business";
    }

    @GetMapping("/register")
    public String showClientRegistrationPage() {
        return "register";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
}
