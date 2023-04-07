package com.example.mobileoperatorapp;

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

public class ServicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final List<ServiceModel> serviceModels;

    public ServicesAdapter(List<ServiceModel> serviceModels) {
        this.serviceModels = serviceModels;
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
        TextView serviceDescription = holder.itemView.findViewById(R.id.service_item__description_tv);
        TextView servicePrice = holder.itemView.findViewById(R.id.service_item__price_tv);
        RecyclerView detailsRV = holder.itemView.findViewById(R.id.service_item__services_details_rv);
        ConstraintLayout cl = holder.itemView.findViewById(R.id.service_item__services_details_cl);
        AppCompatButton detailsButton = holder.itemView.findViewById(R.id.service_item__service_details_bt);

        serviceDescription.setText(serviceModels.get(position).getDescription());
        servicePrice.setText(serviceModels.get(position).getPrice() + " грн");
        detailsRV.addItemDecoration(new ItemSpacingDecoration(DpToPixels.convert(15, holder.itemView.getContext())));
        detailsRV.setAdapter(serviceModels.get(position).getAdapter());

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
        return serviceModels.size();
    }
}
