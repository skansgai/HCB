package com.haochibao.utill.model;

/**
 * Created by Administrator on 2016/12/21.
 */

public class HotPotDetails {
    private String name;
    private Double price;
    private Double grade;
    private String describe;
    private Double taste;
    private Double environment;
    private Double service;
    private String img;
    private String comment;
    private String support;
    private String location;
    private String phone;
    private int zan;
    private String user_name;
    private String time;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getVisited() {
        return visited;
    }

    public void setVisited(int visited) {
        this.visited = visited;
    }

    private int visited;

    public String getName(String name) {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public String getDescribe() {
        return describe;
    }
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Double getTaste() {
        return taste;
    }

    public void setTaste(Double taste) {
        this.taste = taste;
    }

    public Double getEnvironment() {
        return environment;
    }

    public void setEnvironment(Double environment) {
        this.environment = environment;
    }

    public Double getService() {
        return service;
    }

    public void setService(Double service) {
        this.service = service;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }
}
