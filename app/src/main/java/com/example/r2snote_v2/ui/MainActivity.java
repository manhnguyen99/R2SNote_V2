package com.example.r2snote_v2.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.example.r2snote_v2.R;
import com.example.r2snote_v2.fragment.DashboardFragment;
import com.example.r2snote_v2.fragment.PriorityFragment;
import com.example.r2snote_v2.model.User;

public class MainActivity extends AppCompatActivity {

    public static User userLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         userLogin = (User) getIntent().getExtras().get("user");

        replaceFragment(new PriorityFragment());
    }
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainlayout, fragment);
        fragmentTransaction.commit();
    }
}