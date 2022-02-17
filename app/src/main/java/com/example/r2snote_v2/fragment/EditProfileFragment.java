package com.example.r2snote_v2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.r2snote_v2.R;
import com.example.r2snote_v2.model.User;
import com.example.r2snote_v2.repository.NoteRepository;
import com.example.r2snote_v2.ui.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileFragment extends Fragment {
    private EditText edtFirstName, edtLastName, edtnEmail;
    private Button btnUpdate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        edtFirstName = view.findViewById(R.id.edit_firstname);
        edtLastName = view.findViewById(R.id.edit_lastname);
        edtnEmail = view.findViewById(R.id.edit_nemail);
        btnUpdate = view.findViewById(R.id.btn_update_profile);

        btnUpdate.setOnClickListener(view1 -> {
            String email = MainActivity.userLogin.getEmail();
            String lastName = edtFirstName.getText().toString().trim();
            String firstName = edtLastName.getText().toString().trim();
            String nEmail = edtnEmail.getText().toString().trim();
            if (lastName.isEmpty() || firstName.isEmpty() || nEmail.isEmpty()) {
                Toast.makeText(getContext()
                        , "Required Full Information", Toast.LENGTH_LONG).show();
            } else {
                Call<User> userCall = NoteRepository.getUserService().editProfile(email, nEmail, firstName, lastName);
                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.body().getStatus() == 1) {
                            Toast.makeText(getContext()
                                    , "Change Password Successfull", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }


        });

        return view;
    }
}