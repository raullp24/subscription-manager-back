package com.app.subscription_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.subscription_manager.dtos.RegisterUserDTO;
import com.app.subscription_manager.dtos.UserPrivateDTO;
import com.app.subscription_manager.model.Users;
import com.app.subscription_manager.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserPrivateDTO createUser(RegisterUserDTO registerUserDTO) {

        if(userRepository.existsByEmail(registerUserDTO.getEmail())){
            throw new RuntimeException("User with email: " + registerUserDTO.getEmail() + " already exists");
        }
        
        Users user = new Users();
        user.setEmail(registerUserDTO.getEmail());
        user.setName(registerUserDTO.getName());
        user.setSurname(registerUserDTO.getSurname());
        user.setPassword( passwordEncoder.encode(registerUserDTO.getPassword()));

        return new UserPrivateDTO(userRepository.save(user));

    }

    


}
