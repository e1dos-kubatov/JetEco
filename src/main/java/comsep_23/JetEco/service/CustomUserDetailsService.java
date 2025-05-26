package comsep_23.JetEco.service;

import comsep_23.JetEco.entity.Client;
import comsep_23.JetEco.entity.Partner;
import comsep_23.JetEco.repository.ClientRepository;
import comsep_23.JetEco.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final ClientRepository clientRepository;
    private final PartnerRepository partnerRepository;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        Optional<Client> clientOpt = clientRepository.findByPhone(phone);
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            return new User(
                    client.getPhone(),
                    client.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_CLIENT"))
            );
        }

        Optional<Partner> partnerOpt = partnerRepository.findByPhone(phone);
        if (partnerOpt.isPresent()) {
            Partner partner = partnerOpt.get();
            return new User(
                    partner.getPhone(),
                    partner.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_PARTNER"))
            );
        }

        throw new UsernameNotFoundException("User not found with phone: " + phone);
    }
}