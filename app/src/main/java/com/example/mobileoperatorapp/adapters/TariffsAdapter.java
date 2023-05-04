package com.example.mobileoperatorapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileoperatorapp.R;
import com.example.mobileoperatorapp.models.TariffModel;

import java.util.List;

public class TariffsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final List<TariffModel> tariffs;

    public TariffsAdapter(List<TariffModel> tariffs) {
        this.tariffs = tariffs;
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
        AppCompatButton detailsButton = holder.itemView.findViewById(R.id.tariff_item__tariff_details_bt);
        ConstraintLayout cl = holder.itemView.findViewById(R.id.tariff_item__tariff_details_cl);

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

        detailsButton.setOnClickListener(v -> {
            if (cl.getVisibility() == View.GONE) {
                cl.setVisibility(View.VISIBLE);
                detailsButton.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(holder.itemView.getContext(), R.drawable.ic_up_arrow), null, null, null);
            }
            else {
                cl.setVisibility(View.GONE);
                detailsButton.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(holder.itemView.getContext(), R.drawable.ic_down_arrow), null, null, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tariffs.size();
    }
}
