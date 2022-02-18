package com.example.r2snote_v2.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.r2snote_v2.model.Result;
import com.example.r2snote_v2.repository.PriorityCategoryStatusRepository;

import retrofit2.Call;

public class PriorityCategoryStatusViewModel extends AndroidViewModel {
    private String tab;
    private RefreshLiveData<Result> mResult;
    private PriorityCategoryStatusRepository mRepository;

    public PriorityCategoryStatusViewModel(@NonNull Application application) {
        super(application);
        init(tab);
    }


    public void init(String tab) {
        mRepository = new PriorityCategoryStatusRepository();
        mResult = mRepository.loadAllData(tab);
    }

    public void refreshData() {
        mResult.refresh();
    }

    public LiveData<Result> getDataList() {
        return mResult;
    }


    public Call<Result> updateData(String tab, String email, String name, String nname) {
        return mRepository.editData(tab, email, name, nname);
    }

    public Call<Result> addData(String tab, String email, String name) {
        return mRepository.createData(tab, email, name);
    }

    public Call<Result> deleteData(String tab, String email, String name) {
        return mRepository.removeData(tab, email, name);
    }
}
