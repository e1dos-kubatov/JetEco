package comsep_23.JetEco.controller;

import comsep_23.JetEco.entity.Offer;
import comsep_23.JetEco.service.OfferService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping("/create")
    public Offer createOffer(@RequestParam Long partnerId, @RequestBody Offer offer) {
        return offerService.createOffer(partnerId, offer);
    }

    @GetMapping
    public List<Offer> getAllOffers() {
        return offerService.getAllOffers();
    }

    @GetMapping("/active")
    public List<Offer> getActiveOffers() {
        return offerService.getActiveOffers();
    }

    @GetMapping("/partner/{partnerId}")
    public List<Offer> getOffersByPartner(@PathVariable Long partnerId) {
        return offerService.getOffersByPartner(partnerId);
    }

    @PutMapping("/{id}")
    public Offer updateOffer(@PathVariable Long id, @RequestBody Offer updatedOffer) {
        return offerService.updateOffer(id, updatedOffer);
    }

    @DeleteMapping("/{id}")
    public void deleteOffer(@PathVariable Long id) {
        offerService.deleteOffer(id);
    }
}
