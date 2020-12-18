package com.example.food.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

import com.example.food.FoodProfileActivity;
import com.example.food.R;
import com.example.food.model.Food;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class FoodProfileAdapter extends RecyclerView.Adapter<FoodProfileAdapter.MyViewHolder> {

    ArrayList<Food> categoryList;
    Context context;
    String Tag;

    public FoodProfileAdapter(ArrayList<Food> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView;
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.itemfoodprofile, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decimalFormat.applyPattern("#,###,###,###");
        Food categories = categoryList.get(position);
        holder.title.setText(categories.getNamefood());
        holder.txtdiachi.setText(categories.getDiachi());
        holder.txtgia.setText(String.valueOf(decimalFormat.format(categories.getGia())+" VNĐ"));
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


        holder.cardView1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
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
//        holder.title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, ProductActivity.class);
//                intent.putExtra("MaTheLoai",categoryList.get(position).getMatheloai());
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(intent);
//            }
//        });
//        holder.cardView1.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @SuppressLint("ResourceAsColor")
//            @Override
//            public void onClick(View v) {
//            holder.line1.setBackgroundResource( R.drawable.bg_tl);
//                holder.card_view4.setBackgroundResource( R.drawable.bg_t);
//                holder.title.setTextColor(Color.WHITE);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title,txtdiachi,txtgia;
        ProgressBar progressBar;
        CardView cardView,cardView1,card_view4;
        LinearLayout line1;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imgfood);
            title = itemView.findViewById(R.id.txtnamefood);
            progressBar = itemView.findViewById(R.id.progressbar);
            txtdiachi = itemView.findViewById(R.id.txtdiachi);
            txtgia = itemView.findViewById(R.id.txtgia);
            line1 = itemView.findViewById(R.id.line1);
            cardView1 = itemView.findViewById(R.id.cardview1);

        }
    }
}
