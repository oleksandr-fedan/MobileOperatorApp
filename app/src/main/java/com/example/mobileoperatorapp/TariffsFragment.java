package com.example.mobileoperatorapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TariffsFragment extends Fragment {
    private final List<TariffModel> tariffs = new ArrayList<>();
    private final TariffsAdapter adapter = new TariffsAdapter(tariffs);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tariffs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.fragment_tariffs__tariffs_rv);

        tariffs.add(new TariffModel("Тариф 1", 3, 500, 0, 200, 350.0));
        tariffs.add(new TariffModel("Тариф 2", 10, 1500, 100, 1000, 750.0));
        tariffs.add(new TariffModel("Тариф 3", 30, 3000, 1000, 3000, 1500.0));
        tariffs.add(new TariffModel("Тариф 4", 50, 5000, 3000, 5000, 2500.0));
        tariffs.add(new TariffModel("Тариф 5", 100, 10000, 5000, 10000, 5000.0));

        recyclerView.addItemDecoration(new ItemSpacingDecoration(DpToPixels.convert(15, getContext())));
        recyclerView.setAdapter(adapter);
    }
}