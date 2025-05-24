package comsep_23.JetEco.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal OAuth2User oauthUser, Model model) {
        if (oauthUser != null) {
            String name = oauthUser.getAttribute("name");
            String picture = oauthUser.getAttribute("picture");
            String email = oauthUser.getAttribute("email");
            model.addAttribute("name", name);
            model.addAttribute("picture", picture);
            model.addAttribute("email", email);
        }
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
