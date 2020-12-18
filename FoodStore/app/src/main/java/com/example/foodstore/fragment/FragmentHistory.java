package com.example.foodstore.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.foodstore.MainActivity;
import com.example.foodstore.R;
import com.example.foodstore.adapter.ChatsAdapter;
import com.example.foodstore.adapter.Tabadapter;
import com.example.foodstore.daofirebase.DaoStore;
import com.example.foodstore.model.Chat;
import com.example.foodstore.model.Store;
import com.example.foodstore.model.Token;
import com.google.android.material.tabs.TabLayout;


import java.util.ArrayList;
import java.util.List;

import static com.example.foodstore.MainActivity.toolbar;

public class FragmentHistory extends Fragment {
    private Tabadapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView back;
    TextView titletoolbar;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragmenthistory,container,false);
       getActivity(). getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.cam));
        tabLayout = view.findViewById(R.id.tabLayout);
        back =  view.findViewById(R.id.back);
        toolbar.setVisibility(View.GONE);
        titletoolbar =  view.findViewById(R.id.toolbar_title);
        viewPager =view.findViewById(R.id.viewPager);
        adapter = new Tabadapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new FragmentGDThanhCong(), "Giao Dịch Thành Công");
        adapter.addFragment(new FragmentGDThatBai(), "Giao Dịch Thất Bại");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        titletoolbar.setText("History Order");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_l, new Fragmentproflie()).commit();
                toolbar.setVisibility(View.VISIBLE);
            }
        });
        return view;

    }



}
