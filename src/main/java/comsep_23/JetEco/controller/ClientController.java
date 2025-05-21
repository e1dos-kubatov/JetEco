package comsep_23.JetEco.controller;

import comsep_23.JetEco.config.Role;
import comsep_23.JetEco.entity.Client;
import comsep_23.JetEco.repository.ClientRepository;
import comsep_23.JetEco.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;
    private final ClientRepository clientRepository;

    public ClientController(ClientService clientService, ClientRepository clientRepository) {
        this.clientService = clientService;
        this.clientRepository = clientRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<Client> registerClient(@RequestBody Client client) {
        Client createdClient = clientService.createNewClient(client);
        client.setRole(Role.ROLE_CLIENT);
        clientRepository.save(client);
        return ResponseEntity.ok(createdClient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
        client.setId(id);
        Client updatedClient = clientService.updateClient(client);
        return ResponseEntity.ok(updatedClient);
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<Client> getUserByPhone(@PathVariable String phone) {
        Optional<Client> userOptional = clientService.getUserByPhone(phone);
        return userOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}