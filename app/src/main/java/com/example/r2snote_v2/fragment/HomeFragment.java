package com.example.r2snote_v2.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.r2snote_v2.R;
import com.example.r2snote_v2.Service.DashboardService;
import com.example.r2snote_v2.model.Dashboard;
import com.example.r2snote_v2.model.PriorityData;
import com.example.r2snote_v2.model.Result;
import com.example.r2snote_v2.repository.DashboardRepository;
import com.example.r2snote_v2.ui.MainActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private DashboardService dashboardService;
    PieChart pieChart;
    private DashboardRepository mDashboardRepository;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_home,container,false);
        dashboardService = DashboardRepository.getDashboardService();
        pieChart = view.findViewById(R.id.pieChart);

        Call<Dashboard> call  = dashboardService.getDashboard(MainActivity.EMAIL);
        call.enqueue(new Callback<Dashboard>() {
            @Override
            public void onResponse(Call<Dashboard> call, Response<Dashboard> response) {
                if (response.body() != null)
                {

                    Dashboard dashboard = response.body();


                    String doing =   dashboard.getData().get(0).get(1);

                    List<PieEntry> value = new ArrayList<>();
                    value.add(new PieEntry(Float.parseFloat(doing), "Doing"));

//                    value.add(new PieEntry(Float.parseFloat(done), "Done"));
//                    value.add(new PieEntry(Float.parseFloat(pending), "Pending"));

                    PieDataSet pieDataSet = new PieDataSet(value, "Dashboard");
                    PieData pieData = new PieData(pieDataSet);
                    pieChart.setData(pieData);
                    pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
                }

            }

            @Override
            public void onFailure(Call<Dashboard> call, Throwable t) {

            }
        });

        return view;

    }
}