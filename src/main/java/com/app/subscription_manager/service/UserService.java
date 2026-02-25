package com.app.subscription_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.subscription_manager.config.JwtUtil;
import com.app.subscription_manager.dtos.LoginRequestDTO;
import com.app.subscription_manager.dtos.LoginResponseDTO;
import com.app.subscription_manager.dtos.RegisterUserDTO;
import com.app.subscription_manager.dtos.UserPrivateDTO;
import com.app.subscription_manager.exception.InvalidCredentialsException;
import com.app.subscription_manager.exception.UserAlreadyExistsException;
import com.app.subscription_manager.exception.UserNotFoundException;
import com.app.subscription_manager.model.Users;
import com.app.subscription_manager.repository.UserRepository;

@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public UserPrivateDTO createUser(RegisterUserDTO registerUserDTO) throws UserAlreadyExistsException{

        if(userRepository.existsByEmail(registerUserDTO.getEmail())){
            throw new UserAlreadyExistsException(registerUserDTO.getEmail());
        }
        
        Users user = new Users();
        user.setEmail(registerUserDTO.getEmail());
        user.setName(registerUserDTO.getName());
        user.setSurname(registerUserDTO.getSurname());
        user.setPassword( passwordEncoder.encode(registerUserDTO.getPassword()));

        return new UserPrivateDTO(userRepository.save(user));

    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) throws UserNotFoundException,InvalidCredentialsException{

        Users user = userRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow(() -> new UserNotFoundException(loginRequestDTO.getEmail()));

        if(!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(loginRequestDTO.getEmail());

        UserPrivateDTO userPrivateDTO = new UserPrivateDTO(user);

        return new LoginResponseDTO(token,userPrivateDTO);
    }

    public UserPrivateDTO update(String id, UserPrivateDTO userPrivateDTO) throws UserNotFoundException{

        Users user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        user.setName(userPrivateDTO.getName());
        user.setSurname(userPrivateDTO.getSurname());

        return new UserPrivateDTO(userRepository.save(user));
    }

    public UserPrivateDTO changePassword(String id, String newPassword) throws UserNotFoundException{

        Users user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        user.setPassword(passwordEncoder.encode(newPassword));

        return new UserPrivateDTO(userRepository.save(user));
    }
}
