package com.zangesterra.burgerQueen.service;

import com.zangesterra.burgerQueen.entity.Permission;
import com.zangesterra.burgerQueen.entity.Role;
import com.zangesterra.burgerQueen.entity.User;
import com.zangesterra.burgerQueen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).
                orElseThrow(() ->
                        new UsernameNotFoundException(String.format("User with username or email %s not found", email)));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword()
                , true, true, true, true,
                getSimpleGrantedAuthority(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getSimpleGrantedAuthority(Set<Role> roles) {
        Set<String> collections = new HashSet<>();
        Set<Permission> permissions = new HashSet<>();

        for (var role: roles){
            collections.add(role.getName());
            permissions.addAll(role.getPermissions());
        }
        for (var permission : permissions){
            collections.add(permission.getName());
        }

        return collections.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
