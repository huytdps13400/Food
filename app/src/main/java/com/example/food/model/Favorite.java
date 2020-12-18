package com.example.food.model;

public class Favorite {
    private String idfavorite;
    private String uiduser;
    Food idfood;
    boolean checkfavorite;

    public Favorite() {
    }

    public Favorite(String idfavorite, String uiduser, Food idfood, boolean checkfavorite) {
        this.idfavorite = idfavorite;
        this.uiduser = uiduser;
        this.idfood = idfood;
        this.checkfavorite = checkfavorite;
    }

    public Food getIdfood() {
        return idfood;
    }

    public void setIdfood(Food idfood) {
        this.idfood = idfood;
    }

    public boolean isCheckfavorite() {
        return checkfavorite;
    }

    public void setCheckfavorite(boolean checkfavorite) {
        this.checkfavorite = checkfavorite;
    }

    public String getIdfavorite() {
        return idfavorite;
    }

    public void setIdfavorite(String idfavorite) {
        this.idfavorite = idfavorite;
    }

    public String getUiduser() {
        return uiduser;
    }

    public void setUiduser(String uiduser) {
        this.uiduser = uiduser;
    }


}
