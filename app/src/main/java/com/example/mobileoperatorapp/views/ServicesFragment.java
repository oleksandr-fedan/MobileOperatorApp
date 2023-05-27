package com.example.mobileoperatorapp.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobileoperatorapp.ServerConnection;
import com.example.mobileoperatorapp.adapters.TariffsAdapter;
import com.example.mobileoperatorapp.models.ServiceType;
import com.example.mobileoperatorapp.models.TariffModel;
import com.example.mobileoperatorapp.utils.DpToPixels;
import com.example.mobileoperatorapp.utils.ItemSpacingDecoration;
import com.example.mobileoperatorapp.R;
import com.example.mobileoperatorapp.models.ServiceModel;
import com.example.mobileoperatorapp.adapters.ServicesAdapter;
import com.example.mobileoperatorapp.utils.MyViewModel;

import java.util.ArrayList;
import java.util.List;

public class ServicesFragment extends Fragment {
    private List<ServiceModel> services = null;
    private List<ServiceModel> connectedServices = null;
    private ServicesAdapter adapter;
    private ServicesAdapter connectedServicesAdapter;
    private MyViewModel myViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_services, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.fragment_services__services_rv);
        RecyclerView connectedServicesRV = view.findViewById(R.id.fragment_services__connected_services_rv);

        Thread asyncThread = new Thread(() -> {
            ServerConnection connection = new ServerConnection();
            services = connection.getServices();
            connectedServices = connection.getUserServices(myViewModel.getPhoneNumber());

            getActivity().runOnUiThread(() -> {
                adapter = new ServicesAdapter(services, getContext(), myViewModel, ServiceType.SERVICE);
                connectedServicesAdapter = new ServicesAdapter(connectedServices, getContext(), myViewModel, ServiceType.CONNECTED_SERVICE);
                recyclerView.addItemDecoration(new ItemSpacingDecoration(DpToPixels.convert(15, getContext())));
                connectedServicesRV.addItemDecoration(new ItemSpacingDecoration(DpToPixels.convert(15, getContext())));
                recyclerView.setAdapter(adapter);
                connectedServicesRV.setAdapter(connectedServicesAdapter);
            });
        });
        asyncThread.start();
    }
}