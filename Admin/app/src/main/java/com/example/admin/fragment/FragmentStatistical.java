package com.example.admin.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.admin.R;
import com.example.admin.model.Store;
import com.example.admin.model.User;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
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
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<BarEntry> barEntries1 = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    userArrayList.add(user);
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Store");
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                                Store store = dataSnapshot1.getValue(Store.class);
                                storeArrayList.add(store);
                                barEntries.add(new BarEntry(2020,userArrayList.size()));
                                barEntries1.add(new BarEntry(2020,storeArrayList.size()));
                                BarDataSet barDataSet = new BarDataSet(barEntries,"User");
                                BarDataSet barDataSet1 = new BarDataSet(barEntries1,"Store");
                                barDataSet.setColor(Color.DKGRAY);
                                barDataSet1.setColor(R.color.xam);
                                String[] days = new String[]{"2020", "2021","2022","2023","2024","2025","2026"};
                                BarData barData = new BarData(barDataSet,barDataSet1);
                                barchart.setData(barData);
                                XAxis xAxis = barchart.getXAxis();
                                xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
                                xAxis.setCenterAxisLabels(true);
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                xAxis.setGranularity(1);
                                xAxis.setGranularityEnabled(true);
                                barchart.setDragEnabled(true);
                                barchart.setVisibleXRangeMaximum(3);
                                float barspace =0.08f;
                                float groupspace = 0.44f;
                                barData.setBarWidth(0.10f);
                                barchart.getXAxis().setAxisMinimum(0);
                                barchart.getXAxis().setAxisMaximum(0+barchart.getBarData().getGroupWidth(groupspace,barspace)*2);
                                barchart.groupBars(0,groupspace,barspace);
                                barchart.invalidate();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });




        return view;
    }
}
