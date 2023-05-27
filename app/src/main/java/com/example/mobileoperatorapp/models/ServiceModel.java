package com.example.mobileoperatorapp.models;

public class ServiceModel {
    private int id;
    private String name;
    private String description;
    private double price;
    private double internetQuantity;
    private int minutesQuantity;
    private int otherMinutesQuantity;
    private int smsQuantity;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getMinutesQuantity() {
        return minutesQuantity;
    }

    public void setMinutesQuantity(int minutesQuantity) {
        this.minutesQuantity = minutesQuantity;
    }

    public int getOtherMinutesQuantity() {
        return otherMinutesQuantity;
    }

    public void setOtherMinutesQuantity(int otherMinutesQuantity) {
        this.otherMinutesQuantity = otherMinutesQuantity;
    }

    public int getSmsQuantity() {
        return smsQuantity;
    }

    public void setSmsQuantity(int smsQuantity) {
        this.smsQuantity = smsQuantity;
    }
}
