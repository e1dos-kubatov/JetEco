package comsep_23.JetEco.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.ui.Model;
import comsep_23.JetEco.config.Role;
import comsep_23.JetEco.entity.Client;
import comsep_23.JetEco.entity.Partner;
import comsep_23.JetEco.repository.PartnerRepository;
import comsep_23.JetEco.service.PartnerService;

@Controller
@RequestMapping("/api/partners")
public class PartnerController {

    private final PartnerService partnerService;
    private final PartnerRepository partnerRepository;

    public PartnerController(PartnerService partnerService, PartnerRepository partnerRepository) {
        this.partnerService = partnerService;
        this.partnerRepository = partnerRepository;
    }

    @PostMapping("/register-partner")
    public ResponseEntity<Partner> registerPartner(@RequestBody Partner partner) {
        Partner createdPartner = partnerService.createNewPartner(partner);
        partner.setRole(Role.ROLE_PARTNER);
        partnerRepository.save(partner);
        return ResponseEntity.ok(createdPartner);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Partner> updatePartner(@PathVariable Long id, @RequestBody Partner partner) {
        partner.setId(id);
        Partner updatedPartner = partnerService.updatePartner(partner);
        return ResponseEntity.ok(updatedPartner);
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<Partner> getPartnerByPhone(@PathVariable String phone) {
        Optional<Partner> partnerOptional = partnerService.getPartnerByPhone(phone);
        return partnerOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/partners")
    public String showPartners(Model model) {
        model.addText("partners");
        return "partners";
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable Long id) {
        partnerService.deletePartner(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/bublik")
    public String getPartnerCatalog(@AuthenticationPrincipal Client client, Model model) {
        if (client != null) {
            model.addAttribute("name", client.getName());
            model.addAttribute("picture", client.getPictureUrl());
            model.addAttribute("email", client.getEmail());
            model.addAttribute("role", client.getRole().name());
        } else {
            model.addAttribute("role", "ROLE_ANONYMOUS");
        }

        return "partner_id1";
    }

}
