package dev.project.authserver.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Autowired @Lazy
    private AuthenticationManager authManager;

    // Do not filter if request for login or register
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().contains("register") ||
                request.getServletPath().contains("login");
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        // get auth header
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(header == null || !header.startsWith("Bearer")) {
            log.info("No Header Provided");
            response.setStatus(403);
            return; // return the forbidden response
        }
        // extract token
        final String token = header.substring(7);

        TokenAuthentication auth = new TokenAuthentication(token);
        try {
            var authToken = authManager.authenticate(auth);
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }catch (Exception ex) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request,response);
    }
}
