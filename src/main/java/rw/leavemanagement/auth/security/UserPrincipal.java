package rw.leavemanagement.auth.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rw.leavemanagement.auth.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
    private String id; // User ID
    private String email; // User email
    @JsonIgnore
    private String password; // User password
    private String username; // User username

    private Collection<? extends GrantedAuthority> authorities; // User authorities

    public static UserPrincipal create(User user) {
        // List to hold granted authorities
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Assign authorities based on user role
        switch (user.getRole()) {
            case SUPER_ADMIN -> {
                authorities.add(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"));
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            }
            case ADMIN -> {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            }
            case USER -> {
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            }
            default -> throw new IllegalArgumentException("Unknown role: " + user.getRole());
        }



        // Create and return a new UserPrincipal object
        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getEmail(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities; // Return user authorities
    }

    @Override
    public String getPassword() {
        return password; // Return user password
    }

    @Override
    public String getUsername() {
        return username; // Return user username (or email in this case)
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // User account is always non-expired
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // User account is never locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // User credentials are always valid
    }

    @Override
    public boolean isEnabled() {
        return true; // User is always enabled
    }
}
