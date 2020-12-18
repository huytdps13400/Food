package com.example.foodstore.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodstore.R;
import com.example.foodstore.adapter.FoodAdapter;
import com.example.foodstore.callback.Foodcallback;
import com.example.foodstore.daofirebase.DaoFood;
import com.example.foodstore.daofirebase.DaoHDCT;
import com.example.foodstore.dialog.BottomSheef_Add_Food;
import com.example.foodstore.dialog.BottomSheef_Update_Food;
import com.example.foodstore.model.Food;
import com.example.foodstore.model.HDCT;
import com.example.foodstore.model.Order;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static com.example.foodstore.MainActivity.idstore;
import static com.example.foodstore.MainActivity.toolbar;

public class FragmentThongKe_Nam extends Fragment {
    BarChart thongkengay;
    ArrayList<HDCT> arrayList;
    ArrayList<HDCT> arrayListHDCT;
    FirebaseUser firebaseUser;
    DaoHDCT daoHDCT;
    String nam;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmenttk_nam, container, false);
        thongkengay = view.findViewById(R.id.thongkengay);
        arrayListHDCT = new ArrayList<>();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        daoHDCT = new DaoHDCT(getActivity());
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        nam = currentDate.substring(6, currentDate.length());
        arrayList = new ArrayList<>();
        arrayListHDCT = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("HDCT");
        arrayList = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {

            ArrayList<Order> orderArrayList1 = new ArrayList<>();
            ArrayList<Order> orderArrayListt2 = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                String uidstore = "";

                ArrayList<Order> orderArrayList = new ArrayList<>();

                orderArrayList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HDCT hdct = dataSnapshot.getValue(HDCT.class);
                    arrayList.add(hdct);
                    String thangtk = hdct.getNgay().substring(6,hdct.getNgay().length());

                    for (int i = 0; i < arrayList.size(); i++) {
                        if (arrayList.get(i).isCheck() == true) {
                            orderArrayList.addAll(arrayList.get(i).getOrderArrayList());
                            orderArrayListt2.addAll(arrayList.get(i).getOrderArrayList());
                        }
                        for (int j = 0; j < orderArrayList.size(); j++) {
                            if (orderArrayList.get(j).getStore().getTokenstore().equalsIgnoreCase(firebaseUser.getUid())) {
                                uidstore = orderArrayList.get(j).getStore().getTokenstore();

                            }
                        }

                    }
                    int tongngay=0;
                    if (nam.matches(thangtk) && hdct.isCheck() == true && uidstore.equalsIgnoreCase(firebaseUser.getUid())) {
                        orderArrayList1.addAll(hdct.getOrderArrayList());
                        for (Order order : orderArrayList1) {
                            tongngay += order.getSoluongmua() * order.getFood().getGia();

                        }

                    }

                    ArrayList<BarEntry> barEntries1 = new ArrayList<>();
                    barEntries1.add(new BarEntry(0, tongngay));
                    BarDataSet barDataSet = new BarDataSet(barEntries1, "Năm");
                    barDataSet.setColor(Color.RED);
                    BarData barData = new BarData();
                    barData.setBarWidth(0.20f);
                    barData.addDataSet(barDataSet);
                    thongkengay.setFitBars(true);
                    thongkengay.getDescription().setText("Food App");
                    thongkengay.setData(barData);
                    thongkengay.animateY(2000);
                    thongkengay.invalidate();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

}
