package com.example.r2snote_v2.repository;

import android.util.Log;

import com.example.r2snote_v2.Service.APIClient;
import com.example.r2snote_v2.Service.NoteService;
import com.example.r2snote_v2.ViewModel.RefreshLiveData;
import com.example.r2snote_v2.model.Note;
import com.example.r2snote_v2.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoteRepository {

//    private NoteService mNoteService;
//
//    public NoteRepository() {
//        mNoteService = APIClient.getClient().create(NoteService.class);
//    }
//    public RefreshLiveData<ArrayList<Note>> loadAllNotes () {
//       final RefreshLiveData<ArrayList<Note>> liveData = new RefreshLiveData<>((callback) -> {
//           mNoteService.getAllNotes().enqueue(new Callback<ArrayList<Note>>() {
//               @Override
//               public void onResponse(Call<ArrayList<Note>> call, Response<ArrayList<Note>> response) {
//                   callback.onDataLoaded(response.body());
//               }
//               @Override
//               public void onFailure(Call<ArrayList<Note>> call, Throwable t) {
//                   Log.e("UserRepository", t.getMessage());
//
//               }
//           });
//       });
//       return liveData;
//    }
//
//    public Call<Note> loadNoteById ( long id){
//        Call<Note> call = mNoteService.getNoteById(id);
//        return call;
//    }
//
//    public Call<Void> editNote ( long id, Note note){
//        return mNoteService.updateNote(id, note);
//    }
//
//    public Call<Note> createNote (Note note){
//        return mNoteService.postNote(note);
//    }
//
//    public Call<Void> removeNote ( long id){
//        return mNoteService.deleteNote(id);
//    }
}
