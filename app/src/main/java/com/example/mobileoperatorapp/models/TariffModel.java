package com.example.mobileoperatorapp.models;

import java.util.List;

public class TariffModel {
    private int id;
    private String name;
    private double price;
    private double internetQuantity;
    private double minutesQuantity;
    private double otherMinutesQuantity;
    private int smsQuantity;
    private List<UserModel> users;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getInternetQuantity() {
        return internetQuantity;
    }

    public void setInternetQuantity(double internetQuantity) {
        this.internetQuantity = internetQuantity;
    }

    public double getMinutesQuantity() {
        return minutesQuantity;
    }

    public void setMinutesQuantity(double minutesQuantity) {
        this.minutesQuantity = minutesQuantity;
    }

    public double getOtherMinutesQuantity() {
        return otherMinutesQuantity;
    }

    public void setOtherMinutesQuantity(double otherMinutesQuantity) {
        this.otherMinutesQuantity = otherMinutesQuantity;
    }

    public int getSmsQuantity() {
        return smsQuantity;
    }

    public void setSmsQuantity(int smsQuantity) {
        this.smsQuantity = smsQuantity;
    }

    public List<UserModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }
}
