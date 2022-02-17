package com.example.r2snote_v2.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.r2snote_v2.R;
import com.example.r2snote_v2.ViewModel.CommunicateViewModel;
import com.example.r2snote_v2.ViewModel.NoteViewModel;
import com.example.r2snote_v2.adapter.NoteAdapter;
import com.example.r2snote_v2.model.Note;
import com.example.r2snote_v2.model.NoteData;
import com.example.r2snote_v2.repository.NoteRepository;
import com.example.r2snote_v2.ui.MainActivity;
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
//        getDataNote();
        btnAdd.setOnClickListener(view1 -> {
            showCustomDialog();
        });
        return view;
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
//                Call<Note> noteCall = NoteRepository.getNoteService().deleteNote(noteData.getEmail(),noteData.getName());
//                noteCall.enqueue(new Callback<Note>() {
//                    @Override
//                    public void onResponse(Call<Note> call, Response<Note> response) {
//                        if (response.body().getStatus()==1) {
//                            Toast.makeText(getContext(), "Delete note successful!"
//                                    ,Toast.LENGTH_LONG).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Note> call, Throwable t) {
//
//                    }
//                });
                noteViewModel.deleteNote(noteData.getEmail(), noteData.getName()).enqueue(new Callback<Note>() {
                    @Override
                    public void onResponse(Call<Note> call, Response<Note> response) {
                        if (response.body().getStatus() == 1) {
                            Toast.makeText(getContext(), "Delete note successful!"
                                    , Toast.LENGTH_LONG).show();
                            noteViewModel.refreshData();
                        } else {
                            Toast.makeText(getContext(), "Delete note Unsuccessful!"
                                    , Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Note> call, Throwable t) {

                    }
                });
            }

        }).attachToRecyclerView(recyclerViewNote);
    }

    private void showCustomDialog() {
        DialogAddNote editNameDialogFragment = DialogAddNote.newInstance();
        editNameDialogFragment.show(getChildFragmentManager(), null);
    }

    private void getDataNote() {
        Call<Note> call = NoteRepository.getNoteService().getAllNotes(MainActivity.userLogin.getEmail());
        call.enqueue(new Callback<Note>() {
            @Override
            public void onResponse(Call<Note> call, Response<Note> response) {
                if (response.body().getStatus() == 1) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        noteList.add(new NoteData(response.body().getData().get(i).get(0),
                                response.body().getData().get(i).get(1),
                                response.body().getData().get(i).get(2),
                                response.body().getData().get(i).get(3),
                                response.body().getData().get(i).get(4),
                                response.body().getData().get(i).get(5),
                                response.body().getData().get(i).get(6)));
                    }
                    noteAdapter.setData(noteList);
                }

            }

            @Override
            public void onFailure(Call<Note> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        noteViewModel.refreshData();
    }


}