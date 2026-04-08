package com.rendezvous.demo.filter;


import com.rendezvous.demo.Repositories.UserRepo;
import com.rendezvous.demo.domain.Entities.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserProvisioningFilter extends OncePerRequestFilter {
    private final UserRepo userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null
                && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof Jwt jwt){
            UUID keyclockId = UUID.fromString(jwt.getSubject());
            System.out.println("User ID from JWT: " +jwt.getClaimAsString("Adresse") );
            if (!userRepo.existsById(keyclockId)){
                User newUser = new  User();
                newUser.setId(keyclockId);
                newUser.setEmail(jwt.getClaimAsString("email"));
                newUser.setName(jwt.getClaimAsString("preferred_username"));
                newUser.setFirstName(jwt.getClaimAsString("firstName"));
                newUser.setAddress(jwt.getClaimAsString("Adresse"));
                newUser.setNumber(jwt.getClaimAsString("PhoneNumber"));
                String field = jwt.getClaimAsString("field");
                if (field != null) {
                    newUser.setField(field);
                }
                userRepo.save(newUser);
            }
        }
        filterChain.doFilter(request,response);



    }
}
