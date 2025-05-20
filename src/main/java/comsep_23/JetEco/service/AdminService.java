package comsep_23.JetEco.service;

import comsep_23.JetEco.entity.Offer;
import comsep_23.JetEco.entity.Partner;
import comsep_23.JetEco.entity.Client;
import comsep_23.JetEco.repository.OfferRepository;
import comsep_23.JetEco.repository.OrderRepository;
import comsep_23.JetEco.repository.PartnerRepository;
import comsep_23.JetEco.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final OfferRepository offerRepository;
    private final PartnerRepository partnerRepository;
    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;

    public AdminService(OfferRepository offerRepository,
                        PartnerRepository partnerRepository,
                        ClientRepository clientRepository,
                        OrderRepository orderRepository) {
        this.offerRepository = offerRepository;
        this.partnerRepository = partnerRepository;
        this.clientRepository = clientRepository;
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



    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public void blockClient(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        client.setActive(false);
        clientRepository.save(client);
    }

    public void unblockClient(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        client.setActive(true);
        clientRepository.save(client);
    }

    public void deleteClient(Long clientId) {
        clientRepository.deleteById(clientId);
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
        long totalClients = clientRepository.count();
        long activeClients = clientRepository.countByActiveTrue();
        long totalPartners = partnerRepository.count();
        long activePartners = partnerRepository.countByActiveTrue();
        long totalOrders = orderRepository.count();


        return String.format("Offers: %d (Active: %d), Clients: %d (Active: %d), Partners: %d (Active: %d), Orders: %d",
                totalOffers, activeOffers, totalClients, activeClients, totalPartners, activePartners, totalOrders);
    }
}