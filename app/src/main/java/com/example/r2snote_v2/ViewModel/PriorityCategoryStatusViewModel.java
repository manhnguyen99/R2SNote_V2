package com.example.r2snote_v2.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.r2snote_v2.model.Result;
import com.example.r2snote_v2.repository.PriorityRepository;

import retrofit2.Call;

public class PriorityViewModel extends AndroidViewModel {
    private String tab;
    private RefreshLiveData<Result> mPriorityList;
    private PriorityRepository mPriorityRepository;

    public PriorityViewModel(@NonNull Application application) {
        super(application);
        init(tab);
    }


    public void init(String tab) {
        mPriorityRepository = new PriorityRepository();
        mPriorityList = mPriorityRepository.loadAllPriority(tab);
    }

    public void refreshData() {
        mPriorityList.refresh();
    }

    public LiveData<Result> getPriorityList() {
        return mPriorityList;
    }


    public Call<Result> updatePriority(String tab, String email, String name, String nname) {
        return mPriorityRepository.editPriority(tab, email, name, nname);
    }

    public Call<Result> addPriority(String tab, String email, String name) {
        return mPriorityRepository.createPriority(tab, email, name);
    }

    public Call<Result> deletePriority(String tab, String email, String name) {
        return mPriorityRepository.removePriority(tab, email, name);
    }
}
