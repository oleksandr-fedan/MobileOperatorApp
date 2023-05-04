package com.example.mobileoperatorapp.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobileoperatorapp.R;
import com.example.mobileoperatorapp.ServerConnection;
import com.example.mobileoperatorapp.models.UserModel;

public class ProfileFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        TextView phoneNumberTV = view.findViewById(R.id.fragment_profile__phone_number);
        TextView fullNameTV = view.findViewById(R.id.fragment_profile__full_name);
        TextView balanceTV = view.findViewById(R.id.fragment_profile__balance_tv);
        TextView tariffNameTV = view.findViewById(R.id.fragment_profile__tariff_name);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.getString("phoneNumber") != null) {
            String phoneNumber = bundle.getString("phoneNumber");

            Thread thread = new Thread(() -> {
                ServerConnection connection = new ServerConnection();
                UserModel user = connection.getUser(phoneNumber);

                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        phoneNumberTV.setText("+380 " + user.getPhoneNumber());
                        fullNameTV.setText(user.getSurname() + "\n" + user.getName() + " " + user.getMiddleName());
                        balanceTV.setText(user.getBalance().toString());
                        tariffNameTV.setText(user.getTariff().getName());
                    });
                }
            });
            thread.start();
        }
    }
}