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

import com.example.mobileoperatorapp.R;
import com.example.mobileoperatorapp.ServerConnection;
import com.example.mobileoperatorapp.adapters.TariffsAdapter;
import com.example.mobileoperatorapp.models.TariffModel;
import com.example.mobileoperatorapp.utils.DpToPixels;
import com.example.mobileoperatorapp.utils.ItemSpacingDecoration;
import com.example.mobileoperatorapp.utils.MyViewModel;

import java.util.List;

public class TariffsFragment extends Fragment {
    private List<TariffModel> tariffs = null;
    private TariffsAdapter adapter;
    private MyViewModel myViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tariffs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.fragment_tariffs__tariffs_rv);

        Thread asyncThread = new Thread(() -> {
            ServerConnection connection = new ServerConnection();
            tariffs = connection.getTariffs();

            getActivity().runOnUiThread(() -> {
                adapter = new TariffsAdapter(tariffs, getContext(), myViewModel);
                recyclerView.addItemDecoration(new ItemSpacingDecoration(DpToPixels.convert(15, getContext())));
                recyclerView.setAdapter(adapter);
            });
        });
        asyncThread.start();
    }
}