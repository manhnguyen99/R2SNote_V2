package com.example.r2snote_v2.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.r2snote_v2.R;
import com.example.r2snote_v2.adapter.PriorityAdapter;
import com.example.r2snote_v2.model.Priority;
import com.example.r2snote_v2.model.PriorityData;
import com.example.r2snote_v2.model.User;
import com.example.r2snote_v2.repository.PriorityRepository;
import com.example.r2snote_v2.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PriorityFragment extends Fragment {

    private PriorityAdapter priorityAdapter;
    private RecyclerView recyclerViewPriority;
    private List<PriorityData> priorityDataList;


    public PriorityFragment() {
    }
    public static PriorityFragment newInstance(String param1, String param2) {
        PriorityFragment fragment = new PriorityFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_priority2, container, false);



        recyclerViewPriority = view.findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewPriority.setLayoutManager(linearLayoutManager);
        priorityAdapter = new PriorityAdapter(getContext());
        priorityDataList = new ArrayList<>();
        recyclerViewPriority.setAdapter(priorityAdapter);


        Call<Priority> call = PriorityRepository.getProrityService().getPrority("Priority", MainActivity.userLogin.getEmail());
        call.enqueue(new Callback<Priority>() {
            @Override
            public void onResponse(Call<Priority> call, Response<Priority> response) {
                if (response.body() != null)
                {
//                    for(int i= 0; i< response.body().getData().size();i++){
//                        priorityDataList.add(new PriorityData(response.body().getData().get(i).get(0),response.body().getData().get(i).get(1)));
//                    }
                    Log.d("TAG", "onResponse: " + MainActivity.userLogin.getEmail());
                    priorityAdapter.setData(priorityDataList);
                }
            }

            @Override
            public void onFailure(Call<Priority> call, Throwable t) {
            }
        });
        return view;
    }
}