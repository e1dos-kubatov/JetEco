package comsep_23.JetEco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/basket")
    public String basket() {
        return "basket";
    }

    @GetMapping("/client-profile")
    public String clientProfile() {
        return "client-profile";
    }

    @GetMapping("/partner-profile")
    public String partnerProfile() {
        return "partners-profile";
    }

    @GetMapping("/review")
    public String review() {
        return "review";
    }
}