package com.example.mobileoperatorapp.models;

public class AvailableServicesModel {
    private double availableInternet;
    private int availableMinutes;
    private int availableOtherMinutes;
    private int availableSMS;

    public double getAvailableInternet() {
        return availableInternet;
    }

    public void setAvailableInternet(double availableInternet) {
        this.availableInternet = availableInternet;
    }

    public int getAvailableMinutes() {
        return availableMinutes;
    }

    public void setAvailableMinutes(int availableMinutes) {
        this.availableMinutes = availableMinutes;
    }

    public int getAvailableOtherMinutes() {
        return availableOtherMinutes;
    }

    public void setAvailableOtherMinutes(int availableOtherMinutes) {
        this.availableOtherMinutes = availableOtherMinutes;
    }

    public int getAvailableSMS() {
        return availableSMS;
    }

    public void setAvailableSMS(int availableSMS) {
        this.availableSMS = availableSMS;
    }
}
