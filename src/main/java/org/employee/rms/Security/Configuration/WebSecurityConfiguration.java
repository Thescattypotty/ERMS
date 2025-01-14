package org.employee.rms.Security.Configuration;

import org.employee.rms.Security.Component.AuthenticationEntryPointJwt;
import org.employee.rms.Security.Component.JwtAuthenticationFilter;
import org.employee.rms.Security.Component.JwtLogoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfiguration {
    
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationEntryPointJwt authenticationEntryPoint;
    private final AuthenticationProvider authenticationProvider;
    private final JwtLogoutHandler jwtLogoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        log.info("Configuring security filter chain");
        return httpSecurity
            .csrf(CsrfConfigurer::disable)
            .authorizeHttpRequests(
                request -> {
                    log.info("Configuring request matchers");
                    request.anyRequest().permitAll();
                }
            )
            .exceptionHandling(exception -> {
                log.info("Configuring exception handling");
                exception.authenticationEntryPoint(authenticationEntryPoint);
            })
            .sessionManagement(manager -> {
                log.info("Configuring session management");
                manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            })
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(
                jwtAuthenticationFilter, 
                UsernamePasswordAuthenticationFilter.class
            )
            .logout(logout -> {
                log.info("Configuring logout");
                logout.logoutUrl("/api/v1/auth/logout")
                    .addLogoutHandler(jwtLogoutHandler)
                    .logoutSuccessHandler((request, response, authentication) -> {
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.getWriter().write("Logout successful");
                    });
            })

            .build();
    }
    
}
