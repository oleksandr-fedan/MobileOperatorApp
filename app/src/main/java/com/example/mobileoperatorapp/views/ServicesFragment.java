package com.example.mobileoperatorapp.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobileoperatorapp.utils.DpToPixels;
import com.example.mobileoperatorapp.utils.ItemSpacingDecoration;
import com.example.mobileoperatorapp.R;
import com.example.mobileoperatorapp.adapters.ServiceDetailsAdapter;
import com.example.mobileoperatorapp.models.ServiceDetailsModel;
import com.example.mobileoperatorapp.models.ServiceModel;
import com.example.mobileoperatorapp.adapters.ServicesAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ServicesFragment extends Fragment {
    private final List<ServiceModel> serviceModels = new ArrayList<>();
    private final RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = new ServicesAdapter(serviceModels);

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        serviceModels.add(new ServiceModel("desc1", createServiceDetailsAdapter(), 120));
        serviceModels.add(new ServiceModel("desc2", createServiceDetailsAdapter(), 120));
        serviceModels.add(new ServiceModel("desc3", createServiceDetailsAdapter(), 120));
        serviceModels.add(new ServiceModel("desc4", createServiceDetailsAdapter(), 120));
        serviceModels.add(new ServiceModel("desc5", createServiceDetailsAdapter(), 120));
        serviceModels.add(new ServiceModel("desc6", createServiceDetailsAdapter(), 120));
        RecyclerView recyclerView = view.findViewById(R.id.fragment_services__services_rv);

        recyclerView.addItemDecoration(new ItemSpacingDecoration(DpToPixels.convert(15, getContext())));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_services, container, false);
    }

    private ServiceDetailsAdapter createServiceDetailsAdapter() {
        List<ServiceDetailsModel> detailsModels = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < random.nextInt(3) + 1; i++) {
            detailsModels.add(new ServiceDetailsModel("service" + i, random.nextInt(300)));
        }

        ServiceDetailsAdapter adapter = new ServiceDetailsAdapter(detailsModels);
        return adapter;
    }
}