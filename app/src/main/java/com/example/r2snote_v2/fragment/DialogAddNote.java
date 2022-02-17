package com.example.r2snote_v2.fragment;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.r2snote_v2.R;
import com.example.r2snote_v2.ViewModel.CommunicateViewModel;
import com.example.r2snote_v2.ViewModel.NoteViewModel;
import com.example.r2snote_v2.model.Note;
import com.example.r2snote_v2.model.NoteData;
import com.example.r2snote_v2.repository.NoteRepository;
import com.example.r2snote_v2.ui.MainActivity;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogAddNote extends DialogFragment {
    private Button btnAdd, btnClose;
    private Spinner sCategory, sPriority, sStatus;
    private EditText noteName;
    private DatePickerDialog datePickerDialog;
    private TextView textDatePick;
    private NoteViewModel noteViewModel;
    private NoteData noteData;
    private String nameNote;
    private String priorityNote;
    private String categoryNote;
    private String statusNote;
    private String plandateNote;
    private String emailNote;
    private CommunicateViewModel communicateViewModel;

    public static DialogAddNote newInstance() {
        return new DialogAddNote();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_dialog_note, container, false);
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        intUi(view);
        setSpinner();
        eventAddNote();
        eventDatePick();
        getNoteData();
        setCancelable(false);
        return view;
    }

    private void eventDatePick() {
        textDatePick.setOnClickListener(view1 -> {

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    calendar.set(i, i1, i2);
                    SimpleDateFormat simpleDateFormat = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        textDatePick.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }
            }, year, month, day);
            datePickerDialog.show();
        });
    }

    private void setSpinner() {
        String[] priority = {"Medium", "Slow", "High"};
        ArrayAdapter<String> adapterPriority = new ArrayAdapter<String>(getContext(),
                R.layout.support_simple_spinner_dropdown_item, priority);

        String[] category = {"Learn", "Working", "Relex", "Study"};
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(getContext(),
                R.layout.support_simple_spinner_dropdown_item, category);

        String[] status = {"Doing", "Processing", "Pending"};
        ArrayAdapter<String> adapterStatus = new ArrayAdapter<String>(getContext(),
                R.layout.support_simple_spinner_dropdown_item, status);
        sCategory.setAdapter(adapterCategory);
        sStatus.setAdapter(adapterStatus);
        sPriority.setAdapter(adapterPriority);
    }

    private void intUi(View view) {
        noteName = view.findViewById(R.id.edt_note_name);
        sCategory = view.findViewById(R.id.spinner_category);
        sPriority = view.findViewById(R.id.spinner_priority);
        sStatus = view.findViewById(R.id.spinner_status);
        textDatePick = view.findViewById(R.id.text_date_picker);

        btnAdd = view.findViewById(R.id.btn_add_dialog);
        btnClose = view.findViewById(R.id.btn_close_dialog);
        communicateViewModel = new ViewModelProvider(getActivity()).get(CommunicateViewModel.class);

    }

    private void eventAddNote() {
        btnAdd.setOnClickListener(view1 -> {
            String name = noteName.getText().toString().trim();
            String priority = String.valueOf(sPriority.getSelectedItem());
            String category = String.valueOf(sCategory.getSelectedItem());
            String status = String.valueOf(sStatus.getSelectedItem());
            String planDate = textDatePick.getText().toString().trim();
            if (btnAdd.getText().toString().trim().equals("Add")) {
                if (noteName.getText().toString().trim().isEmpty() || textDatePick.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext()
                            , "Required Full Information", Toast.LENGTH_LONG).show();
                } else {

                    Call<Note> noteCall = noteViewModel.addNote(MainActivity.userLogin.getEmail(), name, priority, category, status, planDate);
                    noteCall.enqueue(new Callback<Note>() {
                        @Override
                        public void onResponse(Call<Note> call, Response<Note> response) {
                            if (response.body().getStatus() == 1) {
                                Toast.makeText(getContext()
                                        , "Add Note Successfully", Toast.LENGTH_LONG).show();
                                communicateViewModel.makeChanges();
                                dismiss();

                            } else if (response.body().getStatus() == -1) {
                                if (response.body().getError() == 2) {
                                    Toast.makeText(getContext()
                                            , "Note's Name was existed", Toast.LENGTH_LONG).show();
                                } else
                                    Toast.makeText(getContext()
                                            , "Add Note UnSuccessfully", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Note> call, Throwable t) {

                        }
                    });
//                    Call<Note> noteCall = NoteRepository.getNoteService().postNote("kylh84@gmail.com", name, priority, category, status, planDate);
//                    noteCall.enqueue(new Callback<Note>() {
//                        @Override
//                        public void onResponse(Call<Note> call, Response<Note> response) {
//                            if (response.body().getStatus() == 1) {
//                                Toast.makeText(getContext()
//                                        , "Add Note Successfully", Toast.LENGTH_LONG).show();
//                                dismiss();
//
//                            } else if (response.body().getStatus() == -1) {
//                                if (response.body().getError() == 2) {
//                                    Toast.makeText(getContext()
//                                            , "Note's Name was existed", Toast.LENGTH_LONG).show();
//                                } else
//                                    Toast.makeText(getContext()
//                                            , "Add Note UnSuccessfully", Toast.LENGTH_LONG).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<Note> call, Throwable t) {
//                            Toast.makeText(getContext()
//                                    , "Add Note UnSuccessfully", Toast.LENGTH_LONG).show();
//                        }
//                    });
                }
            } else {
                if (noteName.getText().toString().trim().isEmpty() || textDatePick.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext()
                            , "Required Full Information", Toast.LENGTH_LONG).show();
                } else {
                    Call<Note> noteCall = noteViewModel.updateNote(emailNote, nameNote, noteName.getText().toString().trim(), priorityNote, categoryNote, statusNote, plandateNote);
                    noteCall.enqueue(new Callback<Note>() {
                        @Override
                        public void onResponse(Call<Note> call, Response<Note> response) {
                            if (response.body().getStatus() == 1) {
                                Toast.makeText(getContext(), "Update user successful!"
                                        , Toast.LENGTH_LONG).show();
//                                getActivity().startActivityForResult(getActivity().getIntent(), 10);
                                communicateViewModel.makeChanges();
                                dismiss();
                            } else {
                                Toast.makeText(getContext(), "Update user Unsuccessful!"
                                        , Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Note> call, Throwable t) {

                        }
                    });
                }
//                Call<Note> noteCall = NoteRepository.getNoteService().updateNote("kylh84@gmail.com", nameNote, "minh", priorityNote, categoryNote, statusNote, plandateNote);
//                noteCall.enqueue(new Callback<Note>() {
//                    @Override
//                    public void onResponse(Call<Note> call, Response<Note> response) {
//                        if (response.body().getStatus()==1) {
//                            Toast.makeText(getContext(), "Update user successful!"
//                                    , Toast.LENGTH_LONG).show();
//                            dismiss();
//                        } else {
//                            Toast.makeText(getContext(), "Update user Unsuccessful!"
//                                    , Toast.LENGTH_LONG).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Note> call, Throwable t) {
//
//                    }
//                });
            }
        });

        btnClose.setOnClickListener(view1 -> {
            dismiss();
        });


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void getNoteData() {
        Bundle mArgs = getArguments();

        if (mArgs != null) {
            nameNote = mArgs.getString("name");
            priorityNote = mArgs.getString("priority");
            categoryNote = mArgs.getString("category");
            statusNote = mArgs.getString("status");
            plandateNote = mArgs.getString("plandate");
            emailNote = mArgs.getString("email");
            noteName.setText(nameNote);
            textDatePick.setText(plandateNote);
            btnAdd.setText("Edit");

        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }
}
