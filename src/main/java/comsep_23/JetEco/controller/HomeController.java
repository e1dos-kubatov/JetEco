package comsep_23.JetEco.controller;

import comsep_23.JetEco.entity.Client;
import comsep_23.JetEco.service.PartnerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class HomeController {

    private final PartnerService partnerService;

    public HomeController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal Client client, Model model) {
        if (client != null) {
            model.addAttribute("name", client.getName());
            model.addAttribute("picture", client.getPictureUrl());
            model.addAttribute("email", client.getEmail());
            model.addAttribute("role", client.getRole().name());
        } else {
            model.addAttribute("role", "ROLE_ANONYMOUS");
        }
        return "home";
    }



    @GetMapping("/basket")
    public String basket() {
        return "basket";
    }

    @GetMapping("/client-profile")
    public String clientProfile(@AuthenticationPrincipal OAuth2User oauthUser, Model model) {
        if (oauthUser != null) {
            String name = oauthUser.getAttribute("name");
            String picture = oauthUser.getAttribute("picture");
            String email = oauthUser.getAttribute("email");
            String phone = oauthUser.getAttribute("phone");
            String registeredAt = oauthUser.getAttribute("registeredAt");
            model.addAttribute("name", oauthUser.getAttribute("name"));
            model.addAttribute("picture", oauthUser.getAttribute("picture"));
            model.addAttribute("email", oauthUser.getAttribute("email"));
            model.addAttribute("phone", oauthUser.getAttribute("phone"));
            model.addAttribute("registeredAt", oauthUser.getAttribute("registeredAt"));
        }
        return "client-profile";
    }

    @GetMapping("/partner-profile")
    public String partnerProfile(@AuthenticationPrincipal OAuth2User oauthUser, Model model) {
        if (oauthUser != null) {
            String name = oauthUser.getAttribute("name");
            String picture = oauthUser.getAttribute("picture");
            String email = oauthUser.getAttribute("email");
            String phone = oauthUser.getAttribute("phone");
            model.addAttribute("name", oauthUser.getAttribute("name"));
            model.addAttribute("picture", oauthUser.getAttribute("picture"));
            model.addAttribute("email", oauthUser.getAttribute("email"));
            model.addAttribute("phone", oauthUser.getAttribute("phone"));
        }
        return "partners-profile";
    }

    @GetMapping("/review")
    public String review() {
        return "review";
    }

    @GetMapping("/partners")
    public String showPartners(Model model) {
        model.addAttribute("partners", partnerService.getAllPartners());
        return "partners";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about_us";
    }

    @GetMapping("/how-it-works")
    public String howItWorksPage() {
        return "how-it-works";
    }

    @GetMapping("/profile")
    public String redirectToProfilePage(OAuth2AuthenticationToken authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority("ROLE_CLIENT"))) {
            return "redirect:/client-profile";
        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_PARTNER"))) {
            return "redirect:/partner-profile";
        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "redirect:/admin";
        }

        return "redirect:/";
    }
}
