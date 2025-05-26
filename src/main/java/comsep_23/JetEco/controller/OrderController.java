package comsep_23.JetEco.controller;

import comsep_23.JetEco.entity.Client;
import comsep_23.JetEco.entity.Order;
import comsep_23.JetEco.repository.ClientRepository;
import comsep_23.JetEco.service.ClientService;
import comsep_23.JetEco.service.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final ClientRepository clientRepository;

    public OrderController(OrderService orderService, ClientRepository clientRepository) {
        this.orderService = orderService;
        this.clientRepository = clientRepository;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('CLIENT')")
    public Order createOrder(@RequestParam Long offerId, Principal principal) {
        String email = principal.getName();
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        return orderService.createOrder(client.getId(), offerId);
    }
    @GetMapping("/client/{clientId}")
    public List<Order> getClientOrders(@PathVariable Long clientId) {
        return orderService.getClientOrders(clientId);
    }

    @GetMapping("/offer/{offerId}")
    public List<Order> getOfferOrders(@PathVariable Long offerId) {
        return orderService.getOfferOrders(offerId);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
