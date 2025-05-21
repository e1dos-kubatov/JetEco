package comsep_23.JetEco.service;

import comsep_23.JetEco.entity.Client;
import comsep_23.JetEco.entity.Partner;
import comsep_23.JetEco.entity.RefreshToken;
import comsep_23.JetEco.exceptions.AppError;
import comsep_23.JetEco.repository.ClientRepository;
import comsep_23.JetEco.repository.PartnerRepository;
import comsep_23.JetEco.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final ClientRepository clientRepository;
    private final PartnerRepository partnerRepository;

    public RefreshToken createClientRefreshToken(String phone) {
        Client client = clientRepository.findByPhone(phone)
                .orElseThrow(() -> new AppError(HttpStatus.NOT_FOUND.value(), "Client not found"));

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setClient(client);
        refreshToken.setExpiryDate(Instant.now().plusSeconds(7 * 24 * 60 * 60)); // 7 days
        refreshToken.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken createPartnerRefreshToken(String phone) {
        Partner partner = partnerRepository.findByPhone(phone)
                .orElseThrow(() -> new AppError(HttpStatus.NOT_FOUND.value(), "Partner not found"));

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setPartner(partner);
        refreshToken.setExpiryDate(Instant.now().plusSeconds(7 * 24 * 60 * 60)); // 7 days
        refreshToken.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired. Please sign in again.");
        }
        return token;
    }

    public void deleteByToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
