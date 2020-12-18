package com.example.food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.food.adapter.FavoriteAdapter;
import com.example.food.callback.Foodcallback;
import com.example.food.daofirebase.DaoFood;
import com.example.food.model.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    EditText TF_location;
    Button B_search;
    ArrayList<Food> arrayListfood= new ArrayList<>();
    DaoFood daoFood;
    RecyclerView rcvsearch;
    FavoriteAdapter favoriteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        TF_location = findViewById(R.id.TF_location);
        B_search = findViewById(R.id.B_search);
        rcvsearch = findViewById(R.id.rcvsearch);
        arrayListfood = new ArrayList<>();

        daoFood =new DaoFood(SearchActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false);
        rcvsearch.setHasFixedSize(true);
        rcvsearch.setLayoutManager(layoutManager);

//        daoFood.getAll(new Foodcallback() {
//            @Override
//            public void onSuccess(ArrayList<Food> lists) {
//                arrayListfood.addAll(lists);
//                favoriteAdapter = new FavoriteAdapter(arrayListfood,SearchActivity.this);
//                rcvsearch.setAdapter(favoriteAdapter);
//            }
//
//            @Override
//            public void onError(String message) {
//
//            }
//        });
        if (TF_location.getText().toString().equals("")){
            arrayListfood.clear();
            favoriteAdapter = new FavoriteAdapter(arrayListfood,SearchActivity.this);
            rcvsearch.setAdapter(favoriteAdapter);
        }
        B_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daoFood.getAll(new Foodcallback() {
                    @Override
                    public void onSuccess(ArrayList<Food> lists) {


                            arrayListfood.clear();
                            for (int i =0;i<lists.size();i++){
                                if (lists.get(i).getNamefood().toLowerCase().contains(TF_location.getText().toString())){
                                    arrayListfood.add(lists.get(i));
                                    favoriteAdapter = new FavoriteAdapter(arrayListfood,SearchActivity.this);
                                    rcvsearch.setAdapter(favoriteAdapter);
                        }

                            }

                    }

                    @Override
                    public void onError(String message) {

                    }
                });
            }
        });



    }
}