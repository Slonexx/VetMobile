package com.example.vetmobile.DateBase.Model;

import java.util.List;

public class UserModel {
    private int id;
    private String name;
    private String photo_id;
    private String phone;
    private String email;
    private String password;
    private String token;
    UserModel User;

    private List<AnimailModel> Animal;

    public List<AnimailModel> getAnimal() {
        return Animal;
    }

    public UserModel getUser() {
        return User;
    }

    public String getToken() {
        return token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(String photo_id) {
        this.photo_id = photo_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
