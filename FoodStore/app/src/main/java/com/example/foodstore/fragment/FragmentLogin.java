package com.example.foodstore.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.foodstore.MainActivity;
import com.example.foodstore.R;
import com.example.foodstore.callback.Storecallback;
import com.example.foodstore.daofirebase.DaoStore;
import com.example.foodstore.model.Store;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class FragmentLogin extends Fragment {
    EditText email, pass;
    TextView forgot;
    Button loginbutton;
    DaoStore daoStore;
    ArrayList<Store> datastore;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentlogin, container, false);
        email = view.findViewById(R.id.edtemail);
        pass = view.findViewById(R.id.edtpass);
        forgot = view.findViewById(R.id.fogort);
        loginbutton = view.findViewById(R.id.login);
        daoStore = new DaoStore(getContext());
        datastore = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        email.setText("kingonwork@gmail.com");
        pass.setText("123456");
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


        return view;
    }

    private void login() {
        final String ussername = email.getText().toString().trim();
        final String pass1 = pass.getText().toString().trim();
         if (ussername.isEmpty() || pass1.isEmpty()) {
            Toast.makeText(getActivity(), "Vui Lòng Nhập Đầy Đủ 2 Trường", Toast.LENGTH_SHORT).show();
        }  else if (!ussername.matches("^[a-zA-Z][a-z0-9_\\.]{4,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$")) {
            Toast.makeText(getActivity(), "Email Không Hợp Lệ", Toast.LENGTH_SHORT).show();
        }else {
            daoStore = new DaoStore(getContext());
            datastore = new ArrayList<>();
            daoStore.getAll(new Storecallback() {
                @Override
                public void onSuccess(ArrayList<Store> lists) {
                    datastore.clear();
                    datastore.addAll(lists);
                }

                @Override
                public void onError(String message) {

                }
            });
            mAuth.signInWithEmailAndPassword(ussername,pass1).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        for (int i = 0; i < datastore.size(); i++) {
                            if (datastore.get(i).getEmail().matches(email.getText().toString()) && datastore.get(i).getPass().matches(pass.getText().toString())) {
                                Toast.makeText(getActivity(), "Login Thành Công", Toast.LENGTH_SHORT).show();
//                                    Bundle bundle = new Bundle();
//                                    bundle.putString("email",email.getText().toString());
//                                bundle.putString("pass",pass.getText().toString());
//                                FragmentUpdateProfile fragmentUpdateProfile = new FragmentUpdateProfile();
//                                fragmentUpdateProfile.setArguments(bundle);
//                                fragmentUpdateProfile.show(getFragmentManager(),fragmentUpdateProfile.getTag());
                                Intent is = new Intent(getActivity(),MainActivity.class);
                                is.putExtra("email",email.getText().toString());
                                startActivity(is);
                                break;
                            }
                            else {
                                Toast.makeText(getActivity(), "Login Thất Bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(getActivity(), "Login Thất Bại", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        }
    private void setupRatio(BottomSheetDialog bottomSheetDialog) {
        //id = com.google.android.material.R.id.design_bottom_sheet for Material Components
        //id = android.support.design.R.id.design_bottom_sheet for support librares
        FrameLayout bottomSheet = (FrameLayout)
                bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        layoutParams.height = getBottomSheetDialogDefaultHeight();
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
    private int getBottomSheetDialogDefaultHeight() {
        return getWindowHeight() * 85 / 100;
    }
    private int getWindowHeight() {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    }
