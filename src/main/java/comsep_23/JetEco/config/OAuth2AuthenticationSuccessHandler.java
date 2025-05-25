package comsep_23.JetEco.config;

import comsep_23.JetEco.entity.Client;
import comsep_23.JetEco.repository.ClientRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ClientRepository clientRepository;

    public OAuth2AuthenticationSuccessHandler(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        // Получаем email из OAuth2Principal
        String phone = ((org.springframework.security.oauth2.core.user.DefaultOAuth2User) authentication.getPrincipal())
                .getAttribute("phone");

        // Находим пользователя в БД
        Client client = clientRepository.findByPhone(phone).get();

        if (client != null) {
            // Сохраняем имя и фото (если надо)
            HttpSession session = request.getSession();
            session.setAttribute("name", client.getName());
            session.setAttribute("picture", client.getPictureUrl());

            // Сохраняем роль (в формате ROLE_CLIENT, ROLE_ADMIN и т.п.)
            session.setAttribute("role", "ROLE_" + client.getRole().name());
        }

        // Перенаправляем на главную страницу
        response.sendRedirect("/");
    }
}

