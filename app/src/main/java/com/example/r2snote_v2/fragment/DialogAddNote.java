package com.example.r2snote_v2.fragment;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.r2snote_v2.Service.PriorityCategoryStatusService;
import com.example.r2snote_v2.ViewModel.CommunicateViewModel;
import com.example.r2snote_v2.ViewModel.NoteViewModel;
import com.example.r2snote_v2.model.PriorityCategoryStatusData;
import com.example.r2snote_v2.model.Result;
import com.example.r2snote_v2.model.NoteData;
import com.example.r2snote_v2.repository.NoteRepository;
import com.example.r2snote_v2.ui.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

        intUi(view);
        setSpinner();
        onEventClick();
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


        getDataSpinner("Priority", MainActivity.EMAIL,sPriority);
        getDataSpinner("Category", MainActivity.EMAIL,sCategory);
        getDataSpinner("Status", MainActivity.EMAIL,sStatus);
    }

    private void intUi(View view) {

        SharedPreferences sharedPref = getContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        MainActivity.EMAIL = sharedPref.getString("email", "");

        noteName = view.findViewById(R.id.edt_note_name);
        sCategory = view.findViewById(R.id.spinner_category);
        sPriority = view.findViewById(R.id.spinner_priority);
        sStatus = view.findViewById(R.id.spinner_status);
        textDatePick = view.findViewById(R.id.text_date_picker);

        btnAdd = view.findViewById(R.id.btn_add_dialog);
        btnClose = view.findViewById(R.id.btn_close_dialog);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        communicateViewModel = new ViewModelProvider(getActivity()).get(CommunicateViewModel.class);

    }

    private void onEventClick() {
        btnAdd.setOnClickListener(view1 -> {
            SharedPreferences sharedPref = getContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
            MainActivity.EMAIL = sharedPref.getString("email", "");

            String name = noteName.getText().toString().trim();
            String priority = String.valueOf(sPriority.getSelectedItem());
            String category = String.valueOf(sCategory.getSelectedItem());
            String status = String.valueOf(sStatus.getSelectedItem());
            String planDate = textDatePick.getText().toString().trim();
            if (btnAdd.getText().toString().trim().equals("Add")) {
                if (noteName.getText().toString().trim().isEmpty() || textDatePick.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext()
                            , "Required Full Information", Toast.LENGTH_SHORT).show();
                } else {

                    Call<Result> noteCall = noteViewModel.addNote(MainActivity.EMAIL, name, priority, category, status, planDate);
                    noteCall.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            if (response.body().getStatus() == 1) {
                                Toast.makeText(getContext()
                                        , "Add Note Successfully", Toast.LENGTH_SHORT).show();
                                communicateViewModel.makeChanges();
                                dismiss();

                            } else if (response.body().getStatus() == -1) {
                                if (response.body().getError() == 2) {
                                    Toast.makeText(getContext()
                                            , "Note's Name was existed", Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(getContext()
                                            , "UnSuccessfully", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {
                            Toast.makeText(getContext()
                                    , "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            } else {
                if (noteName.getText().toString().trim().isEmpty() || textDatePick.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext()
                            , "Required Full Information", Toast.LENGTH_SHORT).show();
                } else {
                    Call<Result> noteCall = noteViewModel.updateNote(emailNote, nameNote, noteName.getText().toString().trim(), priorityNote, categoryNote, statusNote, plandateNote);
                    noteCall.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            if (response.body().getStatus() == 1) {
                                Toast.makeText(getContext(), "Update Note Successful!"
                                        , Toast.LENGTH_SHORT).show();
                                communicateViewModel.makeChanges();
                                dismiss();
                            } else {
                                Toast.makeText(getContext(), "Unsuccessful!"
                                        , Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {
                            Toast.makeText(getContext()
                                    , "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        btnClose.setOnClickListener(view1 -> {
            dismiss();
        });


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

    private void getDataSpinner(String tab, String email, Spinner spinner) {
        List<String> listData = new ArrayList<>();
        Call<Result> statusSpinner = NoteRepository.getDataService().getAllData(tab, email);
        statusSpinner.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.body().getStatus() == 1) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        listData.add(response.body().getData().get(i).get(0));
                    }
                    ArrayAdapter adapterSpiner = new ArrayAdapter<>(getContext(),
                            R.layout.support_simple_spinner_dropdown_item, listData);
                    spinner.setAdapter(adapterSpiner);
                } else {
                    Toast.makeText(getContext(), "Get Data Spinner Failed", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
