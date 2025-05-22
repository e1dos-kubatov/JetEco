package comsep_23.JetEco.repository;

import comsep_23.JetEco.entity.Client;
import comsep_23.JetEco.entity.Partner;
import comsep_23.JetEco.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository <RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Transactional
    void deleteByToken(String token);

        Optional<RefreshToken> findByClient(Client client);

    Optional<RefreshToken> findByPartner(Partner partner);
}
