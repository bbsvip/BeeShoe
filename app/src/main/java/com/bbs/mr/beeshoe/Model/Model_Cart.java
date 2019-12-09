// Copyright (c) 2019.
// Tạo bởi Cừu Đen
//
// Gmail: 0331999bbs@gmail.com
// Phone: 0347079556

package com.bbs.mr.beeshoe.Model;

//Tạo bới Cừu Đen
//Email: 0331999bbs@gmail.com
//Phone: 0347079556
public class Model_Cart {

    int sl_cart;
    double gia;
    String name,img;
    int id_acc,id_sp;
    String date;

    public Model_Cart(int sl_cart, double gia, String name, String img, int id_acc, int id_sp, String date) {
        this.sl_cart = sl_cart;
        this.gia = gia;
        this.name = name;
        this.img = img;
        this.id_acc = id_acc;
        this.id_sp = id_sp;
        this.date = date;
    }

    public Model_Cart(int sl_cart, double gia, String name, String img) {
        this.sl_cart = sl_cart;
        this.gia = gia;
        this.name = name;
        this.img = img;
    }

    public int getId_acc() {
        return id_acc;
    }

    public void setId_acc(int id_acc) {
        this.id_acc = id_acc;
    }

    public int getId_sp() {
        return id_sp;
    }

    public void setId_sp(int id_sp) {
        this.id_sp = id_sp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSl_cart() {
        return sl_cart;
    }

    public void setSl_cart(int sl_cart) {
        this.sl_cart = sl_cart;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
