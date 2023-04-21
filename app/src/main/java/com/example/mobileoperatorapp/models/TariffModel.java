package com.example.mobileoperatorapp.models;

public class TariffModel {
    private final String name;
    private final double internetQuantity;
    private final double minutesQuantity;
    private final double otherMinutesQuantity;
    private final int smsQuantity;
    private final double price;

    public TariffModel(String name, double internetQuantity, double minutesQuantity, double otherMinutesQuantity, int smsQuantity, double price) {
        this.name = name;
        this.internetQuantity = internetQuantity;
        this.minutesQuantity = minutesQuantity;
        this.otherMinutesQuantity = otherMinutesQuantity;
        this.smsQuantity = smsQuantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getInternetQuantity() {
        return internetQuantity;
    }

    public double getMinutesQuantity() {
        return minutesQuantity;
    }

    public double getOtherMinutesQuantity() {
        return otherMinutesQuantity;
    }

    public int getSmsQuantity() {
        return smsQuantity;
    }

    public double getPrice() {
        return price;
    }
}
