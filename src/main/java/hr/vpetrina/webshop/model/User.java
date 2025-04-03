package hr.vpetrina.webshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class User implements UserDetails {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private UserRole role;

    public User() {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
