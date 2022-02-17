package com.example.r2snote_v2.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.r2snote_v2.model.Note;
import com.example.r2snote_v2.model.User;

import com.example.r2snote_v2.repository.NoteRepository;

import java.util.ArrayList;

import retrofit2.Call;

public class NoteViewModel extends AndroidViewModel{

    private RefreshLiveData<Note> mNoteList;

    private NoteRepository mNoteRepository;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        init();
    }


    private void init() {
        mNoteRepository = new NoteRepository();
        mNoteList = mNoteRepository.loadAllNotes();
    }

    public void refreshData() {
        mNoteList.refresh();
    }

    public LiveData<Note> getNoteList() {
        return mNoteList;
    }


    public Call<Note> updateNote(String email, String name, String nname, String Priority, String Category, String Status, String PlanDate) {
        return mNoteRepository.editNote(email, name, nname, Priority, Category, Status, PlanDate);
    }

    public Call<Note> addNote(String email, String name, String Priority, String Category, String Status, String PlanDate) {
        return mNoteRepository.createNote(email, name, Priority, Category, Status, PlanDate);
    }

    public Call<Note> deleteNote(String email, String name) {
        return mNoteRepository.removeNote(email, name);
    }
}
