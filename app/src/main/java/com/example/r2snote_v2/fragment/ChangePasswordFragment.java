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
import androidx.fragment.app.Fragment;

import com.example.r2snote_v2.R;
import com.example.r2snote_v2.model.User;
import com.example.r2snote_v2.repository.NoteRepository;
import com.example.r2snote_v2.ui.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChangePasswordFragment extends Fragment {
    private EditText currentPass, newPass, newPassAgain;
    private Button btnChange;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        currentPass = view.findViewById(R.id.change_current_pass);
        newPass = view.findViewById(R.id.change_new_pass);
        newPassAgain = view.findViewById(R.id.change_new_pass_again);
        btnChange = view.findViewById(R.id.btn_change);

        btnChange.setOnClickListener(view1 -> {
            String email = MainActivity.userLogin.getEmail();
            String pass = MainActivity.userLogin.getPassWord().toString().trim();
            String cPass = currentPass.getText().toString().trim();
            String nPass = newPass.getText().toString().trim();
            String nPassAgain = newPassAgain.getText().toString().trim();
            if (cPass.isEmpty() || nPass.isEmpty() || nPassAgain.isEmpty()) {
                Toast.makeText(getContext()
                        , "Required Full Information", Toast.LENGTH_LONG).show();
            } else {
                if (cPass.equals(pass)) {
                    if (nPass.equals(nPassAgain)) {

                        Call<User> userCall = NoteRepository.getUserService().changePassword(email,pass,nPass);
                        userCall.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.body().getStatus()==1){
                                    Toast.makeText(getContext()
                                            , "Change Password Successfull", Toast.LENGTH_LONG).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<User> call, Throwable t) {

                            }
                        });

                    }else {
                        Toast.makeText(getContext()
                                , "New Password Again Incorrect", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getContext()
                            , "Current Password Incorrect!", Toast.LENGTH_LONG).show();
                }
            }
        });


//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
//        String currentPassword = sharedPreferences.getString("currentpassword", "");


        return view;
    }
}