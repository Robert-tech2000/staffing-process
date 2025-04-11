package com.example.staffing.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtClaimsConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private static final String PUBLIC_CLIENT_NAME = "public-oauth2-staffing-process-client";
    
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        var authorities = extractRealmRoles(jwt);
        return new JwtAuthenticationToken(jwt, authorities, getPrincipalFromClaim(jwt));
    }

    private String getPrincipalFromClaim(Jwt jwt) {
        var claimName = "preferred_username";
        return jwt.getClaim(claimName);
    }

    private Collection<GrantedAuthority> extractRealmRoles(Jwt jwt) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Extract client roles
        Map<String, Object> resourceAccess = (Map<String, Object>) jwt.getClaims().get("resource_access");
        if (resourceAccess != null) {
            Map<String, Object> client = (Map<String, Object>) resourceAccess.get(PUBLIC_CLIENT_NAME);
            if (client != null && client.containsKey("roles")) {
                List<String> clientRoles = (List<String>) client.get("roles");
                authorities.addAll(clientRoles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                        .collect(Collectors.toList()));
            }
        }

        return authorities;
//        Map<String, Object> resource = jwt.getClaim("realm_access");
//        Collection<String> roles;
//        if (resource == null
//                || (roles = (Collection<String>) resource.get("roles")) == null) {
//            return Set.of();
//        }
//        return roles.stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
//                .collect(Collectors.toSet());
    }
}