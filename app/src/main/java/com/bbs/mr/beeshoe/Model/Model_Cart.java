package com.bbs.mr.beeshoe.Model;

//Tạo bới Cừu Đen
//Email: 0331999bbs@gmail.com
//Phone: 0347079556
public class Model_Cart {

    int id_cart,id_acc,id_sp,sl_cart;
    double total_cart,gia;
    String name;
    String img;

    public Model_Cart(int id_cart, int id_acc, int id_sp, int sl_cart, double total_cart, String img) {
        this.id_cart = id_cart;
        this.id_acc = id_acc;
        this.id_sp = id_sp;
        this.sl_cart = sl_cart;
        this.total_cart = total_cart;
        this.img = img;
    }

    public Model_Cart(int sl_cart, double gia, String name, String img) {
        this.sl_cart = sl_cart;
        this.gia = gia;
        this.name = name;
        this.img = img;
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

    public int getId_cart() {
        return id_cart;
    }

    public void setId_cart(int id_cart) {
        this.id_cart = id_cart;
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

    public int getSl_cart() {
        return sl_cart;
    }

    public void setSl_cart(int sl_cart) {
        this.sl_cart = sl_cart;
    }

    public double getTotal_cart() {
        return total_cart;
    }

    public void setTotal_cart(double total_cart) {
        this.total_cart = total_cart;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
