// Copyright (c) 2019.
// Tạo bởi Cừu Đen
//
// Gmail: 0331999bbs@gmail.com
// Phone: 0347079556

package com.bbs.mr.beeshoe.Model;

public class Model_SP {
    private int id;
    private String Name;
    private String thump;
    private String all_pic;
    private String info;
    private String size;
    private int color;
    private int count_click;
    private double gia;
    private int sl;
    private int sex;
    private int type;
    private int rate;

    public Model_SP() {
    }

    public Model_SP(int id, String name, String thump, int count_click, double gia, int sex, int type, int rate) {
        this.id = id;
        Name = name;
        this.thump = thump;
        this.count_click = count_click;
        this.gia = gia;
        this.sex = sex;
        this.type = type;
        this.rate = rate;
    }

    public Model_SP(int id, String name, String thump, String all_pic, String info, String size, int color, int count_click, double gia, int sl, int sex, int type, int rate) {
        this.id = id;
        Name = name;
        this.thump = thump;
        this.all_pic = all_pic;
        this.info = info;
        this.size = size;
        this.color = color;
        this.count_click = count_click;
        this.gia = gia;
        this.sl = sl;
        this.sex = sex;
        this.type = type;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getThump() {
        return thump;
    }

    public void setThump(String thump) {
        this.thump = thump;
    }

    public String getAll_pic() {
        return all_pic;
    }

    public void setAll_pic(String all_pic) {
        this.all_pic = all_pic;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getCount_click() {
        return count_click;
    }

    public void setCount_click(int count_click) {
        this.count_click = count_click;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
