package com.example.r2snote_v2.repository;

import android.util.Log;

import com.example.r2snote_v2.Service.APIClient;
import com.example.r2snote_v2.Service.NoteService;
import com.example.r2snote_v2.Service.PriorityCategoryStatusService;
import com.example.r2snote_v2.Service.UserService;
import com.example.r2snote_v2.ViewModel.RefreshLiveData;
import com.example.r2snote_v2.model.Result;
import com.example.r2snote_v2.ui.MainActivity;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoteRepository {

    public static UserService getUserService(){
        return APIClient.getClient().create(UserService.class);
    }

    public static PriorityCategoryStatusService getDataService(){
        return  APIClient.getClient().create(PriorityCategoryStatusService.class);
    }


    private NoteService mNoteService;

    public NoteRepository() {
        mNoteService = APIClient.getClient().create(NoteService.class);
    }

    public RefreshLiveData<Result> loadAllNotes() {
        final RefreshLiveData<Result> liveData = new RefreshLiveData<>((callback) -> {
            mNoteService.getAllNotes(MainActivity.EMAIL).enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    if (response.body().getStatus()==1) {
                        callback.onDataLoaded(response.body());

                    } else {
                        Log.e("fail", "fail");
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Log.e("fail", "fail");
                }
            });
        });
        return liveData;
    }

    public Call<Result> editNote(String email, String name, String nname, String Priority, String Category, String Status, String PlanDate) {
        return mNoteService.updateNote(email, name, nname, Priority, Category, Status, PlanDate);
    }

    public Call<Result> createNote(String email, String name, String Priority, String Category, String Status, String PlanDate) {
        return mNoteService.postNote(email, name, Priority, Category, Status, PlanDate);
    }

    public Call<Result> removeNote(String email, String name) {
        return mNoteService.deleteNote(email, name);
    }

}
