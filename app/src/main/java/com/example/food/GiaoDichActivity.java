package com.example.food;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food.adapter.Tabadapter;
import com.example.food.adapter.XacnhanAdapter;
import com.example.food.fragment.FragmentGDThanhCong;
import com.example.food.fragment.FragmentGDThatBai;
import com.example.food.fragment.FragmentLogin;
import com.example.food.fragment.FragmentSignup;
import com.example.food.model.HDCT;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GiaoDichActivity extends AppCompatActivity {
    TabLayout tableLayout;
    private Tabadapter adapter;
    TextView titletoolbar;
    Toolbar toolbar;
    Intent intent;
    XacnhanAdapter xacnhanAdapter;
    ArrayList<HDCT> hdctArrayList;
    RecyclerView rcvxacnhan;
    public  static ViewPager viewPager;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giao_dich);
        titletoolbar = findViewById(R.id.toolbar_title);
        toolbar = findViewById(R.id.toolbar);
        rcvxacnhan = findViewById(R.id.rcvxacnhan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        titletoolbar.setText("Giao Dịch");
        titletoolbar.setTextSize(30);
        hdctArrayList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcvxacnhan.setLayoutManager(linearLayoutManager);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("HDCT");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    HDCT hdct = dataSnapshot.getValue(HDCT.class);
                    if(hdct.getUiduser().equalsIgnoreCase(user.getUid())){
                        hdctArrayList.add(hdct);
                        xacnhanAdapter = new XacnhanAdapter(hdctArrayList,GiaoDichActivity.this);
                        rcvxacnhan.setAdapter(xacnhanAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.cam));
    }
}