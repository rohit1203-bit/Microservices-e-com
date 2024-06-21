package dev.project.authserver.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenAuthProvider tokenAuthProvider;
    private final JwtFilter jwtFilter;

    @Bean
    AuthenticationManager authManager(HttpSecurity http) throws Exception {
//        var authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        authBuilder.authenticationProvider(tokenAuthProvider);
//
//        return authBuilder.build();
        return new ProviderManager(tokenAuthProvider);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.sessionManagement(session -> session.sessionCreationPolicy(STATELESS));
        http.authorizeHttpRequests()
                // Allow login and register routes
                .requestMatchers("/api/v1/auth/users/login", "/api/v1/auth/users/register").permitAll()
                // Authenticate everything else
                .anyRequest().authenticated();
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
