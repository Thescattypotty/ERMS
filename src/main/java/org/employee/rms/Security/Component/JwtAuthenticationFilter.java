package org.employee.rms.Security.Component;


import java.io.IOException;

import org.employee.rms.Security.Service.JwtUtilService;
import org.employee.rms.Security.Service.UserDetailsServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    
    private final JwtUtilService jwtUtilService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
        ) throws ServletException, IOException {
        
        log.info("Processing authentication for '{}'", request.getRequestURL());
        
        final String authorizationHeader = request.getHeader("Authorization");
        final String token;
        final String username;

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            log.info("No JWT token found in request headers");
            filterChain.doFilter(request, response);
            return;    
        }
        token = authorizationHeader.substring(7);
        log.info("JWT token found in request headers: {}", token);
        username = jwtUtilService.getEmailFromToken(token);
        log.info("Email extracted from token: {}", username);

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            log.info("Security context was null, authorizing user");
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(jwtUtilService.validateToken(token)){
                log.info("JWT token validated. Authenticating user: {}", username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
                );
                authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                log.info("User authenticated successfully");
            } else {
                log.warn("JWT token validation failed for user: {}", username);
            }
        }
        filterChain.doFilter(request, response);
    }
}