package com.example.auth_jwt.security.jwtFilter;

import com.example.auth_jwt.security.services.JwtService;
import com.example.auth_jwt.security.services.UsersAccountsDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UsersAccountsDetailsServiceImpl usersAccountsService;
    private final JwtService jwtService;

    @Autowired
    public JwtAuthFilter(UsersAccountsDetailsServiceImpl usersAccountsService,
                         JwtService jwtService) {
        this.usersAccountsService = usersAccountsService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        // Retrieve the Authorization header
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        // Check if the header starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // extract token
            email = jwtService.extractEmail(token); // extract email from token
        }

        // if the token is valid and no authentication is set in the context
        if (email != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = usersAccountsService.loadUserByUsername(email);

            // validate token and set authentication
            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        //continue the filter chain
        filterChain.doFilter(request, response);
    }
}
