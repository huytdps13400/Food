package com.example.foodstore.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.foodstore.R;
import com.example.foodstore.model.Order;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class CartHDCTAdapter extends RecyclerView.Adapter<CartHDCTAdapter.MyViewHolder> {

    ArrayList<Order> cartList;
    Context context;

    String _subtotal, _price, _quantity;

    Gson gson;

    public CartHDCTAdapter(ArrayList<Order> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_carthdct, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decimalFormat.applyPattern("#,###,###,###");
        final Order cart = cartList.get(position);

        gson = new Gson();
        holder.title.setText(cart.getFood().getNamefood());
        holder.attribute.setText(cart.getFood().getMatheloai());

        holder.quantity.setText(String.valueOf(decimalFormat.format(cart.getSoluongmua())));
        holder.price.setText(String.valueOf(decimalFormat.format(cart.getFood().getGia())));

        _subtotal = String.valueOf(decimalFormat.format(cart.getSoluongmua() * cart.getFood().getGia()));

        holder.subTotal.setText(_subtotal);
        Picasso.get()
                .load(cart.getFood().getImage())
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

//        holder.plus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                pQuantity = Integer.parseInt(holder.quantity.getText().toString());
//                if (pQuantity >= 1) {
//                    int total_item = Integer.parseInt(holder.quantity.getText().toString());
//                    total_item++;
//                    holder.quantity.setText(total_item + "");
//                    for (int i = 0; i < cartList.size(); i++) {
//
//                        if (cartList.get(i).getId().equalsIgnoreCase(cart.getId())) {
//
//                            // Log.d("totalItem", total_item + "");
//
//                            _subtotal = String.valueOf(Double.parseDouble(holder.price.getText().toString()) * total_item);
//                            cartList.get(i).setQuantity(holder.quantity.getText().toString());
//                            cartList.get(i).setSubTotal(_subtotal);
//                            holder.subTotal.setText(_subtotal);
//                            String cartStr = gson.toJson(cartList);
//                            //Log.d("CART", cartStr);
//                            localStorage.setCart(cartStr);
//                            ((CartActivity) context).updateTotalPrice();
//                        }
//                    }
//                }
//
//
//            }
//        });
//        holder.minus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                pQuantity = Integer.parseInt(holder.quantity.getText().toString());
//                if (pQuantity != 1) {
//                    int total_item = Integer.parseInt(holder.quantity.getText().toString());
//                    total_item--;
//                    holder.quantity.setText(total_item + "");
//                    for (int i = 0; i < cartList.size(); i++) {
//                        if (cartList.get(i).getId().equalsIgnoreCase(cart.getId())) {
//
//                            //holder.quantity.setText(total_item + "");
//                            //Log.d("totalItem", total_item + "");
//                            _subtotal = String.valueOf(Double.parseDouble(holder.price.getText().toString()) * total_item);
//                            cartList.get(i).setQuantity(holder.quantity.getText().toString());
//                            cartList.get(i).setSubTotal(_subtotal);
//                            holder.subTotal.setText(_subtotal);
//                            String cartStr = gson.toJson(cartList);
//                            //Log.d("CART", cartStr);
//                            localStorage.setCart(cartStr);
//                            ((CartActivity) context).updateTotalPrice();
//
//                        }
//                    }
//
//                }
//            }
//        });



    }

    @Override
    public int getItemCount() {

        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        ProgressBar progressBar;
        CardView cardView;
        TextView offer, currency, price, quantity, attribute, addToCart, subTotal;
        TextView plus, minus;
        Button delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.product_image);
            title = itemView.findViewById(R.id.product_title);
            progressBar = itemView.findViewById(R.id.progressbar);
            quantity = itemView.findViewById(R.id.quantity);
            currency = itemView.findViewById(R.id.product_currency);
            attribute = itemView.findViewById(R.id.product_attribute);
//            plus = itemView.findViewById(R.id.quantity_plus);
            minus = itemView.findViewById(R.id.quantity_minus);
            subTotal = itemView.findViewById(R.id.sub_total);
            price = itemView.findViewById(R.id.product_price);
        }
    }
}
