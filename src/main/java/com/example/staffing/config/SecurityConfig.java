package com.example.staffing.config;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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

    private static final String PUBLIC_CLIENT_ROLE = "ROLE_CLIENT_PUBLIC_ADMIN";
    private static final String CONFIDENTIAL_CLIENT_ROLE = "ROLE_CLIENT_CONFIDENTIAL_ADMIN";

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("pass")
                .roles("ADMIN")
                .build();

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("pass")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("✅ SecurityFilterChain is being initialized!");
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/home/**").permitAll()
                        .requestMatchers("/home-admin/**").hasAuthority(PUBLIC_CLIENT_ROLE)
                        .anyRequest().authenticated()
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
                                // Manually Create JwtAuthenticationToken.
                                String accessToken = authorizedClient.getAccessToken().getTokenValue();
                                Jwt jwt = jwtDecoder().decode(accessToken);
                                AbstractAuthenticationToken authentication2 = jwtAuthenticationConverter().convert(jwt);
                                Collection<GrantedAuthority> authorities = authentication2.getAuthorities();
                                Authentication jwtAuth = new JwtAuthenticationToken(jwt, authorities);

                                // Manually set authentication in security context.
                                SecurityContextHolder.getContext().setAuthentication(jwtAuth);
                            } else {
                                System.out.println("❌ No Access Token found!");
                            }

                            response.sendRedirect("/home-admin");
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
        return JwtDecoders.fromIssuerLocation("http://localhost:8081/realms/oauth2-staffing-process-realm");
    }

    /*
    // No keycloak setup just roles.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/roles").hasRole("ADMIN")
                        .requestMatchers("/staffing-processes").hasRole("ADMIN")
                        .requestMatchers("/clients").hasRole("ADMIN")
                        .requestMatchers("/employees").hasRole("ADMIN")
                        .requestMatchers("/comments").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
    */


    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
        return converter;
    }

}
