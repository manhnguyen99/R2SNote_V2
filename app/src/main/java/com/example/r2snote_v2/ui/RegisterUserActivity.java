package com.example.r2snote_v2.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.r2snote_v2.R;
import com.example.r2snote_v2.Service.UserService;
import com.example.r2snote_v2.model.User;
import com.example.r2snote_v2.repository.NoteRepository;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUserActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassWord, edtFname, edtLname;
    private Button btnCancel, btnSignUp;
    private UserService userService;
    public long backPressedTime;
    public Toast backToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        initUI();
        initEvent();
    }

    private void initEvent() {
        userService = NoteRepository.getUserService();

        btnSignUp.setOnClickListener(view -> {
            signUP();
        });
        btnCancel.setOnClickListener(view -> {
            finish();
        });
    }

    private void signUP() {

        String email = edtEmail.getText().toString().trim();
        String pass = edtPassWord.getText().toString().trim();
        String fname = edtFname.getText().toString().trim();
        String lname = edtLname.getText().toString().trim();

        if (email.isEmpty()) {
            edtEmail.requestFocus();
            edtEmail.setError("Please enter email");
            return;
        }
        if (pass.isEmpty()) {
            edtPassWord.requestFocus();
            edtPassWord.setError("Please enter password");
            return;
        }
        if (fname.isEmpty()) {
            edtFname.requestFocus();
            edtFname.setError("Please enter First name");
            return;
        }
        if (lname.isEmpty()) {
            edtLname.requestFocus();
            edtLname.setError("Please enter Last name");
            return;
        }

//        User user = new User(email, pass, fname, lname);

        Call<User> call = userService.createNewUser(email, pass, fname, lname);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {

                    User user = response.body();
                    if (user.getStatus() == -1 && user.getError() == 2) {
                        Toast.makeText(RegisterUserActivity.this, "Email is used", Toast.LENGTH_SHORT).show();

                    }
                    if (user.getStatus() == 1) {
                        Toast.makeText(RegisterUserActivity.this, "Signup successfully", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPref = getSharedPreferences("USER", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("email", email);
                        editor.putString("pass", pass);
                        editor.putString("firstname", fname);
                        editor.putString("lastname", lname);
                        editor.commit();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    private void initUI() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassWord = findViewById(R.id.edtPassword);
        edtFname = findViewById(R.id.edtFirstName);
        edtLname = findViewById(R.id.edtLastName);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnCancel = findViewById(R.id.btnCancel);
    }
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press Back again to Exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }

}