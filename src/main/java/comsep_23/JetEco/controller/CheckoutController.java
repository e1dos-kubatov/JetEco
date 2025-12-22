package comsep_23.JetEco.controller;

import comsep_23.JetEco.entity.CartItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @GetMapping
    public String checkoutPage(Model model) {

        // Пример — потом заменишь на реальные данные из корзины
        List<CartItem> cartItems = List.of(
                new CartItem("Food Box from Bublik", 190),
                new CartItem("Food Box from Ula Food * 2", 200)
        );

        int total = cartItems.stream()
                .mapToInt(CartItem::getPrice)
                .sum();

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);

        return "checkout";
    }

    @PostMapping("/confirm")
    public String confirmOrder(
            @RequestParam String paymentMethod,
            RedirectAttributes redirectAttributes
    ) {
        // paymentMethod = QR or COURIER

        // TODO: сохранить заказ в БД
        // TODO: если QR → статус WAITING_FOR_PAYMENT
        // TODO: если COURIER → статус PAY_ON_DELIVERY

        redirectAttributes.addFlashAttribute(
                "message",
                "Order created successfully!"
        );

        return "redirect:/order-success";
    }
}

