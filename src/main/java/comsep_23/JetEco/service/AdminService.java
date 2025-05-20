package comsep_23.JetEco.service;

import comsep_23.JetEco.entity.Offer;
import comsep_23.JetEco.entity.Partner;
import comsep_23.JetEco.entity.User;
import comsep_23.JetEco.repository.OfferRepository;
import comsep_23.JetEco.repository.OrderRepository;
import comsep_23.JetEco.repository.PartnerRepository;
import comsep_23.JetEco.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final OfferRepository offerRepository;
    private final PartnerRepository partnerRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public AdminService(OfferRepository offerRepository,
                        PartnerRepository partnerRepository,
                        UserRepository userRepository,
                        OrderRepository orderRepository) {
        this.offerRepository = offerRepository;
        this.partnerRepository = partnerRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
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

    public void activateOffer(Long offerId) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));
        offer.setActive(true);
        offerRepository.save(offer);
    }



    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void blockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(false);
        userRepository.save(user);
    }

    public void unblockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(true);
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }


    public List<Partner> getAllPartners() {
        return partnerRepository.findAll();
    }

    public void blockPartner(Long partnerId) {
        Partner partner = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new RuntimeException("Partner not found"));
        partner.setActive(false);
        partnerRepository.save(partner);
    }

    public void unblockPartner(Long partnerId) {
        Partner partner = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new RuntimeException("Partner not found"));
        partner.setActive(true);
        partnerRepository.save(partner);
    }

    public void deletePartner(Long partnerId) {
        partnerRepository.deleteById(partnerId);
    }

    public String getStatistics() {
        long totalOffers = offerRepository.count();
        long activeOffers = offerRepository.countByActiveTrue();
        long totalUsers = userRepository.count();
        long activeUsers = userRepository.countByActiveTrue();
        long totalPartners = partnerRepository.count();
        long activePartners = partnerRepository.countByActiveTrue();
        long totalOrders = orderRepository.count();


        return String.format("Offers: %d (Active: %d), Users: %d (Active: %d), Partners: %d (Active: %d), Orders: %d",
                totalOffers, activeOffers, totalUsers, activeUsers, totalPartners, activePartners, totalOrders);
    }
}
