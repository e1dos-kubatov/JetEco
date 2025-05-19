package comsep_23.JetEco.controller;

import comsep_23.JetEco.entity.Order;
import comsep_23.JetEco.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public Order createOrder(@RequestParam Long userId, @RequestParam Long offerId) {
        return orderService.createOrder(userId, offerId);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getUserOrders(@PathVariable Long userId) {
        return orderService.getUserOrders(userId);
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
