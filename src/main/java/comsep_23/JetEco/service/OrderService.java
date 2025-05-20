package comsep_23.JetEco.service;

import comsep_23.JetEco.entity.Offer;
import comsep_23.JetEco.entity.Order;
import comsep_23.JetEco.entity.User;
import comsep_23.JetEco.repository.OfferRepository;
import comsep_23.JetEco.repository.OrderRepository;
import comsep_23.JetEco.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OfferRepository offerRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, OfferRepository offerRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
    }

    public Order createOrder(Long userId, Long offerId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        if (!offer.isActive() || offer.getQuantity() <= 0) {
            throw new RuntimeException("Offer is not available");
        }

        offer.setQuantity(offer.getQuantity() - 1);
        offerRepository.save(offer);

        Order order = new Order();
        order.setUser(user);
        order.setOffer(offer);
        order.setOrderedAt(LocalDateTime.now());
        order.setPaid(false);
        order.setAmount(offer.getDiscountPrice());

        return orderRepository.save(order);
    }

    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
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
