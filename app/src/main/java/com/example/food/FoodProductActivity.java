package com.example.food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.food.adapter.FoodAdapter;
import com.example.food.callback.Foodcallback;
import com.example.food.daofirebase.DaoFood;
import com.example.food.model.Food;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class FoodProductActivity extends AppCompatActivity {
    ArrayList<Food> foodArrayList;
    DaoFood daoFood;
    FoodAdapter foodAdapter;
    Toolbar toolbar;
    TextView titletoolbar;
    RecyclerView rcvfood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_product);
        toolbar = findViewById(R.id.toolbar);
        titletoolbar = findViewById(R.id.toolbar_title);
        rcvfood = findViewById(R.id.rcvfood);
        Intent getdata = getIntent();
        String matl = getdata.getStringExtra("matl");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        titletoolbar.setText("Food");
        titletoolbar.setTextSize(30);
        daoFood = new DaoFood(FoodProductActivity.this);
        foodArrayList = new ArrayList<>();
        foodAdapter = new FoodAdapter(foodArrayList,FoodProductActivity.this);
        GridLayoutManager idLayoutManager = new GridLayoutManager(FoodProductActivity.this,2);
        rcvfood.setLayoutManager(idLayoutManager);
        rcvfood.setHasFixedSize(true);
        rcvfood.setAdapter(foodAdapter);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodProductActivity.this,MainActivity.class));
                overridePendingTransition(R.anim.from_right_in, R.anim.from_left_out);
                finish();
            }
        });
        daoFood.getAll(new Foodcallback() {
            @Override
            public void onSuccess(ArrayList<Food> lists) {
                foodArrayList.clear();
                for (int i =0;i<lists.size();i++){
                    if (lists.get(i).getMatheloai().equalsIgnoreCase(matl)){
                        foodArrayList.add(lists.get(i));
                        foodAdapter.notifyDataSetChanged();
                    }
                }

            }
            @Override
            public void onError(String message) {

            }
        });
    }


}