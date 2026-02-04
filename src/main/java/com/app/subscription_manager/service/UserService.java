package com.app.subscription_manager.service;


import com.app.subscription_manager.dtos.LoginRequestDTO;
import com.app.subscription_manager.dtos.LoginResponseDTO;
import com.app.subscription_manager.dtos.RegisterUserDTO;
import com.app.subscription_manager.dtos.UserPrivateDTO;

public interface UserService {

    UserPrivateDTO createUser(RegisterUserDTO registerUserDTO);
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

}
