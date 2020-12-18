package com.example.foodstore.model;

public class ChatUserStore {
    private String id;
    private User user;
    private Store store;

    public ChatUserStore() {
    }

    public ChatUserStore(String id, User user, Store store) {
        this.id = id;
        this.user = user;
        this.store = store;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
