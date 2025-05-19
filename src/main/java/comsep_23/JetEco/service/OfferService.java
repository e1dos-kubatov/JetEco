package comsep_23.JetEco.service;

import comsep_23.JetEco.entity.Offer;
import comsep_23.JetEco.entity.Partner;
import comsep_23.JetEco.repository.OfferRepository;
import comsep_23.JetEco.repository.PartnerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final PartnerRepository partnerRepository;

    public OfferService(OfferRepository offerRepository, PartnerRepository partnerRepository) {
        this.offerRepository = offerRepository;
        this.partnerRepository = partnerRepository;
    }

    public Offer createOffer(Long partnerId, Offer offer) {
        Partner partner = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new RuntimeException("Partner not found"));

        offer.setPartner(partner);
        offer.setCreatedAt(LocalDateTime.now());
        offer.setActive(true);
        return offerRepository.save(offer);
    }

    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    public List<Offer> getActiveOffers() {
        return offerRepository.findByActiveTrue();
    }

    public List<Offer> getOffersByPartner(Long partnerId) {
        Partner partner = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new RuntimeException("Partner not found"));
        return offerRepository.findByPartner(partner);
    }

    public Offer updateOffer(Long offerId, Offer updatedOffer) {
        Offer existingOffer = offerRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        existingOffer.setTitle(updatedOffer.getTitle());
        existingOffer.setDescription(updatedOffer.getDescription());
        existingOffer.setOriginalPrice(updatedOffer.getOriginalPrice());
        existingOffer.setDiscountPrice(updatedOffer.getDiscountPrice());
        existingOffer.setQuantity(updatedOffer.getQuantity());
        existingOffer.setPickupStart(updatedOffer.getPickupStart());
        existingOffer.setPickupEnd(updatedOffer.getPickupEnd());
        existingOffer.setActive(updatedOffer.isActive());

        return offerRepository.save(existingOffer);
    }

    public void deleteOffer(Long id) {
        if (offerRepository.existsById(id)) {
            offerRepository.deleteById(id);
        } else {
            throw new RuntimeException("Offer not found");
        }
    }
}
