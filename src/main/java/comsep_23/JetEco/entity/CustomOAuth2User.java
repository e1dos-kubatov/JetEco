package comsep_23.JetEco.entity;


import comsep_23.JetEco.entity.Client;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User extends Client implements OAuth2User {

    private final Map<String, Object> attributes;

    public CustomOAuth2User(Client client, Map<String, Object> attributes) {
        super(client); // копирует все поля клиента
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return getEmail(); // или другой уникальный идентификатор
    }
}
