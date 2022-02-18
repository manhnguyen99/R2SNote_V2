package com.example.r2snote_v2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.r2snote_v2.R;
import com.example.r2snote_v2.ViewModel.CommunicateViewModel;
import com.example.r2snote_v2.ViewModel.PriorityCategoryStatusViewModel;
import com.example.r2snote_v2.adapter.PriorityCategoryStatusAdapter;
import com.example.r2snote_v2.model.NoteData;
import com.example.r2snote_v2.model.PriorityCategoryStatusData;
import com.example.r2snote_v2.model.Result;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StatusFragment extends Fragment {
    private final String TAB = "Status";
    private PriorityCategoryStatusAdapter statusAdapter;
    private RecyclerView recyclerViewStatus;
    private List<PriorityCategoryStatusData> statusList;
    private FloatingActionButton btnAdd;
    private CommunicateViewModel communicateViewModel;
    private PriorityCategoryStatusViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status, container, false);
        intUi(view);
        onEventClick();
        onSwipeItemRecyclerView();

        return view;
    }

    private void onEventClick() {
        btnAdd.setOnClickListener(view -> {
            showCustomDialog();
        });

    }

    private void intUi(View view) {
        recyclerViewStatus = view.findViewById(R.id.recylerview_status);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewStatus.setLayoutManager(linearLayoutManager);
        statusAdapter = new PriorityCategoryStatusAdapter(getContext());
        statusList = new ArrayList<>();
        btnAdd = view.findViewById(R.id.btn_add_status);
        recyclerViewStatus.setAdapter(statusAdapter);
        mViewModel = new ViewModelProvider(this).get(PriorityCategoryStatusViewModel.class);
        mViewModel.init(TAB);
        communicateViewModel = new ViewModelProvider(getActivity()).get(CommunicateViewModel.class);
        communicateViewModel.needReloading().observe(getViewLifecycleOwner(), needReloading -> {
            if (needReloading) {
                onResume();
            }
        });

        mViewModel.getDataList().observe(getViewLifecycleOwner(),result -> {
            statusList.clear();
            for (int i = 0; i < result.getData().size(); i++) {

                statusList.add(0, new PriorityCategoryStatusData(TAB,result.getData().get(i).get(0),
                        result.getData().get(i).get(1),
                        result.getData().get(i).get(2)));
            }
            statusAdapter.setData(statusList);
        });
    }

    private void onSwipeItemRecyclerView(){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder
                    , RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                List<PriorityCategoryStatusData> tasks = statusAdapter.getData();
                PriorityCategoryStatusData statusData = tasks.get(position);

                mViewModel.deleteData(TAB,statusData.getEmail(), statusData.getName()).enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.body().getStatus() == 1) {
                            Toast.makeText(getContext(), "Delete status successful!"
                                    , Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "Unsuccessful!"
                                    , Toast.LENGTH_LONG).show();
                        }
                        mViewModel.refreshData();
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {

                    }
                });
            }

        }).attachToRecyclerView(recyclerViewStatus);
    }

    private void showCustomDialog() {
        Bundle args = new Bundle();
        args.putString("tab", TAB);
        args.putString("name", "");
        args.putString("createddate", "");
        PriorityCategoryStatusDialog dialog = PriorityCategoryStatusDialog.newInstance();
        dialog.setArguments(args);
        dialog.show(getChildFragmentManager(), null);
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.refreshData();
    }
}