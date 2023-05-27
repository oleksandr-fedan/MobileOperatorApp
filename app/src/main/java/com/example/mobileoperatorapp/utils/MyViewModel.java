package com.example.mobileoperatorapp.utils;

import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private String phoneNumber;
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
