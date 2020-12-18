package com.example.admin.model;

public class Categories {
    private String matheloai;
    private String tentheloai;
    private String mota;
    private String image;

    public Categories() {
    }

    public Categories(String matheloai, String tentheloai, String mota, String image) {
        this.matheloai = matheloai;
        this.tentheloai = tentheloai;
        this.mota = mota;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMatheloai() {
        return matheloai;
    }

    public void setMatheloai(String matheloai) {
        this.matheloai = matheloai;
    }

    public String getTentheloai() {
        return tentheloai;
    }

    public void setTentheloai(String tentheloai) {
        this.tentheloai = tentheloai;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
}
