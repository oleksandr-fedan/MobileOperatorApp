package com.example.mobileoperatorapp;

public class ServiceDetailsModel {
    private final String detailName;
    private final double detailQuantity;

    public ServiceDetailsModel(String detailName, double detailQuantity) {
        this.detailName = detailName;
        this.detailQuantity = detailQuantity;
    }

    public String getDetailName() {
        return detailName;
    }

    public double getDetailQuantity() {
        return detailQuantity;
    }
}
