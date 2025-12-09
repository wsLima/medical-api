package br.com.wsystechnologies.medical.infrastructure.security;

import br.com.wsystechnologies.medical.domain.enums.Role;
import br.com.wsystechnologies.medical.domain.model.Account;
import br.com.wsystechnologies.medical.domain.model.Profile;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
public class UserPrincipal implements UserDetails {

    private final UUID accountId;
    private final UUID clinicId;
    private final Role role;
    private final String email;
    private final String password;
    private final boolean enabled;

    public UserPrincipal(Account account, Profile profile) {
        this.accountId = account.getId();
        this.clinicId = profile.getClinic().getId();
        this.role = profile.getRole();
        this.email = account.getEmail();
        this.password = account.getPassword();
        this.enabled = account.isEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
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

    public UUID getAccountId() {
        return accountId;
    }

    public UUID getClinicId() {
        return clinicId;
    }

    public Role getRole() {
        return role;
    }
}

