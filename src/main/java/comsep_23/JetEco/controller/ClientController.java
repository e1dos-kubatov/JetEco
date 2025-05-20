package comsep_23.JetEco.controller;

import comsep_23.JetEco.entity.Client;
import comsep_23.JetEco.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/register")
    public ResponseEntity<Client> registerClient(@RequestBody Client client) {
        Client createdClient = clientService.createNewClient(client);
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
