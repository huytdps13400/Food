package com.example.foodstore.model;

public class User {
    private String email;
    private String password;
    private String name;
    private String phone;
    private String image;
    private String diachi;
    String token;

    public User() {
    }

    public User(String email, String password, String name, String phone, String image, String diachi, String token) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.image = image;
        this.diachi = diachi;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
