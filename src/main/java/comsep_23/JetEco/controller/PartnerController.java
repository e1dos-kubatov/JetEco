package comsep_23.JetEco.controller;

import ch.qos.logback.core.model.Model;
import comsep_23.JetEco.config.Role;
import comsep_23.JetEco.entity.Partner;
import comsep_23.JetEco.repository.PartnerRepository;
import comsep_23.JetEco.service.PartnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
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


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable Long id) {
        partnerService.deletePartner(id);
        return ResponseEntity.noContent().build();
    }

}
