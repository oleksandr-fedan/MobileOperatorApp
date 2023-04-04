package com.example.mobileoperatorapp;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

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
        TextView name = holder.itemView.findViewById(R.id.tariff_item__tariff_name);
        TextView internetQuantity = holder.itemView.findViewById(R.id.tariff_item__internet_quantity_tv);
        TextView minutesQuantity = holder.itemView.findViewById(R.id.tariff_item__minutes_quantity_tv);
        TextView otherMinutesQuantity = holder.itemView.findViewById(R.id.tariff_item__other_minutes_quantity_tv);
        TextView smsQuantity = holder.itemView.findViewById(R.id.tariff_item__sms_quantity_tv);
        TextView price = holder.itemView.findViewById(R.id.tariff_item__price_tv);
        AppCompatButton detailsButton = holder.itemView.findViewById(R.id.tariff_item__tariff_details_bt);
        ConstraintLayout cl = holder.itemView.findViewById(R.id.tariff_item__tariff_details_cl);

        name.setText(tariffs.get(position).getName());
        internetQuantity.setText(tariffs.get(position).getInternetQuantity() + " Мб");
        minutesQuantity.setText(tariffs.get(position).getMinutesQuantity() + " Хв");
        otherMinutesQuantity.setText(tariffs.get(position).getOtherMinutesQuantity() + " Хв");
        smsQuantity.setText(tariffs.get(position).getSmsQuantity() + " Шт");
        price.setText(tariffs.get(position).getPrice() + " грн/міс");

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
