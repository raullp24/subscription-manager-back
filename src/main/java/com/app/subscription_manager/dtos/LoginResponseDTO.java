package com.app.subscription_manager.dtos;

public class LoginResponseDTO {
    private String token;
    private UserPrivateDTO user;


    public LoginResponseDTO(){

    }

    public LoginResponseDTO(String token, UserPrivateDTO user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public UserPrivateDTO getUser() {
        return user;
    }
    public void setUser(UserPrivateDTO user) {
        this.user = user;
    }

    

}
