package comsep_23.JetEco.service;

import comsep_23.JetEco.entity.Offer;
import comsep_23.JetEco.entity.Order;
import comsep_23.JetEco.entity.Client;
import comsep_23.JetEco.repository.OfferRepository;
import comsep_23.JetEco.repository.OrderRepository;
import comsep_23.JetEco.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final OfferRepository offerRepository;

    public OrderService(OrderRepository orderRepository, ClientRepository clientRepository, OfferRepository offerRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.offerRepository = offerRepository;
    }

    public Order createOrder(Long clientId, Long offerId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        if (!offer.isActive() || offer.getQuantity() <= 0) {
            throw new RuntimeException("Offer is not available");
        }

        offer.setQuantity(offer.getQuantity() - 1);
        offerRepository.save(offer);

        Order order = new Order();
        order.setClient(client);
        order.setOffer(offer);
        order.setOrderedAt(LocalDateTime.now());
        order.setPaid(false);
        order.setAmount(offer.getDiscountPrice());

        return orderRepository.save(order);
    }

    public List<Order> getClientOrders(Long clientId) {
        return orderRepository.findByClientId(clientId);
    }

    public List<Order> getOfferOrders(Long offerId) {
        return orderRepository.findByOfferId(offerId);
    }

    public void deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            throw new RuntimeException("Order not found");
        }
    }
}
