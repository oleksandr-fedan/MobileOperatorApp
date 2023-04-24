package com.example.mobileoperatorapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerConnection {
    private final String endPoint = "http://192.168.0.104:12531/";

    public String getLoginCode(String userPhoneNumber) {
        HttpURLConnection connection = null;
        String code = null;

        try {
            connection = (HttpURLConnection) (new URL(endPoint + "api/user/" + userPhoneNumber).openConnection());
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            code = in.readLine();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return code;
    }

    public boolean checkCode(String userPhoneNumber, String code) {
        HttpURLConnection connection = null;
        boolean result = false;
        try {
            connection = (HttpURLConnection) (new URL(endPoint + "api/user/" + userPhoneNumber + " " + code).openConnection());
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            result = Boolean.parseBoolean(in.readLine());
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
