package com.example.admin.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.admin.R;
import com.example.admin.adapter.Tabadapter;
import com.google.android.material.tabs.TabLayout;

public class Fragmentmanager extends Fragment {
    private Tabadapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentmanager,container,false);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager =view.findViewById(R.id.viewPager);
        adapter = new Tabadapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new Fragmentmanageruser(), "User");
        adapter.addFragment(new Fragmentmanagerstore(), "Store");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}
