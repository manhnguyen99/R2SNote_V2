package com.example.r2snote_v2.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.r2snote_v2.R;
import com.example.r2snote_v2.ViewModel.CommunicateViewModel;
import com.example.r2snote_v2.ViewModel.NoteViewModel;
import com.example.r2snote_v2.ViewModel.PriorityCategoryStatusViewModel;
import com.example.r2snote_v2.model.Result;
import com.example.r2snote_v2.ui.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PriorityCategoryStatusDialog extends DialogFragment {
    private Button btnAdd, btnClose;
    private EditText edtName;
    private CommunicateViewModel communicateViewModel;
    private PriorityCategoryStatusViewModel mViewModel;
    private String tab, name, createdDate;


    public static PriorityCategoryStatusDialog newInstance() {
        return new PriorityCategoryStatusDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.priority_category_status_dialog, container, false);
        intUi(view);
        getData();
        onEventClick();
        setCancelable(false);
        return view;
    }

    private void onEventClick() {
        btnClose.setOnClickListener(view -> {
            dismiss();
        });
        btnAdd.setOnClickListener(view -> {
            String dataName = edtName.getText().toString().trim();

            if (dataName.isEmpty()) {
                Toast.makeText(getContext()
                        , "Required Full Information", Toast.LENGTH_LONG).show();
            } else {
                Call<Result> resultCall = mViewModel.addData(tab, MainActivity.EMAIL, dataName);
                resultCall.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.body().getStatus() == 1) {
                            Toast.makeText(getContext()
                                    , "Add " + tab + " Successfully", Toast.LENGTH_LONG).show();
                            communicateViewModel.makeChanges();
                            dismiss();
                        } else {
                            Toast.makeText(getContext()
                                    , "Unsuccessful", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Toast.makeText(getContext()
                                , "Failed", Toast.LENGTH_LONG).show();
                    }
                });
            }

        });
    }

    private void intUi(View view) {
        SharedPreferences sharedPref = getContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        MainActivity.EMAIL = sharedPref.getString("email", "");

        edtName = view.findViewById(R.id.edt_pcs_name);
        btnAdd = view.findViewById(R.id.btn_add_pcs);
        btnClose = view.findViewById(R.id.btn_close_pcs);

        mViewModel = new ViewModelProvider(this).get(PriorityCategoryStatusViewModel.class);
        communicateViewModel = new ViewModelProvider(getActivity()).get(CommunicateViewModel.class);
    }

    private void getData() {
        Bundle mArgs = getArguments();
        if (mArgs != null) {
            tab = mArgs.getString("tab");
            name = mArgs.getString("name");
            createdDate = mArgs.getString("createddate");
            if (!name.isEmpty()){
                edtName.setText(name);
            }
        }
    }

}
