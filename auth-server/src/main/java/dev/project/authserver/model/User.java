package dev.project.authserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static jakarta.persistence.GenerationType.AUTO;

@Entity(name = "users")
@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    // Personal data
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    // Credentials
    private String username;
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
