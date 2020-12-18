package com.example.admin.model;

public class Store {
    private String email;
    private String pass;
    private String name;
    private String phone;
    private String image;
    private String diachi;
    private String status;
    private String tokenstore;

    public Store() {
    }

    public Store(String email, String pass, String name, String phone, String image, String diachi, String status, String tokenstore) {
        this.email = email;
        this.pass = pass;
        this.name = name;
        this.phone = phone;
        this.image = image;
        this.diachi = diachi;
        this.status = status;
        this.tokenstore = tokenstore;
    }

    public String getTokenstore() {
        return tokenstore;
    }

    public void setTokenstore(String tokenstore) {
        this.tokenstore = tokenstore;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
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
}