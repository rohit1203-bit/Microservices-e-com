package dev.project.authserver.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class TokenAuthentication implements Authentication {
    private String username;
    private Object credentials;
    private String token;
    private boolean authenticated;

    public TokenAuthentication(String token) {
        this.token = token;
        this.setAuthenticated(false);
    }

    public TokenAuthentication(String username,Object credentials,  Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.credentials = credentials;
        this.setAuthenticated(true);
    }

    public TokenAuthentication(String username, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.setAuthenticated(true);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getDetails() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return this.token;
    }
}
