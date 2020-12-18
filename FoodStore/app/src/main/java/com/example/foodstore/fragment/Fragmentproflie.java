package com.example.foodstore.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodstore.R;
import com.example.foodstore.callback.Storecallback;
import com.example.foodstore.daofirebase.DaoStore;
import com.example.foodstore.model.Store;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.foodstore.MainActivity.toolbar;


public class Fragmentproflie extends Fragment {
    CircleImageView profileCircleImageView;
    TextView usernameTextView, email, logout,history,txteditprofile,txtchangepassword;
    FirebaseUser firebaseUser;
    DaoStore daoStore;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentprofile,container,false);
        profileCircleImageView = view.findViewById(R.id.profileCircleImageView);
        usernameTextView = view.findViewById(R.id.usernameTextView);
        email=view.findViewById(R.id.email);
        toolbar.setVisibility(View.VISIBLE);
        logout = view.findViewById(R.id.logout);
        txtchangepassword = view.findViewById(R.id.txtchangepassword);
        txteditprofile = view.findViewById(R.id.txteditprofile);
        history = view.findViewById(R.id.history);
        daoStore = new DaoStore(getActivity());
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        daoStore.getAll(new Storecallback() {
            @Override
            public void onSuccess(ArrayList<Store> lists) {
                for (int i =0 ; i<lists.size();i++){
                    if (lists.get(i).getTokenstore().equalsIgnoreCase(firebaseUser.getUid())){
                        email.setText(lists.get(i).getEmail());
                        usernameTextView.setText(lists.get(i).getName());
                        Picasso.get()
                                .load(lists.get(i).getImage()).into(profileCircleImageView);

                    }
                }
            }

            @Override
            public void onError(String message) {

            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_l, new FragmentHistory()).commit();            }
        });
        txteditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_l, new FragmentEditProfile()).commit();            }
        });
        txtchangepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_l, new FragmentChangePassword()).commit();            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent endMain = new Intent(Intent.ACTION_MAIN);
                endMain.addCategory(Intent.CATEGORY_HOME);
                startActivity(endMain);
                getActivity().finish();
                Toast.makeText(getActivity(), "See you later", Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }
}
