package com.example.staffing.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    private static final String PUBLIC_CLIENT_ADMIN_ROLE = "ROLE_CLIENT_PUBLIC_ADMIN";
    private static final String PUBLIC_CLIENT_USER_ROLE = "ROLE_CLIENT_PUBLIC_USER";

    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String issuerUri;

    private final JwtClaimsConverter jwtAuthConverter;

    public SecurityConfig(JwtClaimsConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("✅ SecurityFilterChain is being initialized!");
        Customizer<CorsConfigurer<HttpSecurity>> corsCustomizer = cors -> cors
                .configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.addAllowedOrigin("http://localhost:4200");
                    config.addAllowedMethod("*");
                    config.addAllowedHeader("*");
                    config.setAllowCredentials(true);
                    return config;
                });

        http.cors(corsCustomizer)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/clients/**").hasAuthority(PUBLIC_CLIENT_ADMIN_ROLE)
                        .requestMatchers(HttpMethod.PUT, "/clients/**").hasAuthority(PUBLIC_CLIENT_ADMIN_ROLE)
                        .requestMatchers(HttpMethod.DELETE, "/clients/**").hasAuthority(PUBLIC_CLIENT_ADMIN_ROLE)

                        .requestMatchers(HttpMethod.POST, "/employees/**").hasAuthority(PUBLIC_CLIENT_ADMIN_ROLE)
                        .requestMatchers(HttpMethod.PUT, "/employees/**").hasAuthority(PUBLIC_CLIENT_ADMIN_ROLE)
                        .requestMatchers(HttpMethod.DELETE, "/employees/**").hasAuthority(PUBLIC_CLIENT_ADMIN_ROLE)

                        .requestMatchers("/home-admin/**").hasAuthority(PUBLIC_CLIENT_ADMIN_ROLE)
                        .anyRequest().hasAuthority(PUBLIC_CLIENT_USER_ROLE)
                )
                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2ResourceServer ->
                        oauth2ResourceServer.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
                );

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return JwtDecoders.fromIssuerLocation(issuerUri);
    }

}
