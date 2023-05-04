package com.example.mobileoperatorapp;

import com.example.mobileoperatorapp.models.TariffModel;
import com.example.mobileoperatorapp.models.UserModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ServerConnection {
    private final String endPoint = "http://192.168.0.107:12531/";

    public String getLoginCode(String userPhoneNumber) {
        HttpURLConnection connection = null;
        String code = null;

        try {
            connection = (HttpURLConnection) (new URL(endPoint + "api/login/" + userPhoneNumber).openConnection());
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
            connection = (HttpURLConnection) (new URL(endPoint + "api/login/" + userPhoneNumber + " " + code).openConnection());
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            result = Boolean.parseBoolean(in.readLine());
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public UserModel getUser(String userPhoneNumber) {
        HttpURLConnection connection;
        UserModel user = null;
        try {
            connection = (HttpURLConnection) (new URL(endPoint + "api/user/" + userPhoneNumber).openConnection());
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            String json = response.toString();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            user = objectMapper.readValue(json, UserModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }

    public List<TariffModel> getTariffs() {
        HttpURLConnection connection;

        List<TariffModel> tariffs = null;
        try {
            connection = (HttpURLConnection) (new URL(endPoint + "api/tariff/get_tariffs").openConnection());
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            String json = response.toString();

            ObjectMapper objectMapper = new ObjectMapper();
            tariffs = objectMapper.readValue(json, new TypeReference<List<TariffModel>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tariffs;
    }
}
