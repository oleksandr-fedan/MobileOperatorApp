package com.example.mobileoperatorapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mobileoperatorapp.R;
import com.example.mobileoperatorapp.ServerConnection;
import com.santalu.maskara.widget.MaskEditText;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppCompatButton loginBt = findViewById(R.id.activity_login__bt);
        Button getCodeBt = findViewById(R.id.activity_login__get_code_bt);
        EditText codeET = findViewById(R.id.activity_login__code_et);
        MaskEditText userNumberMET = findViewById(R.id.activity_login__number_met);
        TextView errorTV = findViewById(R.id.activity_login__error_tv);

        getCodeBt.setOnClickListener(view -> new Thread(new Runnable() {
            boolean emptyNumber = false;
            String code = null;

            @Override
            public void run() {
                if (userNumberMET.getUnMasked().isEmpty())
                    emptyNumber = true;
                else {
                    ServerConnection connection = new ServerConnection();
                    code = connection.getLoginCode(userNumberMET.getUnMasked());
                }

                runOnUiThread(() -> {
                    errorTV.setVisibility(View.VISIBLE);
                    errorTV.setTextColor((ContextCompat.getColor(view.getContext(), R.color.error_red)));

                    if (emptyNumber) errorTV.setText("Введіть номер");
                    else if (code == null || code.isEmpty()) { //не понятно почему код == елс всегда фолс ?????
                        errorTV.setText("Помилка підключення");
                    } else if (code.equals("-1")) {
                        errorTV.setText("Вказано невірний номер");
                    } else {
                        errorTV.setText("Ваш код: " + code);
                        errorTV.setTextColor(ContextCompat.getColor(view.getContext(), R.color.black));
                    }
                });
            }
        }).start());

        loginBt.setOnClickListener(view -> new Thread(new Runnable() {
            boolean emptyCode = false;
            boolean result = false;

            @Override
            public void run() {
                if (codeET.getText().toString().isEmpty())
                    emptyCode = true;
                else {
                    ServerConnection connection = new ServerConnection();
                    result = connection.checkCode(userNumberMET.getUnMasked(), codeET.getText().toString());
                }

                runOnUiThread(() -> {
                    errorTV.setVisibility(View.VISIBLE);
                    errorTV.setTextColor(ContextCompat.getColor(view.getContext(), R.color.error_red));

                    if (emptyCode) errorTV.setText("Введіть код");
                    else if (!result) {
                        errorTV.setText("Вказано невірний код");
                    }
                    else {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }).start());
    }
}