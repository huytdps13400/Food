package com.example.food.model;

public class Order {
    String idorder;
    Food food;
    private int soluongmua;
    Store store;
    User user;

    public Order() {
    }

    public Order(String idorder, Food food, int soluongmua, Store store, User user) {
        this.idorder = idorder;
        this.food = food;
        this.soluongmua = soluongmua;
        this.store = store;
        this.user = user;
    }

    public String getIdorder() {
        return idorder;
    }

    public void setIdorder(String idorder) {
        this.idorder = idorder;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getSoluongmua() {
        return soluongmua;
    }

    public void setSoluongmua(int soluongmua) {
        this.soluongmua = soluongmua;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
