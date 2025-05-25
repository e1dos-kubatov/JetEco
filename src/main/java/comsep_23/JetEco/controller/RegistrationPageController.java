package comsep_23.JetEco.controller;


import comsep_23.JetEco.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegistrationPageController {

    private final CustomUserDetailsService customUserDetailsService;

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
