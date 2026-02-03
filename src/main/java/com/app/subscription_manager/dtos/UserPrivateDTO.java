package com.app.subscription_manager.dtos;

import com.app.subscription_manager.model.Users;

public class UserPrivateDTO {
    
    private String id;
    private String name;
    private String surname;
    private String email;
    private String password;


    public UserPrivateDTO(){

    }

    public UserPrivateDTO(Users user){
        this.id  = user.getId();
        this.name = user.getName();
        this.surname = user.getName();
        this.email = user.getEmail();
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getSurname() {
        return surname;
    }


    public void setSurname(String surname) {
        this.surname = surname;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

}
