package com.app.subscription_manager.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.subscription_manager.dtos.LoginRequestDTO;
import com.app.subscription_manager.dtos.LoginResponseDTO;
import com.app.subscription_manager.dtos.RegisterUserDTO;
import com.app.subscription_manager.dtos.UserPrivateDTO;
import com.app.subscription_manager.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserPrivateDTO> register(@RequestBody RegisterUserDTO registerUserDTO){
        UserPrivateDTO savedUser = userService.createUser(registerUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        LoginResponseDTO response = userService.login(loginRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/test")
    public String test() {
        return "FUNCIONA BIEN EL TOKEN";
    }
    
}
