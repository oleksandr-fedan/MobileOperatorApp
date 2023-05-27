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
import com.example.mobileoperatorapp.models.TariffModel;
import com.example.mobileoperatorapp.models.UserModel;
import com.example.mobileoperatorapp.utils.DpToPixels;
import com.example.mobileoperatorapp.utils.MyViewModel;

import java.util.List;

public class TariffsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final List<TariffModel> tariffs;
    private Context context;
    private MyViewModel myViewModel;

    public TariffsAdapter(List<TariffModel> tariffs, Context context, MyViewModel myViewModel) {
        this.tariffs = tariffs;
        this.context = context;
        this.myViewModel = myViewModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.tariff_item, parent, false)) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView nameTV = holder.itemView.findViewById(R.id.tariff_item__tariff_name);
        TextView internetQuantityTV = holder.itemView.findViewById(R.id.tariff_item__internet_quantity_tv);
        TextView minutesQuantityTV = holder.itemView.findViewById(R.id.tariff_item__minutes_quantity_tv);
        TextView otherMinutesQuantityTV = holder.itemView.findViewById(R.id.tariff_item__other_minutes_quantity_tv);
        TextView smsQuantityTV = holder.itemView.findViewById(R.id.tariff_item__sms_quantity_tv);
        TextView priceTV = holder.itemView.findViewById(R.id.tariff_item__price_tv);
        AppCompatButton detailsBt = holder.itemView.findViewById(R.id.tariff_item__tariff_details_bt);
        AppCompatButton connectBt = holder.itemView.findViewById(R.id.tariff_item__connect_bt);
        ConstraintLayout detailsCL = holder.itemView.findViewById(R.id.tariff_item__tariff_details_cl);
        ConstraintLayout titleCL = holder.itemView.findViewById(R.id.tariff_item__title_cl);

        nameTV.setText(tariffs.get(position).getName());

        double internetQuantity = tariffs.get(position).getInternetQuantity();
        if (internetQuantity == -1)
            internetQuantityTV.setText("Безліміт");
        else if (internetQuantity == 0)
            internetQuantityTV.setText("Відсутній");
        else
            internetQuantityTV.setText(internetQuantity + " Мб");

        double minutesQuantity = tariffs.get(position).getMinutesQuantity();
        if (minutesQuantity == -1)
            minutesQuantityTV.setText("Безліміт");
        else if (minutesQuantity == 0)
            minutesQuantityTV.setText("Відсутні");
        else
            minutesQuantityTV.setText(minutesQuantity + " Хв");

        double otherMinutesQuantity = tariffs.get(position).getOtherMinutesQuantity();
        if (otherMinutesQuantity == -1)
            otherMinutesQuantityTV.setText("Безліміт");
        else if (otherMinutesQuantity == 0)
            otherMinutesQuantityTV.setText("Відсутні");
        else
            otherMinutesQuantityTV.setText(otherMinutesQuantity + " Хв");

        int smsQuantity = tariffs.get(position).getSmsQuantity();
        if (smsQuantity == -1)
            smsQuantityTV.setText("Безліміт");
        else if (smsQuantity == 0)
            smsQuantityTV.setText("Відсутні");
        else
            smsQuantityTV.setText(smsQuantity + " Шт");

        priceTV.setText(tariffs.get(position).getPrice() + " грн/міс");

        holder.itemView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int topPadding = titleCL.getHeight() + DpToPixels.convert(15, context);
                detailsCL.setPadding(DpToPixels.convert(15, context), topPadding, DpToPixels.convert(15, context), 0);
                holder.itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        detailsBt.setOnClickListener(v -> {
            if (detailsCL.getVisibility() == View.GONE) {
                detailsCL.setVisibility(View.VISIBLE);
                detailsBt.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(holder.itemView.getContext(), R.drawable.ic_up_arrow), null, null, null);
            }
            else {
                detailsCL.setVisibility(View.GONE);
                detailsBt.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(holder.itemView.getContext(), R.drawable.ic_down_arrow), null, null, null);
            }
        });

        connectBt.setOnClickListener(v -> {
            AlertDialog.Builder confirmConnectionBuilder = new AlertDialog.Builder(context);
            confirmConnectionBuilder.setTitle("Підтвердження");
            confirmConnectionBuilder.setMessage("Ви дійсно хочете підключити тариф " + tariffs.get(position).getName() + "?\n\n" +
                    "Вартість тарифу: " + tariffs.get(position).getPrice() + " грн");

            // Добавление кнопки "Так" и установка слушателя нажатия
            confirmConnectionBuilder.setPositiveButton("Так", (dialog, which) -> {
                Thread asyncThread = new Thread(() -> {
                    ServerConnection connection = new ServerConnection();
                    UserModel user = connection.getUser(myViewModel.getPhoneNumber());

                    if (tariffs.get(position).getPrice() > user.getBalance()) {
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
                        connection.connectTariffToUser(myViewModel.getPhoneNumber(), tariffs.get(position).getId());
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

    @Override
    public int getItemCount() {
        return tariffs.size();
    }
}
