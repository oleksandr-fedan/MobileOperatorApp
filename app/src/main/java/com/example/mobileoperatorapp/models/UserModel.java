package com.example.mobileoperatorapp.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public class UserModel {
    private int id;
    private String phoneNumber;
    private String name;
    private String surname;
    private String middleName;
    private Double balance;
    private LocalDateTime connectionDate;
    private TariffModel tariff;
    private List<SpendingDetailsModel> activities;

//    public UserModel(int id, String phoneNumber, String name, String surname, String middleName, Double balance, TariffModel tariff, List<SpendingDetailsModel> activities) {
//        this.id = id;
//        this.phoneNumber = phoneNumber;
//        this.name = name;
//        this.surname = surname;
//        this.middleName = middleName;
//        this.balance = balance;
//        this.tariff = tariff;
//        this.activities = activities;
//    }

    public UserModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public LocalDateTime getConnectionDate() {
        return connectionDate;
    }

    public void setConnectionDate(LocalDateTime connectionDate) {
        this.connectionDate = connectionDate;
    }

    public TariffModel getTariff() {
        return tariff;
    }

    public void setTariff(TariffModel tariff) {
        this.tariff = tariff;
    }

    public List<SpendingDetailsModel> getActivities() {
        return activities;
    }

    public void setActivities(List<SpendingDetailsModel> activities) {
        this.activities = activities;
    }
}