package com.lucasmoraist.luflix.infrastructure.security.service;

import com.lucasmoraist.luflix.domain.model.User;
import com.lucasmoraist.luflix.infrastructure.database.persistence.UserPersistence;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserPersistence userPersistence;

    public CustomUserDetailsService(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userPersistence.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + user.role().name())
        );

        return new org.springframework.security.core.userdetails.User(
                user.email(),
                user.password(),
                authorities
        );
    }

}
