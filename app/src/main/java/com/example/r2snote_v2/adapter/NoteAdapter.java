package com.example.r2snote_v2.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.r2snote_v2.R;
import com.example.r2snote_v2.fragment.DialogAddNote;
import com.example.r2snote_v2.model.Note;
import com.example.r2snote_v2.model.NoteData;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<NoteData> notes;
    private Context context;

    public NoteAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<NoteData> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public List<NoteData> getData() {
        return notes;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_note_row, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        NoteData note = notes.get(position);
        if (note == null) {
            return;
        }
        holder.bind(note);

    }

    @Override
    public int getItemCount() {
        if (notes == null) {
            return 0;
        }
        return notes.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private NoteData note;
        private TextView noteName, noteCategory, noteStatus, notePriority, notePlanDate, noteCreateDate, noteEmail;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteName = itemView.findViewById(R.id.note_name);
            noteCategory = itemView.findViewById(R.id.note_category);
            notePlanDate = itemView.findViewById(R.id.note_plan_date);
            notePriority = itemView.findViewById(R.id.note_priority);
            noteStatus = itemView.findViewById(R.id.note_status);
            noteCreateDate = itemView.findViewById(R.id.note_create_date);

            itemView.setOnLongClickListener(view -> {
                new MaterialAlertDialogBuilder(view.getContext())
                        .setTitle("Edit it?")
                        .setMessage("Do you want edit Note " + note.getName())
                        .setCancelable(false)
                        .setNegativeButton("Cancel", (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                        })
                        .setPositiveButton("Edit", (dialogInterface, i) -> {

                            Bundle args = new Bundle();
                            args.putString("name", note.getName());
                            args.putString("priority", note.getPriority());
                            args.putString("category", note.getCategory());
                            args.putString("status", note.getStatus());
                            args.putString("plandate", note.getPlaneDate());
                            args.putString("email", note.getEmail());
                            DialogAddNote dialogAddNote = DialogAddNote.newInstance();
                            dialogAddNote.setArguments(args);
                            dialogAddNote.show(((AppCompatActivity) context).getSupportFragmentManager(), null);

                        })
                        .show();

                return true;
            });
        }


        private void bind(NoteData note) {
            this.note = note;
            noteName.setText(note.getName());
            noteCategory.setText(note.getCategory());
            notePriority.setText(note.getPriority());
            noteStatus.setText(note.getStatus());
            notePlanDate.setText(note.getPlaneDate());
            noteCreateDate.setText(note.getCreateDate());
//            noteEmail.setText(note.getEmail());


        }


    }

    private void showCustomDialog() {
        DialogAddNote editNameDialogFragment = DialogAddNote.newInstance();

        FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
        editNameDialogFragment.show(manager, null);
    }

}
