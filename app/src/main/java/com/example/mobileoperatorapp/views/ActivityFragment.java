package com.example.mobileoperatorapp.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobileoperatorapp.ServerConnection;
import com.example.mobileoperatorapp.models.ActivityType;
import com.example.mobileoperatorapp.models.AvailableServicesModel;
import com.example.mobileoperatorapp.models.TariffModel;
import com.example.mobileoperatorapp.utils.DpToPixels;
import com.example.mobileoperatorapp.utils.ItemSpacingDecoration;
import com.example.mobileoperatorapp.R;
import com.example.mobileoperatorapp.adapters.ActivityAdapter;
import com.example.mobileoperatorapp.models.ActivityModel;
import com.example.mobileoperatorapp.utils.MyViewModel;

import java.util.List;

public class ActivityFragment extends Fragment {
    private List<ActivityModel> internetActivities;
    private List<ActivityModel> minutesActivities;
    private List<ActivityModel> otherMinutesActivities;
    private List<ActivityModel> smsActivities;
    private AppCompatButton lastClickedBt;
    private MyViewModel myViewModel;
    private RecyclerView recyclerView;
    private AvailableServicesModel availableServices;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppCompatButton internetBt = view.findViewById(R.id.fragment_activity__internet_bt);
        AppCompatButton minutesBt = view.findViewById(R.id.fragment_activity__minutes_bt);
        AppCompatButton otherMinutesBt = view.findViewById(R.id.fragment_activity__other_minutes_bt);
        AppCompatButton smsBt = view.findViewById(R.id.fragment_activity__sms_bt);
        TextView spentInternetTV = view.findViewById(R.id.fragment_activity__internet_spent_tv);
        TextView spentMinutesTV = view.findViewById(R.id.fragment_activity__minutes_spent_tv);
        TextView spentOtherMinutesTV = view.findViewById(R.id.fragment_activity__other_minutes_spent_tv);
        TextView spentSmsTV = view.findViewById(R.id.fragment_activity__sms_spent_tv);
        TextView availableInternetTV = view.findViewById(R.id.fragment_activity__available_internet_tv);
        TextView availableMinutesTV = view.findViewById(R.id.fragment_activity__available_minutes_tv);
        TextView availableOtherMinutesTV = view.findViewById(R.id.fragment_activity__available_other_minutes_tv);
        TextView availableSmsTV = view.findViewById(R.id.fragment_activity__available_sms_tv);

        recyclerView = view.findViewById(R.id.fragment_activity__spending_details_rv);
        recyclerView.addItemDecoration(new ItemSpacingDecoration(DpToPixels.convert(15, getContext())));

        Thread asyncThread = new Thread(() -> {
            ServerConnection connection = new ServerConnection();
            internetActivities = connection.getUserActivities(myViewModel.getPhoneNumber(), ActivityType.INTERNET);
            minutesActivities = connection.getUserActivities(myViewModel.getPhoneNumber(), ActivityType.MINUTES);
            otherMinutesActivities = connection.getUserActivities(myViewModel.getPhoneNumber(), ActivityType.OTHER_MINUTES);
            smsActivities = connection.getUserActivities(myViewModel.getPhoneNumber(), ActivityType.SMS);
            availableServices = connection.getAvailableServices(myViewModel.getPhoneNumber());
        });
        asyncThread.start();
        try {
            asyncThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setSpentText(spentInternetTV, availableInternetTV, internetActivities, ActivityType.INTERNET);
        setSpentText(spentMinutesTV, availableMinutesTV, minutesActivities, ActivityType.MINUTES);
        setSpentText(spentOtherMinutesTV, availableOtherMinutesTV, otherMinutesActivities, ActivityType.OTHER_MINUTES);
        setSpentText(spentSmsTV, availableSmsTV, smsActivities, ActivityType.SMS);

        internetBt.setOnClickListener(v -> onActivityButtonClick(internetBt, internetActivities));
        internetBt.callOnClick();
        minutesBt.setOnClickListener(v -> onActivityButtonClick(minutesBt, minutesActivities));
        otherMinutesBt.setOnClickListener(v -> onActivityButtonClick(otherMinutesBt, otherMinutesActivities));
        smsBt.setOnClickListener(v -> onActivityButtonClick(smsBt, smsActivities));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activity, container, false);
    }

    private void onActivityButtonClick(AppCompatButton clickedBt, List<ActivityModel> activitiesList) {
        if (lastClickedBt != null) {
            lastClickedBt.setBackgroundResource(R.drawable.custom_green_bt);
            lastClickedBt.setEnabled(true);
        }

        clickedBt.setBackgroundResource(R.drawable.custom_green_bt_pressed);
        clickedBt.setEnabled(false);
        lastClickedBt = clickedBt;

        RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = new ActivityAdapter(activitiesList);
        recyclerView.setAdapter(adapter);
    }

    private void setSpentText (TextView spentTV, TextView availableTV, List<ActivityModel> activitiesList, ActivityType activityType) {
        String quantityMsg = null;
        String unavailableMsg = null;
        String spentMsg = null;
        double quantity = 0;
        switch (activityType) {
            case INTERNET:
                quantityMsg = " Мб";
                unavailableMsg = "Відсутній";
                spentMsg = activitiesList.stream().mapToDouble(ActivityModel::getQuantity).sum() + quantityMsg;
                quantity = availableServices.getAvailableInternet();
                break;
            case MINUTES:
                quantityMsg = " Хв";
                unavailableMsg = "Відсутні";
                spentMsg = Math.round(activitiesList.stream().mapToDouble(ActivityModel::getQuantity).sum()) + quantityMsg;
                quantity = availableServices.getAvailableMinutes();
                break;
            case OTHER_MINUTES:
                quantityMsg = " Хв";
                unavailableMsg = "Відсутні";
                spentMsg = Math.round(activitiesList.stream().mapToDouble(ActivityModel::getQuantity).sum()) + quantityMsg;
                quantity = availableServices.getAvailableOtherMinutes();
                break;
            case SMS:
                quantityMsg = " Шт";
                unavailableMsg = "Відсутні";
                spentMsg = Math.round(activitiesList.stream().mapToDouble(ActivityModel::getQuantity).sum()) + quantityMsg;
                quantity = availableServices.getAvailableSMS();
                break;
        }

        if (quantity == -1) {
            availableTV.setVisibility(View.GONE);
            spentTV.setText(spentMsg);
        }
        else if (quantity == 0) {
            availableTV.setVisibility(View.GONE);
            spentTV.setText(unavailableMsg);
        }
        else {
            availableTV.setVisibility(View.VISIBLE);
            availableTV.setText("з " + quantity + quantityMsg);
            spentTV.setText(spentMsg);
        }
    }
}