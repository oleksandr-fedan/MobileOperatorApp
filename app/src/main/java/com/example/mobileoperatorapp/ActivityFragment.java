package com.example.mobileoperatorapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ActivityFragment extends Fragment {
    private final List<SpendingDetailsModel> spendingDetails = new ArrayList<>();
    private final RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = new SpendingDetailsAdapter(spendingDetails);

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spendingDetails.add(new SpendingDetailsModel(LocalDate.of(2020, 3, 12), 355d));
        spendingDetails.add(new SpendingDetailsModel(LocalDate.of(2021, 3, 12), 355d));
        spendingDetails.add(new SpendingDetailsModel(LocalDate.of(2022, 3, 12), 355d));
        spendingDetails.add(new SpendingDetailsModel(LocalDate.of(2022, 4, 12), 355d));
        spendingDetails.add(new SpendingDetailsModel(LocalDate.of(2022, 5, 12), 355d));
        spendingDetails.add(new SpendingDetailsModel(LocalDate.of(2019, 3, 12), 355d));
        spendingDetails.add(new SpendingDetailsModel(LocalDate.of(2019, 3, 11), 355d));
        spendingDetails.add(new SpendingDetailsModel(LocalDate.of(2022, 4, 12), 355d));
        spendingDetails.add(new SpendingDetailsModel(LocalDate.of(2022, 5, 12), 355d));
        spendingDetails.add(new SpendingDetailsModel(LocalDate.of(2019, 3, 12), 355d));
        spendingDetails.add(new SpendingDetailsModel(LocalDate.of(2019, 3, 11), 355d));

        spendingDetails.sort(Comparator.comparing(SpendingDetailsModel::getDate));

        RecyclerView recyclerView = view.findViewById(R.id.fragment_activity__spending_details_rv);

        recyclerView.addItemDecoration(new ItemSpacingDecoration(DpToPixels.convert(15, getContext())));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activity, container, false);
    }
}