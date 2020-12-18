package com.example.foodstore;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodstore.adapter.FoodAdapter;
import com.example.foodstore.callback.Foodcallback;
import com.example.foodstore.daofirebase.DaoFood;
import com.example.foodstore.fragment.Fragmentcategory;
import com.example.foodstore.fragment.Fragmentproflie;
import com.example.foodstore.model.Food;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.example.foodstore.MainActivity.idstore;

public class FoodchitietActivity extends AppCompatActivity {
    TextView tv_detail_rating,tv_detail_release_date,tv_detail_vote_count,txtsoluong,txtdiachi,txtmota,txtstatus,txtmatl;
    Toolbar toolbar;
    ImageView iv_backdrop,iv_detail_poster;
    int vohan=0;
    DaoFood daoFood;
    ArrayList<Food> foodArrayList;
    RecyclerView rv_reviews;
    FoodAdapter foodAdapter;
    CollapsingToolbarLayout collapsingToolbarLayout;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodchitiet);
        iv_backdrop=findViewById(R.id.iv_backdrop);
        iv_detail_poster=findViewById(R.id.iv_detail_poster);
        tv_detail_rating=findViewById(R.id.tv_detail_rating);
        tv_detail_vote_count=findViewById(R.id.tv_detail_vote_count);
        tv_detail_release_date=findViewById(R.id.tv_detail_release_date);
        rv_reviews=findViewById(R.id.rv_reviews);
        txtsoluong=findViewById(R.id.txtsoluong);
        txtdiachi=findViewById(R.id.txtdiachi);
        txtmota=findViewById(R.id.txtmota);
        txtstatus=findViewById(R.id.txtstatus);
        txtmatl=findViewById(R.id.txtmatl);
        toolbar = findViewById(R.id.toolbar);
        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decimalFormat.applyPattern("#,###,###,###");
        collapsingToolbarLayout = findViewById(R.id.collapsing);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getWindow().setStatusBarColor(ContextCompat.getColor(FoodchitietActivity.this, R.color.colorPrimaryTransparent));
        Intent intent = getIntent();
        Picasso.get().load(intent.getStringExtra("img")).into(iv_backdrop);
        Picasso.get().load(intent.getStringExtra("img")).into(iv_detail_poster);
        collapsingToolbarLayout.setTitle(intent.getStringExtra("namefood"));
        tv_detail_rating.setText(intent.getStringExtra("gia"));
        tv_detail_release_date.setText(intent.getStringExtra("idfood"));
        txtdiachi.setText("Địa Chỉ:\t"+intent.getStringExtra("diachi"));
        txtsoluong.setText("Số Lượng:\t"+intent.getStringExtra("sl"));
        txtmatl.setText("Loại:\t"+intent.getStringExtra("matl"));
        txtstatus.setText("Trạng Thái:\t"+intent.getStringExtra("status"));
        txtmota.setText("Mô Tả:\t"+intent.getStringExtra("mota"));
        tv_detail_vote_count.setText("đăng bởi@"+intent.getStringExtra("idstore"));
        daoFood = new DaoFood(FoodchitietActivity.this);
        foodArrayList = new ArrayList<>();
        foodAdapter = new FoodAdapter(foodArrayList,FoodchitietActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FoodchitietActivity.this,LinearLayoutManager.HORIZONTAL,false);
        rv_reviews.setLayoutManager(linearLayoutManager);
        rv_reviews.setHasFixedSize(true);
        rv_reviews.setAdapter(foodAdapter);

        daoFood.getAll(new Foodcallback() {
            @Override
            public void onSuccess(ArrayList<Food> lists) {
                foodArrayList.clear();
                for (int i =0;i<lists.size();i++){
                    if (lists.get(i).getIdstore().equalsIgnoreCase(idstore)){
                        foodArrayList.add(lists.get(i));
                        foodAdapter.notifyDataSetChanged();
                    }
                }

            }
            @Override
            public void onError(String message) {

            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                android.app.Fragment fragmentB = new Fragment();
//                getFragmentManager().beginTransaction()
//                        .replace(R.id.container, fragmentB)
//                        .addToBackStack(MainActivity.class.getSimpleName())
//                        .commit();
//                startActivity(new Intent(FoodchitietActivity.this,MainActivity.class));
//                finish();
            }
        });
    }
    public void onDefaultToggleClick(View view) {
        Toast.makeText(this, "DefaultToggle", Toast.LENGTH_SHORT).show();
    }

    public void onCustomToggleClick(View view) {
        Toast.makeText(this, "CustomToggle", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}