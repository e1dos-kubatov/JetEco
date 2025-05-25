package comsep_23.JetEco.config;

import comsep_23.JetEco.service.CustomOAuth2ClientService;
import comsep_23.JetEco.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // Changed from EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
// @EnableGlobalMethodSecurity(securedEnabled = true) // Deprecated in Spring Security 6+
@EnableMethodSecurity(securedEnabled = true) // Use this instead for Spring Security 6+
public class SecurityConfig {

    private JwtRequestFilter jwtRequestFilter; // This is fine as a field for setter injection
    private final CustomUserDetailsService customUserDetailsService;
    // Fix: Corrected capitalization for customOAuth2ClientService to follow Java naming conventions
    private final CustomOAuth2ClientService customOAuth2ClientService;

    // Fix: Removed unused OAuth2AuthenticationSuccessHandler from constructor
    public SecurityConfig(CustomUserDetailsService customUserDetailsService,
                          CustomOAuth2ClientService customOAuth2ClientService) { // Corrected parameter name
        this.customUserDetailsService = customUserDetailsService;
        this.customOAuth2ClientService = customOAuth2ClientService; // Corrected assignment
    }

    @Autowired // This is fine for setter injection, though constructor injection is often preferred
    public void setJwtRequestFilter(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Consider csrf.ignoringRequestMatchers("/api/**") for specific APIs

                .cors(cors -> cors.disable())

                .authorizeHttpRequests(auth -> auth
                        // Publicly accessible paths
                        .requestMatchers(
                                "/api/partners/register-partner",
                                "/api/clients/register",
                                "/login",
                                "/register",
                                "/register-business",
                                "/css/**",
                                "/js/**",
                                "/images/**", // Add if you have an images folder
                                "/favicon.ico" // Common static asset
                        ).permitAll()

                        // Role-based access for API endpoints
                        .requestMatchers("/api/clients/**").hasRole("CLIENT")
                        .requestMatchers("/api/partners/**").hasRole("PARTNER")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // Role-based access for UI pages (if applicable, adjust paths)
                        .requestMatchers("/client-profile").hasRole("CLIENT")
                        .requestMatchers("/partner-profile").hasRole("PARTNER")
                        .requestMatchers("/admin-dashboard").hasRole("ADMIN")


                        .anyRequest().authenticated() // Changed from .permitAll()
                )
                .oauth2Login(oauth -> oauth
                                .loginPage("/login") // Custom login page for OAuth2 flow
                                .userInfoEndpoint(userInfo -> userInfo
                                        .userService(customOAuth2ClientService) // Corrected field name
                                )
                                .defaultSuccessUrl("/", true) // Redirect after successful OAuth2 login
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Standard for web apps with sessions
                )
                .exceptionHandling(exception -> exception
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

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