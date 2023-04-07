package com.example.mobileoperatorapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ServiceDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final List<ServiceDetailsModel> serviceDetails;

    public ServiceDetailsAdapter(List<ServiceDetailsModel> serviceDetails) {
        this.serviceDetails = serviceDetails;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.service_details_item, parent, false)) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView detailName = holder.itemView.findViewById(R.id.service_details_item__detail_name_tv);
        TextView detailQuantity = holder.itemView.findViewById(R.id.service_details_item__detail_quantity_tv);

        detailName.setText(serviceDetails.get(position).getDetailName());
        detailQuantity.setText(String.valueOf(serviceDetails.get(position).getDetailQuantity()));
    }

    @Override
    public int getItemCount() {
        return serviceDetails.size();
    }
}
