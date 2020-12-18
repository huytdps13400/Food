package com.example.foodstore.model;

import java.util.ArrayList;

public class HDCT {
    private String idhct;
    private String idhdon;
    String ngay;
    String thoigian;
    boolean check;

    ArrayList<Order> orderArrayList;
    public HDCT() {
    }

    public HDCT(String idhct, String idhdon, String ngay, String thoigian, boolean check, ArrayList<Order> orderArrayList) {
        this.idhct = idhct;
        this.idhdon = idhdon;
        this.ngay = ngay;
        this.thoigian = thoigian;
        this.check = check;

        this.orderArrayList = orderArrayList;
    }

    public String getIdhct() {
        return idhct;
    }

    public void setIdhct(String idhct) {
        this.idhct = idhct;
    }

    public String getIdhdon() {
        return idhdon;
    }

    public void setIdhdon(String idhdon) {
        this.idhdon = idhdon;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public ArrayList<Order> getOrderArrayList() {
        return orderArrayList;
    }

    public void setOrderArrayList(ArrayList<Order> orderArrayList) {
        this.orderArrayList = orderArrayList;
    }





}
