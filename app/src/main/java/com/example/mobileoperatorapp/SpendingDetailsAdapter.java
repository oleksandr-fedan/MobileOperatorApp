package com.example.mobileoperatorapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class SpendingDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final List<SpendingDetailsModel> spendingDetails;

    public SpendingDetailsAdapter(List<SpendingDetailsModel> spendingDetails) {
        this.spendingDetails = spendingDetails;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.spending_details_item, parent, false)) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView date = holder.itemView.findViewById(R.id.fragment_activity__details_spending_item__date_tv);
        TextView quantity = holder.itemView.findViewById(R.id.fragment_activity__details_spending_item__quantity_tv);

        date.setText(spendingDetails.get(position).getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        quantity.setText(String.valueOf(spendingDetails.get(position).getQuantity()));
    }

    @Override
    public int getItemCount() {
        return spendingDetails.size();
    }
}
