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

    @PutMapping("/offer/{id}/deactivate")
    public ResponseEntity<Void> deactivateOffer(@PathVariable Long id) {
        adminService.deactivateOffer(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/offer/{id}/activate")
    public ResponseEntity<Void> activateOffer(@PathVariable Long id) {
        adminService.activateOffer(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/users")
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }

    @PutMapping("/user/{id}/block")
    public ResponseEntity<Void> blockUser(@PathVariable Long id) {
        adminService.blockUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/user/{id}/unblock")
    public ResponseEntity<Void> unblockUser(@PathVariable Long id) {
        adminService.unblockUser(id);
        return ResponseEntity.ok().build();
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

    @PutMapping("/partner/{id}/block")
    public ResponseEntity<Void> blockPartner(@PathVariable Long id) {
        adminService.blockPartner(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/partner/{id}/unblock")
    public ResponseEntity<Void> unblockPartner(@PathVariable Long id) {
        adminService.unblockPartner(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/partner/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable Long id) {
        adminService.deletePartner(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/stats")
    public String getStatistics() {
        return adminService.getStatistics();
    }
}
