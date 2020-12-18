package com.example.admin.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.R;
import com.example.admin.adapter.StoreAdapter;
import com.example.admin.adapter.UserAdapter;
import com.example.admin.model.Store;
import com.example.admin.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragmentmanagerstore extends Fragment {
    ArrayList<Store> arrayList;
    StoreAdapter storeAdapter;
    RecyclerView rcvuser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentmanagerstore,container,false);
        rcvuser = view.findViewById(R.id.rcvuser);
        arrayList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        rcvuser.setLayoutManager(linearLayoutManager);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Store");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Store user = dataSnapshot.getValue(Store.class);
                    arrayList.add(user);
                    storeAdapter = new StoreAdapter(arrayList,getActivity());
                    rcvuser.setAdapter(storeAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}
