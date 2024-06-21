package dev.project.authserver.security;

import dev.project.authserver.model.User;
import dev.project.authserver.service.JwtService;
import dev.project.authserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenAuthProvider implements AuthenticationProvider {
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String token = authentication.getName();
        if(jwtService.isExpired(token)) throw new BadCredentialsException("Token Expired");
        String username = jwtService.decodeToken(token);
        // find user in database
        var user = (User) userService.loadUserByUsername(username);

        // return authenticated user
        return new TokenAuthentication(username, null, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenAuthentication.class.isAssignableFrom(authentication);
    }
}
