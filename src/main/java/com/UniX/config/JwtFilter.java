package com.UniX.config;

import com.UniX.services.JwtService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        
        // Getting the Authorization header                             
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Extracting the token
            String token = authHeader.replace("Bearer ", "");
            
            // Validating the user
            String username =  jwtService.validateAccessToken(token);

            if (username != null) {
                // If authenticated, tell Spring about the authenticated user
                // Authenticated user stored 
                UsernamePasswordAuthenticationToken authUser = 
                        new UsernamePasswordAuthenticationToken(username, null, List.of());
            
                // Adding meta data about the request to the authenticated user object
                authUser.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Store the currently authenticated user in SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(authUser);
            }
        }

        // Passing to next filter on the chain
        filterChain.doFilter(request, response);
    }
}
