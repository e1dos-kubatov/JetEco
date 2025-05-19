package comsep_23.JetEco.service;

import comsep_23.JetEco.entity.Offer;
import comsep_23.JetEco.entity.Partner;
import comsep_23.JetEco.entity.User;
import comsep_23.JetEco.repository.AdminRepository;
import comsep_23.JetEco.repository.OfferRepository;
import comsep_23.JetEco.repository.PartnerRepository;
import comsep_23.JetEco.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final OfferRepository offerRepository;
    private final PartnerRepository partnerRepository;
    private final UserRepository userRepository;

    public AdminService(AdminRepository adminRepository,
                        OfferRepository offerRepository,
                        PartnerRepository partnerRepository,
                        UserRepository userRepository) {
        this.adminRepository = adminRepository;
        this.offerRepository = offerRepository;
        this.partnerRepository = partnerRepository;
        this.userRepository = userRepository;
    }

    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    public void deactivateOffer(Long offerId) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));
        offer.setActive(false);
        offerRepository.save(offer);
    }

    public List<Partner> getAllPartners() {
        return partnerRepository.findAll();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public void deletePartner(Long partnerId) {
        partnerRepository.deleteById(partnerId);
    }
}
