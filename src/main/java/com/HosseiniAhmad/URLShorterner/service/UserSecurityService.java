package com.HosseiniAhmad.URLShorterner.service;

import com.HosseiniAhmad.URLShorterner.model.entity.user.User;
import com.HosseiniAhmad.URLShorterner.repository.UserRepository;
import com.HosseiniAhmad.URLShorterner.security.UserWithCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserWithCredentials(getUserByUsername(username).orElseThrow(
                () -> new NoSuchElementException("User with username=" + username + " not found")
        ));
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
