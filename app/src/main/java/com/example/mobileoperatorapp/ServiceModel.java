package com.example.mobileoperatorapp;

import java.util.List;

public class ServiceModel {
    private final String description;
    private final ServiceDetailsAdapter adapter;
    private final double price;

    public ServiceModel(String description, ServiceDetailsAdapter adapter, double price) {
        this.description = description;
        this.adapter = adapter;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public ServiceDetailsAdapter getAdapter() {
        return adapter;
    }

    public double getPrice() {
        return price;
    }
}
