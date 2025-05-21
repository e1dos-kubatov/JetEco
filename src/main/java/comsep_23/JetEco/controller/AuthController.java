package comsep_23.JetEco.controller;


import comsep_23.JetEco.entity.Client;
import comsep_23.JetEco.entity.Partner;
import comsep_23.JetEco.repository.ClientRepository;
import comsep_23.JetEco.repository.PartnerRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<Client> clientOpt = clientRepository.findByPhone(loginRequest.getPhone());
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), client.getPassword())) {
                return ResponseEntity.ok("Client login successful");
            }
        }

        Optional<Partner> partnerOpt = partnerRepository.findByPhone(loginRequest.getPhone());
        if (partnerOpt.isPresent()) {
            Partner partner = partnerOpt.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), partner.getPassword())) {
                return ResponseEntity.ok("Partner login successful");
            }
        }

        return ResponseEntity.status(401).body("Invalid credentials");
    }

    @Setter
    @Getter
    public static class LoginRequest {
        private String phone;
        private String password;

    }
}
