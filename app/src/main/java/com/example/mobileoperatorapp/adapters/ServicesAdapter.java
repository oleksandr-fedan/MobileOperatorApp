package com.example.mobileoperatorapp.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileoperatorapp.R;
import com.example.mobileoperatorapp.ServerConnection;
import com.example.mobileoperatorapp.models.ServiceModel;
import com.example.mobileoperatorapp.models.ServiceType;
import com.example.mobileoperatorapp.models.UserModel;
import com.example.mobileoperatorapp.utils.DpToPixels;
import com.example.mobileoperatorapp.utils.MyViewModel;

import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final List<ServiceModel> services;
    private final Context context;
    private MyViewModel myViewModel;
    private ServiceType serviceType;

    public ServicesAdapter(List<ServiceModel> serviceModels, Context context, MyViewModel myViewModel, ServiceType serviceType) {
        this.services = serviceModels;
        this.context = context;
        this.myViewModel = myViewModel;
        this.serviceType = serviceType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.service_item, parent, false)) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView nameTV = holder.itemView.findViewById(R.id.service_item__service_name_tv);
        TextView descriptionTV = holder.itemView.findViewById(R.id.service_item__description_tv);
        TextView internetTV = holder.itemView.findViewById(R.id.service_item__internet_quantity_tv);
        TextView minutesTV = holder.itemView.findViewById(R.id.service_item__minutes_quantity_tv);
        TextView otherMinutesTV = holder.itemView.findViewById(R.id.service_item__other_minutes_quantity_tv);
        TextView smsTV = holder.itemView.findViewById(R.id.service_item__sms_quantity_tv);
        TextView priceTV = holder.itemView.findViewById(R.id.service_item__price_tv);
        ConstraintLayout titleCL = holder.itemView.findViewById(R.id.service_item__title_cl);
        ConstraintLayout detailsCL = holder.itemView.findViewById(R.id.service_item__services_details_cl);
        AppCompatButton detailsButton = holder.itemView.findViewById(R.id.service_item__service_details_bt);
        AppCompatButton connectBt = holder.itemView.findViewById(R.id.service_item__connect_bt);

        ConstraintLayout internetCL = holder.itemView.findViewById(R.id.service_item__internet_cl);
        ConstraintLayout minutesCL = holder.itemView.findViewById(R.id.service_item__minutes_cl);
        ConstraintLayout otherMinutesCL = holder.itemView.findViewById(R.id.service_item__other_minutes_cl);
        ConstraintLayout smsCL = holder.itemView.findViewById(R.id.service_item__sms_cl);

        nameTV.setText(services.get(position).getName());
        descriptionTV.setText(services.get(position).getDescription());
        priceTV.setText(services.get(position).getPrice() + " грн");

        double internetQuantity = services.get(position).getInternetQuantity();
        if (internetQuantity == 0)
            internetCL.setVisibility(View.GONE);
        else {
            internetCL.setVisibility(View.VISIBLE);
            internetTV.setText(internetQuantity + " Мб");
        }

        int minutesQuantity = services.get(position).getMinutesQuantity();
        if (minutesQuantity == 0)
            minutesCL.setVisibility(View.GONE);
        else {
            minutesCL.setVisibility(View.VISIBLE);
            minutesTV.setText(minutesQuantity + " Хв");
        }

        int otherMinutesQuantity = services.get(position).getOtherMinutesQuantity();
        if (otherMinutesQuantity == 0)
            otherMinutesCL.setVisibility(View.GONE);
        else {
            otherMinutesCL.setVisibility(View.VISIBLE);
            otherMinutesTV.setText(otherMinutesQuantity + " Хв");
        }

        int smsQuantity = services.get(position).getSmsQuantity();
        if (smsQuantity == 0)
            smsCL.setVisibility(View.GONE);
        else {
            smsCL.setVisibility(View.VISIBLE);
            smsTV.setText(smsQuantity + " Шт");
        }

        holder.itemView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int topPadding = titleCL.getHeight() + DpToPixels.convert(15, context);
                detailsCL.setPadding(DpToPixels.convert(15, context), topPadding, DpToPixels.convert(15, context), 0);
                holder.itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        detailsButton.setOnClickListener(v -> {
            if (detailsCL.getVisibility() == View.GONE) {
                detailsCL.setVisibility(View.VISIBLE);
                detailsButton.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(holder.itemView.getContext(), R.drawable.ic_up_arrow), null, null, null);
            }
            else {
                detailsCL.setVisibility(View.GONE);
                detailsButton.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(holder.itemView.getContext(), R.drawable.ic_down_arrow), null, null, null);
            }
        });

        if (serviceType == ServiceType.SERVICE) {
            connectBt.setOnClickListener(v -> {
                AlertDialog.Builder confirmConnectionBuilder = new AlertDialog.Builder(context);
                confirmConnectionBuilder.setTitle("Підтвердження");
                confirmConnectionBuilder.setMessage("Ви дійсно хочете підключити послугу " + services.get(position).getName() + "?\n\n" +
                        "Вартість послуги: " + services.get(position).getPrice() + " грн");

                // Добавление кнопки "Так" и установка слушателя нажатия
                confirmConnectionBuilder.setPositiveButton("Так", (dialog, which) -> {
                    Thread asyncThread = new Thread(() -> {
                        ServerConnection connection = new ServerConnection();
                        UserModel user = connection.getUser(myViewModel.getPhoneNumber());

                        if (services.get(position).getPrice() > user.getBalance()) {
                            ((Activity) context).runOnUiThread(() -> {
                                AlertDialog.Builder errorBuilder = new AlertDialog.Builder(context);
                                errorBuilder.setTitle("Помилка");
                                errorBuilder.setMessage("На балансі недостатньо коштів");
                                errorBuilder.setPositiveButton("Ок", (dialog1, which1) -> {
                                    dialog1.dismiss();
                                });
                                AlertDialog errorDialog = errorBuilder.create();
                                errorDialog.show();
                            });
                        } else {
                            connection.connectServiceToUser(myViewModel.getPhoneNumber(), services.get(position).getId());
                        }
                    });
                    asyncThread.start();
                    dialog.dismiss(); // Закрыть диалоговое окно
                });

                confirmConnectionBuilder.setNegativeButton("Відмінити", (dialog, which) -> {
                    // Действия при нажатии кнопки "Відмінити"
                    dialog.dismiss(); // Закрыть диалоговое окно
                });

                // Создание и отображение диалогового окна
                AlertDialog confirmConnectionDialog = confirmConnectionBuilder.create();
                confirmConnectionDialog.show();
            });
        }
        else {
            connectBt.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return services.size();
    }
}
