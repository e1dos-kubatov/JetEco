package comsep_23.JetEco.service;

import comsep_23.JetEco.config.Role;
import comsep_23.JetEco.entity.Client;
import comsep_23.JetEco.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.*;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuth2ClientService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {


    private final ClientRepository clientRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();

        String name = (String) attributes.get("name");
        String email = (String) attributes.get("email");
        String picture = (String) attributes.get("picture");

        clientRepository.findByPhone(email).orElseGet(() -> {
            Client newUser = Client.builder()
                    .name(name)
                    .email(email)
                    .pictureUrl(picture)
                    .authProvider("google")
                    .phone("google_" + UUID.randomUUID())
                    .password("google")
                    .role(Role.ROLE_CLIENT)
                    .registeredAt(LocalDateTime.now())
                    .active(true)
                    .build();
            return clientRepository.save(newUser);
        });

        return oAuth2User;
    }
}

