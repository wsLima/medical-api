package br.com.wsystechnologies.medical.infrastructure.security;

import br.com.wsystechnologies.medical.domain.enums.Permission;
import br.com.wsystechnologies.medical.domain.enums.Role;
import br.com.wsystechnologies.medical.domain.model.Profile;
import br.com.wsystechnologies.medical.domain.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
public class UserPrincipal implements UserDetails {

    private final UUID userId;
    private final UUID clinicId;
    private final Role role;
    private final String email;
    private final String password;
    private final boolean enabled;

    public UserPrincipal(User user, Profile profile) {
        this.userId = user.getId();
        this.clinicId = profile.getClinic().getId();
        this.role = profile.getRole();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.enabled = user.isEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Permission> perms = RolePermissions.getPermissions(role);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        perms.forEach(p -> authorities.add(new SimpleGrantedAuthority("PERM_" + p.name())));
        return authorities;
    }

    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return email; }
    @Override public boolean isEnabled() { return enabled; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }

    public UUID getUserId() { return userId; }
    public UUID getClinicId() { return clinicId; }
    public Role getRole() { return role; }
}

