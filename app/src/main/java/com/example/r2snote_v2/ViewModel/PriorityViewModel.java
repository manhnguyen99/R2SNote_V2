package com.example.r2snote_v2.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.r2snote_v2.model.Priority;

import java.util.ArrayList;

public class PriorityViewModel extends AndroidViewModel {

    private RefreshLiveData<ArrayList<Priority>> mProrityList;

    public PriorityViewModel(@NonNull Application application) {
        super(application);
    }
}
