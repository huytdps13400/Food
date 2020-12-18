package com.example.food.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.food.FoodProductActivity;
import com.example.food.R;
import com.example.food.model.Categories;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.food.R.color.black;
import static com.example.food.R.color.cam;
import static com.example.food.R.color.white;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    ArrayList<Categories> categoryList;
    Context context;
    String Tag;

    public CategoryAdapter(ArrayList<Categories> categoryList, Context context) {
        this.categoryList = categoryList;
         this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView;
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_categorieshome, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        Categories categories = categoryList.get(position);
        holder.title.setText(categories.getTentheloai());

            Picasso.get()
                    .load(categories.getImage())
                    .into(holder.imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("Error : ", e.getMessage());
                        }
                    });



//        holder.title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, ProductActivity.class);
//                intent.putExtra("MaTheLoai",categoryList.get(position).getMatheloai());
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(intent);
//            }
//        });
        if (position == 0){
            holder.line1.setBackgroundResource( R.drawable.bg_tl);
            holder.card_view4.setBackgroundResource( R.drawable.bg_t);
            holder.title.setTextColor(Color.WHITE);
        }
        holder.cardView1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
            holder.line1.setBackgroundResource( R.drawable.bg_tl);
                holder.card_view4.setBackgroundResource( R.drawable.bg_t);
                holder.title.setTextColor(Color.WHITE);
                Intent i = new Intent(context, FoodProductActivity.class);
                i.putExtra("matl",categories.getMatheloai());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        ProgressBar progressBar;
        CardView cardView,cardView1,card_view4;
        LinearLayout line1;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.category_image);
            title = itemView.findViewById(R.id.category_title);
            progressBar = itemView.findViewById(R.id.progressbar1);
            cardView = itemView.findViewById(R.id.card_view);
            cardView1 = itemView.findViewById(R.id.card_view1);
            card_view4 = itemView.findViewById(R.id.card_view4);
            line1 = itemView.findViewById(R.id.line1);

        }
    }
}
