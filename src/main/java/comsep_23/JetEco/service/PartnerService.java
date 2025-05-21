package comsep_23.JetEco.service;

import comsep_23.JetEco.entity.Partner;
import comsep_23.JetEco.repository.PartnerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PartnerService implements UserDetailsService {
    private final PartnerRepository partnerRepository;
    private final PasswordEncoder passwordEncoder;

    public PartnerService(PartnerRepository partnerRepository, PasswordEncoder passwordEncoder) {
        this.partnerRepository = partnerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Partner createNewPartner(Partner partner) {
        if (partnerRepository.findByPhone(partner.getPhone()).isPresent()) {
            throw new RuntimeException("Partner with this phone number already exists.");
        }

        if (partner.getName() == null || partner.getName().isBlank()) {
            throw new RuntimeException("Partner name must not be empty.");
        }

        partner.setPassword(passwordEncoder.encode(partner.getPassword()));
        partner.setRegisteredAt(LocalDateTime.now());
        return partnerRepository.save(partner);
    }

    public Partner updatePartner(Partner partner) {
        Partner existingPartner = partnerRepository.findById(partner.getId())
                .orElseThrow(() -> new RuntimeException("Partner with id " + partner.getId() + " not found"));

        existingPartner.setName(partner.getName());
        existingPartner.setAddress(partner.getAddress());
        existingPartner.setPhone(partner.getPhone());
        if (partner.getPassword() != null && !partner.getPassword().isBlank()) {
            existingPartner.setPassword(passwordEncoder.encode(partner.getPassword()));
        }

        return partnerRepository.save(existingPartner);
    }

    public Optional<Partner> getPartnerByPhone(String phone) {
        return partnerRepository.findByPhone(phone);
    }

    public void deletePartner(Long id) {
        if (partnerRepository.existsById(id)) {
            partnerRepository.deleteById(id);
        } else {
            throw new RuntimeException("Partner with id " + id + " not found.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        return partnerRepository.findByPhone(phone)
                .orElseThrow(() -> new UsernameNotFoundException("Partner with number " + phone + " not found"));
    }


    public Partner getByPhone(String phone) {
        return partnerRepository.findByPhone(phone).orElse(null);
    }

}