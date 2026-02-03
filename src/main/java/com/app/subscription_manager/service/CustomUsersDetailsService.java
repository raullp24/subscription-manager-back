package com.app.subscription_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.subscription_manager.model.Users;
import com.app.subscription_manager.repository.UserRepository;

@Service
public class CustomUsersDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public CustomUsersDetailsService() {

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        UserDetails userDetail = User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .build();
        return userDetail;

    }

}
