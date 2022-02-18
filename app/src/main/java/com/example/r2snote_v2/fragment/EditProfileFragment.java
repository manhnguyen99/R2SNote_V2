package com.example.r2snote_v2.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.r2snote_v2.R;
import com.example.r2snote_v2.model.User;
import com.example.r2snote_v2.repository.NoteRepository;
import com.example.r2snote_v2.ui.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileFragment extends Fragment {
    private EditText edtFirstName, edtLastName, edtnEmail;
    private Button btnUpdate, btnHome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        intUi(view);
        onEventClick();

        return view;
    }

    private void onEventClick() {
        btnUpdate.setOnClickListener(view1 -> {

            String lastName = edtLastName.getText().toString().trim();
            String firstName = edtFirstName.getText().toString().trim();
            String nEmail = edtnEmail.getText().toString().trim();
            if (lastName.isEmpty() || firstName.isEmpty() || nEmail.isEmpty()) {
                Toast.makeText(getContext()
                        , "Required Full Information", Toast.LENGTH_LONG).show();
            } else {
                Call<User> userCall = NoteRepository.getUserService().editProfile(MainActivity.EMAIL, nEmail, firstName, lastName);
                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.body().getStatus() == 1) {
                            Toast.makeText(getContext()
                                    , "Change Profile Successfull", Toast.LENGTH_LONG).show();
                            closeKeyBoard();
                            MainActivity.textView.setText(nEmail);
                            SharedPreferences sharedPref = getContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("email", nEmail);
//                            editor.putString("pass", pass);
                            editor.putString("firstname", firstName);
                            editor.putString("lastname", lastName);
                            editor.commit();

                            edtLastName.setText(lastName);
                            edtFirstName.setText(firstName);
                            edtnEmail.setText(nEmail);
                        } else {
                            Toast.makeText(getContext()
                                    , "Unuccessfull", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }


        });
        btnHome.setOnClickListener(view -> {
            replaceFragment(new HomeFragment());
            MainActivity.toolbar.setTitle("Home");
        });
    }

    private void intUi(View view) {
        SharedPreferences sharedPref = getContext().getSharedPreferences("USER", Context.MODE_PRIVATE);
        MainActivity.EMAIL = sharedPref.getString("email", "");
        MainActivity.LASTNAME = sharedPref.getString("lastname", "");
        MainActivity.FIRSTNAME = sharedPref.getString("firstname", "");

        edtFirstName = view.findViewById(R.id.edit_firstname);
        edtLastName = view.findViewById(R.id.edit_lastname);
        edtnEmail = view.findViewById(R.id.edit_nemail);
        btnUpdate = view.findViewById(R.id.btn_update_profile);
        btnHome = view.findViewById(R.id.btn_edit_home);

        edtLastName.setText(MainActivity.LASTNAME);
        edtFirstName.setText(MainActivity.FIRSTNAME);
        edtnEmail.setText(MainActivity.EMAIL);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

    private void closeKeyBoard(){
        View view = getActivity().getCurrentFocus();
        if(view!=null){
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

}