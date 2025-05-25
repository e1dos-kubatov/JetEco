package comsep_23.JetEco.service;

import comsep_23.JetEco.config.JwtRequest;
import comsep_23.JetEco.config.JwtResponse;
import comsep_23.JetEco.entity.Client;
import comsep_23.JetEco.entity.Partner;
import comsep_23.JetEco.entity.RefreshToken;
import comsep_23.JetEco.exceptions.AppError;
import comsep_23.JetEco.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;
    private final ClientService clientService;
    private final PartnerService partnerService;
    private final RefreshTokenService refreshTokenService;
    private final CustomUserDetailsService customUserDetailsService;

    public ResponseEntity<?> login(JwtRequest request) {
        String phone = request.getPhone();

        Client client = clientService.getByPhone(phone);
        if (client != null) {
            return loginUser(request, "client");
        }

        Partner partner = partnerService.getByPhone(phone);
        if (partner != null) {
            return loginUser(request, "partner");
        }

        return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Invalid phone or password"), HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<?> loginUser(JwtRequest request, String userType) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getPhone(), request.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Invalid credentials"), HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getPhone());
        String token = jwtTokenUtils.generateToken(userDetails);

        RefreshToken refreshToken = userType.equals("client")
                ? refreshTokenService.createClientRefreshToken(request.getPhone())
                : refreshTokenService.createPartnerRefreshToken(request.getPhone());

        return ResponseEntity.ok(new JwtResponse(token, refreshToken.getToken()));
    }
}
