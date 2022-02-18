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
import com.example.r2snote_v2.ViewModel.NoteViewModel;
import com.example.r2snote_v2.adapter.NoteAdapter;
import com.example.r2snote_v2.model.Result;
import com.example.r2snote_v2.model.NoteData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NoteFragment extends Fragment {
    private FloatingActionButton btnAdd;
    private NoteAdapter noteAdapter;
    private RecyclerView recyclerViewNote;
    private List<NoteData> noteList;
    private NoteViewModel noteViewModel;
    private CommunicateViewModel communicateViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        intUi(view);
        onEventClick();
        onSwipeItemRecyclerView();
        return view;
    }

    private void onEventClick() {
        btnAdd.setOnClickListener(view1 -> {
            showCustomDialog();
        });
    }

    private void intUi(View view) {
        recyclerViewNote = view.findViewById(R.id.recylerview_note);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewNote.setLayoutManager(linearLayoutManager);
        noteAdapter = new NoteAdapter(getContext());
        noteList = new ArrayList<>();
        btnAdd = view.findViewById(R.id.btn_add_note);
        recyclerViewNote.setAdapter(noteAdapter);
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        communicateViewModel = new ViewModelProvider(getActivity()).get(CommunicateViewModel.class);
        communicateViewModel.needReloading().observe(getViewLifecycleOwner(), needReloading -> {
            if (needReloading) {
                onResume();
            }
        });

        noteViewModel.getNoteList().observe(getViewLifecycleOwner(), note -> {
            noteList.clear();
            for (int i = 0; i < note.getData().size(); i++) {

                noteList.add(0, new NoteData(note.getData().get(i).get(0),
                        note.getData().get(i).get(1),
                        note.getData().get(i).get(2),
                        note.getData().get(i).get(3),
                        note.getData().get(i).get(4),
                        note.getData().get(i).get(5),
                        note.getData().get(i).get(6)));
            }
            noteAdapter.setData(noteList);

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
                List<NoteData> tasks = noteAdapter.getData();
                NoteData noteData = tasks.get(position);

                noteViewModel.deleteNote(noteData.getEmail(), noteData.getName()).enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.body().getStatus() == 1) {
                            Toast.makeText(getContext(), "Delete note successful!"
                                    , Toast.LENGTH_SHORT).show();
                            noteViewModel.refreshData();
                        } else {
                            if (response.body().getError() == 2) {
                                Toast.makeText(getContext(), "Unsuccessful! Using"
                                        , Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Unsuccessful!"
                                        , Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Toast.makeText(getContext(), "Failed"
                                , Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }).attachToRecyclerView(recyclerViewNote);
    }

    private void showCustomDialog() {
        DialogAddNote dialogAddNote = DialogAddNote.newInstance();
        dialogAddNote.show(getChildFragmentManager(), null);
    }

    @Override
    public void onResume() {
        super.onResume();
        noteViewModel.refreshData();
    }


}