package comsep_23.JetEco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrationPageController {

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // templates/register.html
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // templates/login.html
    }

    @GetMapping("/business-signup")
    public String showBusinessSignupPage() {
        return "business-signup"; // templates/business-signup.html
    }
}


