package com.example.food.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.food.FoodProfileActivity;
import com.example.food.R;
import com.example.food.model.Food;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {

    ArrayList<Food> categoryList;
    Context context;
    String Tag;

    public FavoriteAdapter(ArrayList<Food> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView;
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_category, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decimalFormat.applyPattern("#,###,###,###");
        Food categories = categoryList.get(position);
        holder.title.setText(categories.getNamefood());
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



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FoodProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("img", categories.getImage());
                intent.putExtra("gia", decimalFormat.format(categories.getGia())+"\t VNĐ");
                intent.putExtra("namefood", categories.getNamefood());
                intent.putExtra("idfood","Id: "+categories.getIdfood());
                intent.putExtra("idstore",categories.getIdstore());
                intent.putExtra("diachi",categories.getDiachi());
                intent.putExtra("sl",categories.getSoluong()+"");
                intent.putExtra("matl",categories.getMatheloai());
                intent.putExtra("status",categories.getStatus());
                intent.putExtra("mota",categories.getMota());
                intent.putExtra("idfoodcheck",categories.getIdfood()+"");
                intent.putExtra("tokenstore",categories.getTokenstore());
                context.startActivity(intent);
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
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.category_image);
            title = itemView.findViewById(R.id.category_title);
            progressBar = itemView.findViewById(R.id.progressbar);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
    public void search(ArrayList<Food> filllist){
        categoryList = filllist;
        notifyDataSetChanged();

    }
}
