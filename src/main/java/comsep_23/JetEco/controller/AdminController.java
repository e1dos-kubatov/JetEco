package comsep_23.JetEco.controller;

import comsep_23.JetEco.entity.Offer;
import comsep_23.JetEco.entity.Partner;
import comsep_23.JetEco.entity.User;
import comsep_23.JetEco.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/offers")
    public List<Offer> getAllOffers() {
        return adminService.getAllOffers();
    }

    @DeleteMapping("/offer/{id}")
    public ResponseEntity<Void> deactivateOffer(@PathVariable Long id) {
        adminService.deactivateOffer(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/partners")
    public List<Partner> getAllPartners() {
        return adminService.getAllPartners();
    }

    @DeleteMapping("/partner/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable Long id) {
        adminService.deletePartner(id);
        return ResponseEntity.ok().build();
    }
}
