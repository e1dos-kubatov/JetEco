package comsep_23.JetEco.controller;

import comsep_23.JetEco.config.JwtRequest;
import comsep_23.JetEco.config.JwtResponse;
import comsep_23.JetEco.config.TokenRefreshRequest;
import comsep_23.JetEco.entity.RefreshToken;
import comsep_23.JetEco.service.AuthService;
import comsep_23.JetEco.service.ClientService;
import comsep_23.JetEco.service.PartnerService;
import comsep_23.JetEco.service.RefreshTokenService;
import comsep_23.JetEco.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final ClientService clientService;
    private final PartnerService partnerService;
    private final JwtTokenUtils jwtTokenUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(token -> {
                    if (token.getClient() != null) {
                        var userDetails = clientService.loadUserByUsername(token.getClient().getPhone());
                        String newAccessToken = jwtTokenUtils.generateToken(userDetails);
                        RefreshToken newRefresh = refreshTokenService.createClientRefreshToken(token.getClient().getPhone());
                        return ResponseEntity.ok(new JwtResponse(newAccessToken, newRefresh.getToken()));
                    } else if (token.getPartner() != null) {
                        var userDetails = partnerService.loadUserByUsername(token.getPartner().getPhone());
                        String newAccessToken = jwtTokenUtils.generateToken(userDetails);
                        RefreshToken newRefresh = refreshTokenService.createPartnerRefreshToken(token.getPartner().getPhone());
                        return ResponseEntity.ok(new JwtResponse(newAccessToken, newRefresh.getToken()));
                    } else {
                        return ResponseEntity.badRequest().body("Invalid token source");
                    }
                })
                .orElse(ResponseEntity.status(403).body("Refresh token not found or expired"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody TokenRefreshRequest request) {
        refreshTokenService.deleteByToken(request.getRefreshToken());
        return ResponseEntity.ok("Logged out successfully");
    }
}
