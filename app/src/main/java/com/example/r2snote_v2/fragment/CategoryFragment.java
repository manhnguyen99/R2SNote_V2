package com.example.r2snote_v2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.r2snote_v2.R;
import com.example.r2snote_v2.ViewModel.CommunicateViewModel;
import com.example.r2snote_v2.ViewModel.PriorityCategoryStatusViewModel;
import com.example.r2snote_v2.adapter.PriorityCategoryStatusAdapter;
import com.example.r2snote_v2.model.PriorityCategoryStatusData;
import com.example.r2snote_v2.model.Result;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryFragment extends Fragment {
    private final String TAB = "Category";
    private PriorityCategoryStatusAdapter categoryAdapter;
    private RecyclerView recyclerViewCategory;
    private List<PriorityCategoryStatusData> categoryList;
    private PriorityCategoryStatusViewModel mViewModel;
    private FloatingActionButton btnAdd;
    private CommunicateViewModel communicateViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
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
        recyclerViewCategory = view.findViewById(R.id.recylerview_category);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewCategory.setLayoutManager(linearLayoutManager);
        categoryAdapter = new PriorityCategoryStatusAdapter(getContext());
        categoryList = new ArrayList<>();
        btnAdd = view.findViewById(R.id.btn_add_category);
        recyclerViewCategory.setAdapter(categoryAdapter);
        mViewModel = new ViewModelProvider(this).get(PriorityCategoryStatusViewModel.class);
        mViewModel.init(TAB);
        communicateViewModel = new ViewModelProvider(getActivity()).get(CommunicateViewModel.class);
        communicateViewModel.needReloading().observe(getViewLifecycleOwner(), needReloading -> {
            if (needReloading) {
                onResume();
            }
        });

        mViewModel.getDataList().observe(getViewLifecycleOwner(), result -> {
            categoryList.clear();
            for (int i = 0; i < result.getData().size(); i++) {

                categoryList.add(0, new PriorityCategoryStatusData(TAB, result.getData().get(i).get(0),
                        result.getData().get(i).get(1),
                        result.getData().get(i).get(2)));
            }
            categoryAdapter.setData(categoryList);
        });
    }

    private void onSwipeItemRecyclerView() {
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
                List<PriorityCategoryStatusData> tasks = categoryAdapter.getData();
                PriorityCategoryStatusData categoryData = tasks.get(position);

                mViewModel.deleteData(TAB, categoryData.getEmail(), categoryData.getName()).enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.body().getStatus() == 1) {
                            Toast.makeText(getContext(), "Delete status successful!"
                                    , Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Unsuccessful!Using"
                                    , Toast.LENGTH_SHORT).show();
                        }
                        mViewModel.refreshData();
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {

                    }
                });
            }

        }).attachToRecyclerView(recyclerViewCategory);
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