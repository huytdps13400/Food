package com.example.foodstore.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodstore.R;
import com.example.foodstore.daofirebase.DaoStore;
import com.example.foodstore.model.Store;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentSignup extends Fragment {
    EditText emailsignup, passsignup, nhaplaipass;
    Button btnsignup;
    DaoStore daoStore;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    private ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentsignup, container, false);
        emailsignup = view.findViewById(R.id.emailsignup);
        passsignup = view.findViewById(R.id.passsignup);
        nhaplaipass = view.findViewById(R.id.nhaplaipass);
        btnsignup = view.findViewById(R.id.signup);
        database =FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Store");
        mAuth = FirebaseAuth.getInstance();
        progressBar = view.findViewById(R.id.profile_progressBar);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailsignup.getText().toString().trim();
                String pass = passsignup.getText().toString().trim();
                String nhappass = nhaplaipass.getText().toString().trim();
                if(!email.matches("^[a-zA-Z][a-z0-9_\\.]{4,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$")){
                    Toast.makeText(getActivity(), "Email Không Hợp Lệ", Toast.LENGTH_SHORT).show();
                }else {
                    if (email.isEmpty() || pass.isEmpty() || nhappass.isEmpty()){
                        Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ các trường", Toast.LENGTH_SHORT).show();
                    }else if (pass.length()<6){ Toast.makeText(getActivity(), "Mật khẩu phải có ít nhất 6 ký tự!",
                            Toast.LENGTH_SHORT).show();}
                    else if (!(pass.matches(nhappass))){
                        nhaplaipass.setError("Mật Khẩu Không Trùng Khớp");
                    }else {
                        progressBar.setVisibility(View.VISIBLE);
                        //create user
                        mAuth.createUserWithEmailAndPassword(email, pass)
                                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBar.setVisibility(View.GONE);
                                        // If sign in fails, display a message to the user. If sign in succeeds
                                        // the auth state listener will be notified and logic to handle the
                                        // signed in user can be handled in the listener.
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(getActivity(), "Sign Up Thất Bại." ,
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            daoStore = new DaoStore(getActivity());
                                            Store store = new Store(email,pass,null,null,null,null,null,mAuth.getUid());
                                            daoStore.insert(store);
                                            Toast.makeText(getActivity(), "Sign Up Thành Công", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }
                }

            }
        });
        return view;
    }
}
