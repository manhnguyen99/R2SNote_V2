package com.example.r2snote_v2.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.r2snote_v2.R;
import com.example.r2snote_v2.fragment.CategoryFragment;
import com.example.r2snote_v2.fragment.ChangePasswordFragment;
import com.example.r2snote_v2.fragment.DialogAddNote;
import com.example.r2snote_v2.fragment.EditProfileFragment;
import com.example.r2snote_v2.fragment.HomeFragment;
import com.example.r2snote_v2.fragment.NoteFragment;
import com.example.r2snote_v2.fragment.PriorityFragment;
import com.example.r2snote_v2.fragment.StatusFragment;
import com.example.r2snote_v2.model.User;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    public static Toolbar toolbar;
    private NavigationView navigationView;
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_CATEGORY = 1;
    private static final int FRAGMENT_PRIORITY = 2;
    private static final int FRAGMENT_STATUS = 3;
    private static final int FRAGMENT_NOTE = 4;
    private int currentFragment = FRAGMENT_HOME;
    public static String EMAIL, PASS, FIRSTNAME, LASTNAME;
    public static TextView textView;
    public long backPressedTime;
    public Toast backToast;

    public static User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = getSharedPreferences("USER", Context.MODE_PRIVATE);
        LASTNAME = sharedPref.getString("lastname", "");
        FIRSTNAME = sharedPref.getString("firstname", "");
        EMAIL = sharedPref.getString("email", "");
        PASS = sharedPref.getString("pass", "");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intUi();

    }

    private void intUi() {



        drawerLayout = findViewById(R.id.drawer_layout);

        //navigation drawer
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        //toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        View headerView = navigationView.getHeaderView(0);
        textView = headerView.findViewById(R.id.email_header);
        textView.setText(EMAIL);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                if (currentFragment != FRAGMENT_HOME || currentFragment == FRAGMENT_HOME) {
                    replaceFragment(new HomeFragment());
                    currentFragment = FRAGMENT_HOME;
                    toolbar.setTitle("Home");
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                break;
            case R.id.nav_category:
                if (currentFragment != FRAGMENT_CATEGORY || currentFragment == FRAGMENT_CATEGORY) {
                    replaceFragment(new CategoryFragment());
                    currentFragment = FRAGMENT_CATEGORY;
                    toolbar.setTitle("Category");
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                break;
            case R.id.nav_priority:
                if (currentFragment != FRAGMENT_PRIORITY || currentFragment == FRAGMENT_PRIORITY) {
                    replaceFragment(new PriorityFragment());
                    currentFragment = FRAGMENT_PRIORITY;
                    toolbar.setTitle("Priority");
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                break;
            case R.id.nav_status:
                if (currentFragment != FRAGMENT_STATUS || currentFragment == FRAGMENT_STATUS) {
                    replaceFragment(new StatusFragment());
                    currentFragment = FRAGMENT_STATUS;
                    toolbar.setTitle("Status");
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                break;
            case R.id.nav_note:
                if (currentFragment != FRAGMENT_NOTE || currentFragment == FRAGMENT_NOTE) {
                    replaceFragment(new NoteFragment());
                    currentFragment = FRAGMENT_NOTE;
                    toolbar.setTitle("Note");
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                break;
            case R.id.nav_edit_profile:
                replaceFragment(new EditProfileFragment());
                toolbar.setTitle("Edit Profile");
                drawerLayout.closeDrawer(GravityCompat.START);

                break;
            case R.id.nav_change_password:
                toolbar.setTitle("Change Password");
                replaceFragment(new ChangePasswordFragment());
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_logout:
                new MaterialAlertDialogBuilder(MainActivity.this)
                        .setTitle("Log Out?")
                        .setMessage("Are you sure?")
                        .setCancelable(false)
                        .setNegativeButton("No", (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                        })
                        .setPositiveButton("Yes", (dialogInterface, i) -> {
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        })
                        .show();
                break;
        }
        return super.onOptionsItemSelected(item);
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