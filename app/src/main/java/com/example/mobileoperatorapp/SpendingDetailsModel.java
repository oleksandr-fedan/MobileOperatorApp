package com.example.mobileoperatorapp;

import java.time.LocalDate;
import java.util.Date;

public class SpendingDetailsModel {
    private final LocalDate date;
    private final double quantity;

    public SpendingDetailsModel(LocalDate date, double quantity) {
        this.date = date;
        this.quantity = quantity;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getQuantity() {
        return quantity;
    }
}
