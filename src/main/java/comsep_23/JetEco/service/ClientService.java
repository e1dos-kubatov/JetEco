package comsep_23.JetEco.service;

import comsep_23.JetEco.entity.Client;
import comsep_23.JetEco.repository.ClientRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ClientService implements UserDetailsService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public Client createNewClient(Client client) {
        if (clientRepository.findByPhone(client.getPhone()).isPresent()) {
            throw new RuntimeException("Client with this phone number already exists.");
        }

        if (client.getName() == null || client.getName().isBlank()) {
            throw new RuntimeException("Name must not be empty.");
        }

        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client.setRegisteredAt(LocalDateTime.now());
        return clientRepository.save(client);
    }

    public Client updateClient(Client client) {
        Client existingClient = clientRepository.findById(client.getId())
                .orElseThrow(() -> new RuntimeException("Client with id " + client.getId() + " not found"));

        existingClient.setName(client.getName());
        existingClient.setPhone(client.getPhone());
        if (client.getPassword() != null && !client.getPassword().isBlank()) {
            existingClient.setPassword(passwordEncoder.encode(client.getPassword()));
        }
        return clientRepository.save(existingClient);
    }

    public Optional<Client> getUserByPhone(String phone) {
        return clientRepository.findByPhone(phone);
    }

    public void deleteClient(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
        } else {
            throw new RuntimeException("Client with id " + id + " not found.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}


