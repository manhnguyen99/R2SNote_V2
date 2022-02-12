package com.example.r2snote_v2.Service;

import com.example.r2snote_v2.model.Note;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NoteService {
    @GET("api/v1/Note")
    Call<ArrayList<Note>> getAllNotes();

    @GET("api/v1/Note/{id}")
    Call<Note> getNoteById(@Path("id") long id);

    @POST("api/v1/Note")
    Call<Note> postNote(@Body Note note);

    @PUT("api/v1/Note/{id}")
    Call<Void> updateNote(@Path("id") long id, @Body Note note);

    @DELETE("api/v1/Note/{id}")
    Call<Void> deleteNote(@Path("id") long id);
}
