package com.bbs.mr.beeshoe.Model;

//Tạo bới Cừu Đen
//Email: 0331999bbs@gmail.com
//Phone: 0347079556
public class Model_Cart {

    int sl_cart;
    double gia;
    String name,img;

    public Model_Cart(int sl_cart, double gia, String name, String img) {
        this.sl_cart = sl_cart;
        this.gia = gia;
        this.name = name;
        this.img = img;
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
