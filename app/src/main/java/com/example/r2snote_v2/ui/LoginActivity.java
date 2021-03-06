package com.example.r2snote_v2.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.r2snote_v2.R;
import com.example.r2snote_v2.Service.UserService;
import com.example.r2snote_v2.model.User;
import com.example.r2snote_v2.repository.NoteRepository;

import com.example.r2snote_v2.R;
import com.example.r2snote_v2.Service.UserService;
import com.example.r2snote_v2.ViewModel.NoteViewModel;
import com.example.r2snote_v2.fragment.PriorityFragment;
import com.example.r2snote_v2.model.User;
import com.example.r2snote_v2.repository.UserRepository;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;
    private TextView txtSignUp;
    private UserService userService;
    public long backPressedTime;
    public Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
        initEvent();
    }


    private void initEvent() {
        btnLogin.setOnClickListener(view -> {
            loginUser();
        });

        txtSignUp.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterUserActivity.class));
        });
    }

    private void loginUser() {
        String email = edtEmail.getText().toString().trim();
        String pass = edtPassword.getText().toString().trim();
        if (email.isEmpty()) {
            edtEmail.requestFocus();
            edtEmail.setError("Please enter email");
            return;
        }
        if (pass.isEmpty()) {
            edtPassword.requestFocus();
            edtPassword.setError("Please enter password");
            return;
        }

        Call<User> call = userService.getUserByEmail(email, pass);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    user.setEmail(email);
                    user.setPassWord(pass);
                    Log.d("LOGINNNNN", "onResponse: " + user);
                    if (user.getStatus() == -1 && user.getError() == 2) {
                        Toast.makeText(LoginActivity.this, "invalid Password", Toast.LENGTH_SHORT).show();

                    } else if (user.getStatus() == -1 && user.getError() == 1) {
                        Toast.makeText(LoginActivity.this, "invalid email or password", Toast.LENGTH_SHORT).show();

                    } else {
                        Log.d("FIRSTNAME", "onResponse: " + response.body().getInfo().getFirstName());

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                        SharedPreferences sharedPref = getSharedPreferences("USER", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("email", email);
                        editor.putString("pass", pass);
                        editor.putString("firstname", response.body().getInfo().getFirstName());
                        editor.putString("lastname", response.body().getInfo().getLastName());

                        editor.commit();

                        startActivity(intent);

                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    private void initUI() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtSignUp = findViewById(R.id.txtCreateAccount);
        userService = NoteRepository.getUserService();
    }    @Override

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