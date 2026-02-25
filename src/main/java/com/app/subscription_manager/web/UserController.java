package com.app.subscription_manager.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.subscription_manager.dtos.ChangePasswordDTO;
import com.app.subscription_manager.dtos.UserPrivateDTO;
import com.app.subscription_manager.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/{id}")
    public ResponseEntity<UserPrivateDTO> updateUser(@PathVariable String id, @RequestBody UserPrivateDTO userPrivateDTO){
            UserPrivateDTO updatedUser = userService.update(id, userPrivateDTO);
            return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PostMapping("/change-password/{user_id}")
    public ResponseEntity<Void> changePassword(@PathVariable("user_id") String userId, @RequestBody ChangePasswordDTO newPassword){
        userService.changePassword(userId, newPassword);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
