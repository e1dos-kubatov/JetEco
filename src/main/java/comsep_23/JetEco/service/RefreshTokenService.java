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
import org.springframework.transaction.annotation.Transactional; // Важно для транзакций

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final ClientRepository clientRepository;
    private final PartnerRepository partnerRepository;

    // Метод для создания или обновления refresh-токена для клиента
    @Transactional // Обернем метод в транзакцию
    public RefreshToken createClientRefreshToken(String phone) {
        Client client = clientRepository.findByPhone(phone)
                .orElseThrow(() -> new AppError(HttpStatus.NOT_FOUND.value(), "Client not found"));

        // Попытаться найти существующий refresh-токен для данного клиента
        // Вам нужно добавить метод findByClient в RefreshTokenRepository
        Optional<RefreshToken> existingTokenOptional = refreshTokenRepository.findByClient(client);

        RefreshToken refreshToken;
        if (existingTokenOptional.isPresent()) {
            // Если токен существует, обновить его значения
            refreshToken = existingTokenOptional.get();
            refreshToken.setToken(UUID.randomUUID().toString()); // Генерируем новый токен
            refreshToken.setExpiryDate(Instant.now().plusSeconds(7 * 24 * 60 * 60)); // Обновляем срок действия
            // Убедитесь, что partner_id здесь не установлен или установлен в null, если это токен клиента
            refreshToken.setPartner(null); // Устанавливаем partner в null, так как это токен клиента
        } else {
            // Если токена нет, создать новый
            refreshToken = new RefreshToken();
            refreshToken.setClient(client);
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken.setExpiryDate(Instant.now().plusSeconds(7 * 24 * 60 * 60)); // 7 days
            // Убедитесь, что partner_id здесь не установлен или установлен в null
            refreshToken.setPartner(null); // Устанавливаем partner в null, так как это токен клиента
        }

        return refreshTokenRepository.save(refreshToken);
    }

    // Метод для создания или обновления refresh-токена для партнера
    @Transactional // Обернем метод в транзакцию
    public RefreshToken createPartnerRefreshToken(String phone) {
        Partner partner = partnerRepository.findByPhone(phone)
                .orElseThrow(() -> new AppError(HttpStatus.NOT_FOUND.value(), "Partner not found"));

        // Попытаться найти существующий refresh-токен для данного партнера
        // Вам нужно добавить метод findByPartner в RefreshTokenRepository
        Optional<RefreshToken> existingTokenOptional = refreshTokenRepository.findByPartner(partner);

        RefreshToken refreshToken;
        if (existingTokenOptional.isPresent()) {
            // Если токен существует, обновить его значения
            refreshToken = existingTokenOptional.get();
            refreshToken.setToken(UUID.randomUUID().toString()); // Генерируем новый токен
            refreshToken.setExpiryDate(Instant.now().plusSeconds(7 * 24 * 60 * 60)); // Обновляем срок действия
            // Убедитесь, что client_id здесь не установлен или установлен в null, если это токен партнера
            refreshToken.setClient(null); // Устанавливаем client в null, так как это токен партнера
        } else {
            // Если токена нет, создать новый
            refreshToken = new RefreshToken();
            refreshToken.setPartner(partner);
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken.setExpiryDate(Instant.now().plusSeconds(7 * 24 * 60 * 60)); // 7 days
            // Убедитесь, что client_id здесь не установлен или установлен в null
            refreshToken.setClient(null); // Устанавливаем client в null, так как это токен партнера
        }

        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Transactional // Добавьте транзакцию для операций изменения данных
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired. Please sign in again.");
        }
        return token;
    }

    @Transactional // Добавьте транзакцию для операций изменения данных
    public void deleteByToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
