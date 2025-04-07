package com.example.staffing.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    public SecurityConfig() {
        System.out.println("✅ SecurityConfig is being loaded!");
    }

    private static final String PUBLIC_CLIENT_ADMIN_ROLE = "ROLE_CLIENT_PUBLIC_ADMIN";
    private static final String PUBLIC_CLIENT_USER_ROLE = "ROLE_CLIENT_PUBLIC_USER";

    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String issuerUri;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("✅ SecurityFilterChain is being initialized!");
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/staffing-processes/**").hasAuthority(PUBLIC_CLIENT_ADMIN_ROLE)
                        .requestMatchers("/clients/**").hasAuthority(PUBLIC_CLIENT_ADMIN_ROLE)
                        .requestMatchers("/employees/**").hasAuthority(PUBLIC_CLIENT_ADMIN_ROLE)
                        .requestMatchers("/home-admin/**").hasAuthority(PUBLIC_CLIENT_ADMIN_ROLE)
                        .anyRequest().hasAuthority(PUBLIC_CLIENT_USER_ROLE)
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .oauth2Login(oauth2 -> oauth2
                        .successHandler((request, response, authentication) -> {
                            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
                            OAuth2AuthorizedClient authorizedClient =
                                    authorizedClientService.loadAuthorizedClient(
                                            oauthToken.getAuthorizedClientRegistrationId(),
                                            oauthToken.getName());

                            if (authorizedClient != null) {
                                String accessToken = authorizedClient.getAccessToken().getTokenValue();
                                Jwt jwt = jwtDecoder().decode(accessToken);
                                AbstractAuthenticationToken authentication2 = jwtAuthenticationConverter().convert(jwt);
                                Collection<GrantedAuthority> authorities = authentication2.getAuthorities();
                                Authentication jwtAuth = new JwtAuthenticationToken(jwt, authorities);
                                SecurityContextHolder.getContext().setAuthentication(jwtAuth);
                            } else {
                                System.out.println("❌ No Access Token found!");
                            }

                            response.sendRedirect("/home");
                        }))
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtConfigurer -> {
                            jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter());
                        })
                );

        return http.build();
    }


    @Bean
    public JwtDecoder jwtDecoder() {
        return JwtDecoders.fromIssuerLocation(issuerUri);
    }


    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
        return converter;
    }

}
