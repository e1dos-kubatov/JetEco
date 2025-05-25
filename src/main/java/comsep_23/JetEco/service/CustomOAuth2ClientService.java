package comsep_23.JetEco.service;

import comsep_23.JetEco.entity.Client;
import comsep_23.JetEco.config.Role;
import comsep_23.JetEco.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomOAuth2ClientService extends DefaultOAuth2UserService {

    private final ClientRepository clientRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");

        // 1. Поиск по email
        Client client = clientRepository.findByEmail(email).orElseGet(() -> {
            Client newClient = new Client();
            newClient.setEmail(email);
            newClient.setName(oAuth2User.getAttribute("name"));
            newClient.setAuthProvider("google");
            newClient.setPhone("google_" + UUID.randomUUID());
            newClient.setPassword("oauth2user"); // можно закодировать
            newClient.setRole(Role.ROLE_CLIENT); // default role
            newClient.setRegisteredAt(LocalDateTime.now());
            newClient.setActive(true);
            newClient.setPictureUrl(oAuth2User.getAttribute("picture"));
            return clientRepository.save(newClient);
        });

        // 2. Установка правильной роли
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(client.getRole().name()));

        // 3. Возвращаем кастомный OAuth2User с правильной ролью
        return new DefaultOAuth2User(
                authorities,
                oAuth2User.getAttributes(),
                "email" // имя атрибута, который будет использоваться как "username"
        );
    }
}
