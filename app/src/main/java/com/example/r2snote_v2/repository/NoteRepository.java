package com.example.r2snote_v2.repository;

import android.util.Log;
import android.widget.Toast;

import com.example.r2snote_v2.Service.APIClient;
import com.example.r2snote_v2.Service.NoteService;
import com.example.r2snote_v2.Service.UserService;
import com.example.r2snote_v2.ViewModel.RefreshLiveData;
import com.example.r2snote_v2.model.Note;
import com.example.r2snote_v2.model.NoteData;
import com.example.r2snote_v2.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoteRepository {
    public static NoteService getNoteService() {
        NoteService noteService = APIClient.getClient().create(NoteService.class);
        return noteService;
    }

    public static UserService getUserService(){
        return APIClient.getClient().create(UserService.class);
    }


    private NoteService mNoteService;

    public NoteRepository() {
        mNoteService = APIClient.getClient().create(NoteService.class);
    }

    public RefreshLiveData<Note> loadAllNotes() {
        final RefreshLiveData<Note> liveData = new RefreshLiveData<>((callback) -> {
            mNoteService.getAllNotes(MainActivity.userLogin.getEmail()).enqueue(new Callback<Note>() {
                @Override
                public void onResponse(Call<Note> call, Response<Note> response) {
                    if (response.body().getStatus()==1) {
                        callback.onDataLoaded(response.body());

                    } else {
                        Log.e("fail", "fail");
                    }
                }

                @Override
                public void onFailure(Call<Note> call, Throwable t) {
                    Log.e("fail", "fail");
                }
            });
        });
        return liveData;
    }

    public Call<Note> editNote(String email, String name, String nname, String Priority, String Category, String Status, String PlanDate) {
        return mNoteService.updateNote(email, name, nname, Priority, Category, Status, PlanDate);
    }

    public Call<Note> createNote(String email, String name, String Priority, String Category, String Status, String PlanDate) {
        return mNoteService.postNote(email, name, Priority, Category, Status, PlanDate);
    }

    public Call<Note> removeNote(String email, String name) {
        return mNoteService.deleteNote(email, name);
    }
}
