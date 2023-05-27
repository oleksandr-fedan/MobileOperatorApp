package com.example.mobileoperatorapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileoperatorapp.R;
import com.example.mobileoperatorapp.models.ActivityModel;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final List<ActivityModel> spendingDetails;

    public ActivityAdapter(List<ActivityModel> spendingDetails) {
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

        DecimalFormat decimalFormat;
        switch (spendingDetails.get(position).getType()) {
            case INTERNET:
                quantity.setText(spendingDetails.get(position).getQuantity() + " Мб");
                break;
            case MINUTES:
            case OTHER_MINUTES:
                decimalFormat = new DecimalFormat("#");
                quantity.setText(decimalFormat.format(spendingDetails.get(position).getQuantity()) + " Хв");
                break;
            case SMS:
                decimalFormat = new DecimalFormat("#");
                quantity.setText(decimalFormat.format(spendingDetails.get(position).getQuantity()) + " Шт");
                break;
        }

    }

    @Override
    public int getItemCount() {
        return spendingDetails.size();
    }
}
