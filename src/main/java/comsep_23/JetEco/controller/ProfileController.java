package comsep_23.JetEco.controller;

import comsep_23.JetEco.entity.Client;
import comsep_23.JetEco.entity.Partner;
import comsep_23.JetEco.service.ClientService;
import comsep_23.JetEco.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final ClientService clientService;
    private final PartnerService partnerService;

    @GetMapping("/profile")
    public String getProfile(Principal principal, Model model) {
        if (principal == null) return "redirect:/login";

        String phone = principal.getName();
        Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CLIENT"))) {
            Client client = clientService.getByPhone(phone);
            model.addAttribute("user", client);
            model.addAttribute("role", "CLIENT");
        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PARTNER"))) {
            Partner partner = partnerService.getByPhone(phone);
            model.addAttribute("user", partner);
            model.addAttribute("role", "PARTNER");
        }

        return "profile";
    }

}
