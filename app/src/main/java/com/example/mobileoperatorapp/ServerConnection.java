package com.example.mobileoperatorapp;

import android.util.Log;

import com.example.mobileoperatorapp.models.ActivityModel;
import com.example.mobileoperatorapp.models.ActivityType;
import com.example.mobileoperatorapp.models.AvailableServicesModel;
import com.example.mobileoperatorapp.models.ServiceModel;
import com.example.mobileoperatorapp.models.TariffModel;
import com.example.mobileoperatorapp.models.UserModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ServerConnection {
    private final String host = "http://192.168.0.102:12531/";

    public String getLoginCode(String userPhoneNumber) {
        HttpURLConnection connection = null;
        String code = null;

        try {
            connection = (HttpURLConnection) (new URL(host + "api/login/" + userPhoneNumber).openConnection());
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
            connection = (HttpURLConnection) (new URL(host + "api/login/" + userPhoneNumber + " " + code).openConnection());
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
            connection = (HttpURLConnection) (new URL(host + "api/user/" + userPhoneNumber).openConnection());
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
            connection = (HttpURLConnection) (new URL(host + "api/tariff/get_tariffs").openConnection());
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

    public List<ActivityModel> getUserActivities(String userPhoneNumber, ActivityType type) {
        String url = null;
        switch (type) {
            case INTERNET:
                url = "api/activity/get_internet_activity/" + userPhoneNumber;
                break;
            case MINUTES:
                url = "api/activity/get_minutes_activity/" + userPhoneNumber;
                break;
            case OTHER_MINUTES:
                url = "api/activity/get_other_minutes_activity/" + userPhoneNumber;
                break;
            case SMS:
                url = "api/activity/get_sms_activity/" + userPhoneNumber;
                break;
        }

        HttpURLConnection connection;

        List<ActivityModel> activities = null;

        try {
            connection = (HttpURLConnection) (new URL(host + url).openConnection());
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
            activities = objectMapper.readValue(json, new TypeReference<List<ActivityModel>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return activities;
    }

    public List<ServiceModel> getServices() {
        String url = "api/service/";

        HttpURLConnection connection;
        List<ServiceModel> services = null;

        try {
            connection = (HttpURLConnection) (new URL(host + url).openConnection());
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
            services = objectMapper.readValue(json, new TypeReference<List<ServiceModel>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return services;
    }

    public List<ServiceModel> getUserServices(String userPhoneNumber) {
        String url = "api/service/" + userPhoneNumber;

        HttpURLConnection connection;
        List<ServiceModel> services = null;

        try {
            connection = (HttpURLConnection) (new URL(host + url).openConnection());
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
            services = objectMapper.readValue(json, new TypeReference<List<ServiceModel>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return services;
    }

    public boolean connectServiceToUser(String userPhoneNumber, int serviceId) {
        String url = "api/user/connect_service";
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) (new URL(host + url).openConnection());

            // Установка метода запроса и свойств
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Создание объекта ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            // Создание объекта ObjectNode
            ObjectNode jsonNode = objectMapper.createObjectNode();
            // Добавление свойств в JSON-объект
            jsonNode.put("userPhoneNumber", userPhoneNumber);
            jsonNode.put("serviceId", serviceId);
            String requestBody = jsonNode.toString();

            connection.setRequestProperty("Content-Type", "application/json");
            // Запись requestBody в OutputStream
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestBody.getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return true;
            }
            else
                return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deposit(String userPhoneNumber, double deposit) {
        String url = "api/user/deposit";
        HttpURLConnection connection;

        try {
            connection = (HttpURLConnection) (new URL(host + url).openConnection());

            // Установка метода запроса и свойств
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Создание объекта ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            // Создание объекта ObjectNode
            ObjectNode jsonNode = objectMapper.createObjectNode();
            // Добавление свойств в JSON-объект
            jsonNode.put("phoneNumber", userPhoneNumber);
            jsonNode.put("deposit", deposit);
            String requestBody = jsonNode.toString();

            connection.setRequestProperty("Content-Type", "application/json");
            // Запись requestBody в OutputStream
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestBody.getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateTariff(String userPhoneNumber) {
        String url = "api/user/update_tariff";
        HttpURLConnection connection;

        try {
            connection = (HttpURLConnection) (new URL(host + url).openConnection());

            // Установка метода запроса и свойств
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Создание объекта ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            // Создание объекта ObjectNode
            ObjectNode jsonNode = objectMapper.createObjectNode();
            // Добавление свойств в JSON-объект
            jsonNode.put("phoneNumber", userPhoneNumber);
            String requestBody = jsonNode.toString();

            connection.setRequestProperty("Content-Type", "application/json");
            // Запись requestBody в OutputStream
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestBody.getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connectTariffToUser(String userPhoneNumber, int tariffId) {
        String url = "api/user/connect_tariff";
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) (new URL(host + url).openConnection());

            // Установка метода запроса и свойств
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Создание объекта ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            // Создание объекта ObjectNode
            ObjectNode jsonNode = objectMapper.createObjectNode();
            // Добавление свойств в JSON-объект
            jsonNode.put("userPhoneNumber", userPhoneNumber);
            jsonNode.put("tariffId", tariffId);
            String requestBody = jsonNode.toString();

            connection.setRequestProperty("Content-Type", "application/json");
            // Запись requestBody в OutputStream
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestBody.getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AvailableServicesModel getAvailableServices(String phoneNumber) {
        String url = "api/user/available_services/" + phoneNumber;
        HttpURLConnection connection;
        AvailableServicesModel availableServices = null;
        try {
            connection = (HttpURLConnection) (new URL(host + url).openConnection());
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

            availableServices = objectMapper.readValue(json, AvailableServicesModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return availableServices;
    }
}
