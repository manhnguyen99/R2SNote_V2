package com.example.r2snote_v2.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.r2snote_v2.model.Note;
import com.example.r2snote_v2.model.User;
import com.example.r2snote_v2.repository.NoteRepository;

import java.util.ArrayList;

import retrofit2.Call;

public class NoteViewModel extends AndroidViewModel {
    private RefreshLiveData<ArrayList<Note>> mNoteList;
    private ArrayList<User> mUserList;
    private NoteRepository mNoteRepository;
    public NoteViewModel(@NonNull  Application application) {
        super(application);
        init();
    }
    private void init() {
        mNoteRepository = new NoteRepository();
//        mNoteList = mNoteRepository.loadAllNotes();

    }
//    public void refreshData(){
//        mNoteList.refresh();
//    }
//    public LiveData<ArrayList<Note>> getNoteList() {
//        return mNoteList;
//    }
//
//
//
//    public Call<Note> getNoteById(long id) {
//        return mNoteRepository.loadNoteById(id);
//    }
//
//    public Call<Void> updateNote(long id, Note note) {
//        return mNoteRepository.editNote(id, note);
//    }
//
//    public Call<Note> addNote(Note note) {
//        return mNoteRepository.createNote(note);
//    }
//    public Call<Void> deleteNote(long id) {
//        return mNoteRepository.removeNote(id);
//    }
}
