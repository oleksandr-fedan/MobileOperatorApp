package com.example.mobileoperatorapp.views;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mobileoperatorapp.R;
import com.example.mobileoperatorapp.ServerConnection;
import com.example.mobileoperatorapp.models.UserModel;
import com.example.mobileoperatorapp.utils.MyViewModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProfileFragment extends Fragment {
    private MyViewModel myViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
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
        TextView internetTV = view.findViewById(R.id.fragment_profile__internet_quantity_tv);
        TextView minutesTV = view.findViewById(R.id.fragment_profile__minutes_quantity_tv);
        TextView otherMinutesTV = view.findViewById(R.id.fragment_profile__other_minutes_quantity_tv);
        TextView smsTV = view.findViewById(R.id.fragment_profile__sms_quantity_tv);
        TextView endDateTV = view.findViewById(R.id.fragment_profile__end_date_tv);
        TextView priceTV = view.findViewById(R.id.fragment_profile__price_tv);
        AppCompatButton tariffDetailsBt = view.findViewById(R.id.fragment_profile__tariff_details_bt);
        AppCompatButton tariffUpdateBt = view.findViewById(R.id.fragment_profile__update_tariff_bt);
        AppCompatButton depositBt = view.findViewById(R.id.fragment_profile__deposit_bt);
        ConstraintLayout tariffDetailsCL = view.findViewById(R.id.fragment_profile__tariff_details_cl);

        tariffDetailsBt.setOnClickListener(v -> {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) tariffDetailsBt.getLayoutParams();
            if (tariffDetailsCL.getVisibility() == View.GONE) {
                tariffDetailsCL.setVisibility(View.VISIBLE);
                tariffDetailsBt.setText("Приховати");
                layoutParams.topToBottom = R.id.fragment_profile__tariff_details_cl;
            } else {
                tariffDetailsCL.setVisibility(View.GONE);
                tariffDetailsBt.setText("Детальніше");
                layoutParams.topToBottom = R.id.fragment_profile__tariff_title_cl;
            }

            tariffDetailsBt.setLayoutParams(layoutParams);
        });

        tariffUpdateBt.setOnClickListener(v -> {
            Thread asyncThread = new Thread(() -> {
                UserModel user;
                ServerConnection connection = new ServerConnection();
                user = connection.getUser(myViewModel.getPhoneNumber());

                if (user.getBalance() < user.getTariff().getPrice()) {
                    getActivity().runOnUiThread(() -> {
                        AlertDialog.Builder errorTariffUpdateDialogBuilder = new AlertDialog.Builder(getContext());
                        errorTariffUpdateDialogBuilder.setTitle("Помилка");
                        errorTariffUpdateDialogBuilder.setMessage("Недостатньо коштів на балансі");
                        errorTariffUpdateDialogBuilder.setPositiveButton("Ок", (errorDialog, which) -> errorDialog.dismiss());

                        AlertDialog errorDialog = errorTariffUpdateDialogBuilder.create();
                        errorDialog.show();
                    });
                }
                else {
                    connection.updateTariff(myViewModel.getPhoneNumber());
                    getActivity().runOnUiThread(() -> onViewCreated(view, savedInstanceState));
                }
            });
            asyncThread.start();
        });

        depositBt.setOnClickListener(v -> {
            AlertDialog.Builder depositDialogBuilder = new AlertDialog.Builder(getContext());
            View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.deposit_dialog, null);
            depositDialogBuilder.setView(dialogView);

            EditText depositET = dialogView.findViewById(R.id.deposit_dialog__deposit_et);

            depositDialogBuilder.setPositiveButton("ОК", (depositDialog, which) -> {
                String depositText = depositET.getText().toString();

                AlertDialog.Builder depositDialogErrorBuilder = new AlertDialog.Builder(getContext());
                depositDialogErrorBuilder.setTitle("Помилка");
                String errorMsg;
                depositDialogErrorBuilder.setPositiveButton("Ок", (depositErrorDialog, which1) -> depositErrorDialog.dismiss());

                if (TextUtils.isEmpty(depositText)) {
                    errorMsg = "Введіть суму";
                    depositDialogErrorBuilder.setMessage(errorMsg);
                    AlertDialog depositErrorDialog = depositDialogErrorBuilder.create();
                    depositErrorDialog.show();
                }
                else {
                    try {
                        double deposit =  Double.parseDouble(depositET.getText().toString());

                        if (deposit < 10 || deposit > 1000000) {
                            errorMsg = "Введена сума має бути у діапазоні від 10 грн до 1000000 грн";
                            depositDialogErrorBuilder.setMessage(errorMsg);
                            AlertDialog depositErrorDialog = depositDialogErrorBuilder.create();
                            depositErrorDialog.show();
                        }
                        else {
                            Thread asyncThread = new Thread(() -> {
                                ServerConnection connection = new ServerConnection();
                                connection.deposit(myViewModel.getPhoneNumber(), deposit);

                                getActivity().runOnUiThread(() -> {
                                    depositDialog.dismiss();
                                    onViewCreated(view, savedInstanceState);
                                });
                            });
                            asyncThread.start();
                        }
                    } catch (NumberFormatException e) {
                        errorMsg = "Введена сума має некоректний формат";
                        depositDialogErrorBuilder.setMessage(errorMsg);
                        AlertDialog depositErrorDialog = depositDialogErrorBuilder.create();
                        depositErrorDialog.show();
                    }
                }
            });

            depositDialogBuilder.setNegativeButton("Відмінити", (depositDialog, which) -> {
                depositDialog.dismiss(); // Закрыть диалоговое окно
            });

            AlertDialog depositDialog = depositDialogBuilder.create();
            depositDialog.show();
        });

        String phoneNumber = myViewModel.getPhoneNumber();
        Thread thread = new Thread(() -> {
            ServerConnection connection = new ServerConnection();
            UserModel user = connection.getUser(phoneNumber);

            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    double internetQuantity = user.getTariff().getInternetQuantity();
                    if (internetQuantity == -1)
                        internetTV.setText("Безліміт");
                    else if (internetQuantity == 0)
                        internetTV.setText("Відсутній");
                    else
                        internetTV.setText(internetQuantity + " Мб");

                    double minutesQuantity = user.getTariff().getMinutesQuantity();
                    if (minutesQuantity == -1)
                        minutesTV.setText("Безліміт");
                    else if (minutesQuantity == 0)
                        minutesTV.setText("Відсутні");
                    else
                        minutesTV.setText(minutesQuantity + " Хв");

                    double otherMinutesQuantity = user.getTariff().getOtherMinutesQuantity();
                    if (otherMinutesQuantity == -1)
                        otherMinutesTV.setText("Безліміт");
                    else if (otherMinutesQuantity == 0)
                        otherMinutesTV.setText("Відсутні");
                    else
                        otherMinutesTV.setText(otherMinutesQuantity + " Хв");

                    int smsQuantity = user.getTariff().getSmsQuantity();
                    if (smsQuantity == -1)
                        smsTV.setText("Безліміт");
                    else if (smsQuantity == 0)
                        smsTV.setText("Відсутні");
                    else
                        smsTV.setText(smsQuantity + " Шт");

                    phoneNumberTV.setText("+380 " + user.getPhoneNumber());
                    fullNameTV.setText(user.getSurname() + "\n" + user.getName() + " " + user.getMiddleName());
                    balanceTV.setText(user.getBalance().toString()  + " грн");
                    tariffNameTV.setText(user.getTariff().getName());

                    LocalDateTime endDate = user.getConnectionDate().plusDays(30);
                    if (endDate.compareTo(LocalDateTime.now()) <= 0) {
                        endDateTV.setTextColor(getResources().getColor(R.color.error_red));
                        tariffUpdateBt.setVisibility(View.VISIBLE);
                    }
                    else {
                        endDateTV.setTextColor(getResources().getColor(R.color.black));
                        tariffUpdateBt.setVisibility(View.GONE);
                    }

                    endDateTV.setText(endDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                    priceTV.setText(user.getTariff().getPrice() + " грн/міс");
                });
            }
        });
        thread.start();
    }
}