package br.com.wsystechnologies.medical.infrastructure.security;

import br.com.wsystechnologies.medical.domain.model.Profile;
import br.com.wsystechnologies.medical.domain.model.User;
import br.com.wsystechnologies.medical.domain.repository.ProfileRepository;
import br.com.wsystechnologies.medical.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // O Profile tem o mesmo ID do User (MapsId)
        Profile profile = profileRepository.findById(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Profile not found for user: " + email));

        return new UserPrincipal(user, profile);
    }
}
