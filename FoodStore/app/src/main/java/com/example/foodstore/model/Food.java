package com.example.foodstore.model;

public class Food {
    private String idfood;
    private String namefood;
    private double gia;
    private int soluong;
    private String image;
    private String diachi;
    private String mota;
    private String status;
    private String matheloai;
    private String idstore;
    private String tokenstore;



    public Food() {
    }

    public Food(String idfood, String namefood, double gia, int soluong, String image, String diachi, String mota, String status, String matheloai, String idstore, String tokenstore) {
        this.idfood = idfood;
        this.namefood = namefood;
        this.gia = gia;
        this.soluong = soluong;
        this.image = image;
        this.diachi = diachi;
        this.mota = mota;
        this.status = status;
        this.matheloai = matheloai;
        this.idstore = idstore;
        this.tokenstore = tokenstore;
    }

    public String getTokenstore() {
        return tokenstore;
    }

    public void setTokenstore(String tokenstore) {
        this.tokenstore = tokenstore;
    }

    public String getIdfood() {
        return idfood;
    }

    public void setIdfood(String idfood) {
        this.idfood = idfood;
    }

    public String getNamefood() {
        return namefood;
    }

    public void setNamefood(String namefood) {
        this.namefood = namefood;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
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

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMatheloai() {
        return matheloai;
    }

    public void setMatheloai(String matheloai) {
        this.matheloai = matheloai;
    }

    public String getIdstore() {
        return idstore;
    }

    public void setIdstore(String idstore) {
        this.idstore = idstore;
    }
}
