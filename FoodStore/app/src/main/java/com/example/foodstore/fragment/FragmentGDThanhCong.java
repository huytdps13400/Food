package com.example.foodstore.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.foodstore.R;
import com.example.foodstore.adapter.XacnhanAdapter;
import com.example.foodstore.callback.HDCTcallback;
import com.example.foodstore.daofirebase.DaoHDCT;
import com.example.foodstore.model.HDCT;
import com.example.foodstore.model.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class FragmentGDThanhCong extends Fragment {
    XacnhanAdapter xacnhanAdapter;
    DaoHDCT daoHDCT;
    RecyclerView rcvthanhcong;
    ArrayList<HDCT> arrayList;
    FirebaseUser firebaseUser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragmentgdtc,container,false);
        rcvthanhcong= view.findViewById(R.id.rcvthanhcong);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        rcvthanhcong.setLayoutManager(linearLayoutManager);

        daoHDCT = new DaoHDCT(getActivity());
        arrayList=new ArrayList<>();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        daoHDCT.getAll(new HDCTcallback() {
            @Override
            public void onSuccess(ArrayList<HDCT> lists) {
                String uidstore="";
                arrayList.clear();
                ArrayList<Order> orderArrayList = new ArrayList<>();
                orderArrayList.clear();
                for (int i =0;i<lists.size();i++){
                    if ( lists.get(i).isCheck() == true) {

                        orderArrayList.addAll(lists.get(i).getOrderArrayList());


//
//
                    }
                    for (int j = 0; j < orderArrayList.size(); j++) {
                        if (orderArrayList.get(j).getStore().getTokenstore().equalsIgnoreCase(firebaseUser.getUid())) {
                            uidstore = orderArrayList.get(j).getStore().getTokenstore();



                        }
                    }

//

                }
                for (int k = 0; k < lists.size(); k++) {
                    if (uidstore.equalsIgnoreCase(firebaseUser.getUid()) && lists.get(k).isCheck() == true) {
                        arrayList.add(lists.get(k));
                        xacnhanAdapter = new XacnhanAdapter(arrayList, getActivity());
                        rcvthanhcong.setAdapter(xacnhanAdapter);
                    }
                }
            }


            @Override
            public void onError(String message) {

            }
        });

        return view;
    }
}
