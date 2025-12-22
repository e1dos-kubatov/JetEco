package comsep_23.JetEco.controller;

import comsep_23.JetEco.entity.CartItem;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @GetMapping
    public String checkoutPage(
            Model model,
            Principal principal,                   // для JWT
            @AuthenticationPrincipal OAuth2User oAuth2User // для Google OAuth2
    ) {
        // Пример корзины — потом заменишь на реальные данные
        List<CartItem> cartItems = List.of(
                new CartItem("Food Box from Bublik", 190),
                new CartItem("Food Box from Ula Food * 2", 200)
        );

        int total = cartItems.stream().mapToInt(CartItem::getPrice).sum();

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);

        // Определяем e-mail пользователя
        String email = "default@example.com";
        if (oAuth2User != null && oAuth2User.getAttribute("email") != null) {
            email = oAuth2User.getAttribute("email");
        } else if (principal != null) {
            // Тут можешь обратиться к базе пользователей через username
            email = lookupEmailByUsername(principal.getName());
        }

        model.addAttribute("email", email); // передаем email в Thymeleaf

        return "checkout";
    }

    // Пример метода для lookup email по username (JWT)
    private String lookupEmailByUsername(String username) {
        // Тут логика обращения к базе пользователей, например:
        // return userService.findByUsername(username).getEmail();
        return "jwtuser@example.com"; // заглушка для примера
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
