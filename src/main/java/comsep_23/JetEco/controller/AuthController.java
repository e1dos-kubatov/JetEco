package comsep_23.JetEco.controller;

import comsep_23.JetEco.config.JwtRequest;
import comsep_23.JetEco.config.JwtResponse;
import comsep_23.JetEco.config.TokenRefreshRequest;
import comsep_23.JetEco.entity.RefreshToken;
import comsep_23.JetEco.service.AuthService;
import comsep_23.JetEco.service.CustomUserDetailsService;
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
    private final CustomUserDetailsService customUserDetailsService; // ✅ Inject this
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
                    String phone = token.getClient() != null
                            ? token.getClient().getPhone()
                            : token.getPartner() != null
                            ? token.getPartner().getPhone()
                            : null;

                    if (phone == null) {
                        return ResponseEntity.badRequest().body("Invalid token source");
                    }

                    var userDetails = customUserDetailsService.loadUserByUsername(phone);
                    String newAccessToken = jwtTokenUtils.generateToken(userDetails);

                    RefreshToken newRefresh = token.getClient() != null
                            ? refreshTokenService.createClientRefreshToken(phone)
                            : refreshTokenService.createPartnerRefreshToken(phone);

                    return ResponseEntity.ok(new JwtResponse(newAccessToken, newRefresh.getToken()));
                })
                .orElse(ResponseEntity.status(403).body("Refresh token not found or expired"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody TokenRefreshRequest request) {
        refreshTokenService.deleteByToken(request.getRefreshToken());
        return ResponseEntity.ok("Logged out successfully");
    }
}
