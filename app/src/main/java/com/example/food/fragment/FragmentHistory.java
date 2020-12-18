package com.example.food.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import com.example.food.MainActivity;
import com.example.food.R;
import com.example.food.adapter.ChatsAdapter;
import com.example.food.adapter.Tabadapter;
import com.example.food.daofirebase.DaoStore;
import com.example.food.model.Chat;
import com.example.food.model.Store;
import com.example.food.model.Token;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

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
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_l, new FragmentProfile()).commit();
            }
        });
        return view;

    }



}
