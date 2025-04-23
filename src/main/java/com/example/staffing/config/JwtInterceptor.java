package com.example.staffing.config;

import com.example.staffing.model.Role;
import com.example.staffing.model.User;
import com.example.staffing.repository.UserRepository;
import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Component
public class JwtInterceptor implements HandlerInterceptor {


    public static final String CLIENT_PUBLIC_ADMIN = "client_public_admin";
    public static final String CLIENT_PUBLIC_USER = "client_public_user";
    private final UserRepository userRepository;

    private final HttpServletRequest request;

    private User currentUser;

    @Autowired
    public JwtInterceptor(UserRepository userRepository, HttpServletRequest request) {
        this.userRepository = userRepository;
        this.request = request;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
            log.warn("No JWT found in security context.");
            return false;
        }

        return !getUserFromToken(jwt);
    }

    public User getCurrentUser() {
        return userRepository.findByUsername(currentUser.getUsername()).orElseGet(() -> {
            log.warn("User not found in database, returning null.");
            return null;
        });
    }

    private boolean getUserFromToken(Jwt jwt) {
        String username = jwt.getClaimAsString("preferred_username");
        if (username == null) {
            log.warn("No username found in JWT.");
            return true;
        }

        this.currentUser = userRepository.findByUsername(username).orElseGet(() -> {
            User newUser = User.builder()
                    .username(username)
                    .firstName(jwt.getClaimAsString("given_name"))
                    .lastName(jwt.getClaimAsString("family_name"))
                    .email(jwt.getClaimAsString("email"))
                    .available(true) // Default value for availability, perhaps some stronger logic here
                    .position("n/a") // Default value for position, perhaps some stronger logic here
                    .build();

            ArrayList<String> jwtRoles = (ArrayList<String>) ((LinkedTreeMap<?, ?>) jwt.getClaim("realm_access")).get("roles");
            boolean isAdmin = jwtRoles.contains(CLIENT_PUBLIC_ADMIN);
            boolean isUser = jwtRoles.contains(CLIENT_PUBLIC_USER);

            if (isAdmin) {
                newUser.setRoles(List.of(Role.CLIENT_PUBLIC_ADMIN));
            } else if (isUser) {
                newUser.setRoles(List.of(Role.CLIENT_PUBLIC_USER));
            }
            return userRepository.save(newUser);
        });
        return false;
    }

}