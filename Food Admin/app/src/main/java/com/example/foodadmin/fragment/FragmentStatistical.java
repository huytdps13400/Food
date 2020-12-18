package com.example.foodadmin.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodadmin.R;
import com.example.foodadmin.model.Store;
import com.example.foodadmin.model.User;
import com.github.mikephil.charting.charts.BarChart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class FragmentStatistical extends Fragment {

    ArrayList<User> userArrayList;
    ArrayList<Store> storeArrayList;
    BarChart barchart;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentstatistical,container,false);
      
        userArrayList = new ArrayList<>();
        storeArrayList = new ArrayList<>();
        barchart = view.findViewById(R.id.barchart);
        Toast.makeText(getActivity(), "show"+userArrayList.size(), Toast.LENGTH_SHORT).show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    userArrayList.add(user);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });




        return view;
    }
}
