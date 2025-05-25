package comsep_23.JetEco.config;

import comsep_23.JetEco.service.OAuth2ClientService;
import comsep_23.JetEco.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private JwtRequestFilter jwtRequestFilter;
    private final CustomUserDetailsService customUserDetailsService;
    private final OAuth2ClientService OAuth2ClientService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService,
                          OAuth2ClientService OAuth2ClientService) {
        this.customUserDetailsService = customUserDetailsService;
        this.OAuth2ClientService = OAuth2ClientService;
    }

    @Autowired
    public void setJwtRequestFilter(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/partners/register-partner", "/api/clients/register", "/login", "/register", "/register-business", "/css/**").permitAll()
                        .requestMatchers("/api/clients/**").hasRole("CLIENT")
                        .requestMatchers("/api/partners/**").hasRole("PARTNER")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/profile").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .oauth2Login(oauth -> oauth
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(OAuth2ClientService)
                        )
                        .defaultSuccessUrl("/profile", true)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                );
        return http.build();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(customUserDetailsService);
        return provider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

