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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.food.FoodProfileActivity;
import com.example.food.R;
import com.example.food.callback.Foodcallback;
import com.example.food.daofirebase.DaoFood;
import com.example.food.model.Favorite;
import com.example.food.model.Food;
import com.example.food.model.Token;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> {

    ArrayList<Food> categoryList;
    Context context;
    String pLikes;
    DatabaseReference databaseReference,fvrtref,fvrt_listRef;
    FirebaseUser user;
    DaoFood daoFood;
    Food food1 = null;
    Boolean mProcessLike =false;

    public FoodAdapter(ArrayList<Food> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemfood, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decimalFormat.applyPattern("#,###,###,###");
        Food categories = categoryList.get(position);
        holder.title.setText(categories.getNamefood());
        holder.txtdiachi.setText(categories.getDiachi());
        user = FirebaseAuth.getInstance().getCurrentUser();
        daoFood = new DaoFood(context);
        fvrtref = FirebaseDatabase.getInstance().getReference("favourites");
        fvrt_listRef = FirebaseDatabase.getInstance().getReference("favoriteList").child(user.getUid());


        holder.txtgia.setText(String.valueOf(decimalFormat.format(categories.getGia()) + " VNĐ"));
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
//        try {
//            databaseReference = FirebaseDatabase.getInstance().getReference("Favorite");
//            databaseReference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    String  idfood ;
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        Favorite favorite = snapshot.getValue(Favorite.class);
//                        idfood = favorite.getIdfood().getIdfood();
//                        if (favorite.getUiduser().equalsIgnoreCase(user.getUid()) &&favorite.getIdfood().getIdfood().equalsIgnoreCase(categories.getIdfood()) ) {
//                            holder.likeBtn.setImageResource(R.drawable.ic_heart_liked);
////                    holder.likeBtn.setText("Liked");
//                        } else  {
//                            holder.likeBtn.setImageResource(R.drawable.ic_heart);
////                    holder.likeBtn.setText("Like");
//                        }
//
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        }catch (Exception e){
//
//        }

      String  key = categories.getIdfood();
        favouriteChecker(key,holder);
        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProcessLike = true;


//

                fvrtref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        if (mProcessLike.equals(true)){
                            if (snapshot.child(key).hasChild(user.getUid())){
                                fvrtref.child(key).child(user.getUid()).removeValue();
                                fvrt_listRef.child(key).removeValue();
//                                delete(time);
                                Toast.makeText(context, "Removed from favourite", Toast.LENGTH_SHORT).show();
                                mProcessLike = false;
                            }else {


                                fvrtref.child(key).child(user.getUid()).setValue(true);
                                Favorite favorite = new Favorite(fvrt_listRef.push().getKey(), user.getUid(), categories,true);

//                                  String id = fvrt_listRef.push().getKey();
                                fvrt_listRef.child(key).setValue(favorite);
                                mProcessLike = false;

                                Toast.makeText(context, "Added to favourite", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
//                mProcessLike =true;
//                setLikes(holder, categories,true);
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
                intent.putExtra("gia", decimalFormat.format(categories.getGia()) + "\t VNĐ");
                intent.putExtra("namefood", categories.getNamefood());
                intent.putExtra("idfood", "Id: " + categories.getIdfood());
                intent.putExtra("idstore", categories.getIdstore());
                intent.putExtra("diachi", categories.getDiachi());
                intent.putExtra("sl", categories.getSoluong() + "");
                intent.putExtra("matl", categories.getMatheloai());
                intent.putExtra("status", categories.getStatus());
                intent.putExtra("mota", categories.getMota());
                intent.putExtra("tokenstore", categories.getTokenstore());
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, likeBtn;
        TextView title, txtdiachi, txtgia;
        ProgressBar progressBar;
        CardView cardView, cardView1, card_view4;
        LinearLayout line1;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imgfood);
            title = itemView.findViewById(R.id.txtnamefood);
            progressBar = itemView.findViewById(R.id.progressbar);
            txtdiachi = itemView.findViewById(R.id.txtdiachi);
            txtgia = itemView.findViewById(R.id.txtgia);
            line1 = itemView.findViewById(R.id.line1);
            likeBtn = itemView.findViewById(R.id.likeBtn);
            cardView1 = itemView.findViewById(R.id.cardview1);

        }
    }

    public void favouriteChecker(final String postkey, MyViewHolder holder) {




        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = user.getUid();

        fvrtref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child(postkey).hasChild(uid)){
                    holder.likeBtn.setImageResource(R.drawable.ic_heart_liked);
                }else {
                    holder.likeBtn.setImageResource(R.drawable.ic_heart);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

//    private void setLikes(final MyViewHolder holder, Food food,boolean id ) {
//        databaseReference = FirebaseDatabase.getInstance().getReference("Favorite");
//        if (id){
//            Favorite favorite = new Favorite(databaseReference.push().getKey(), user.getUid(), food,id);
//            key = databaseReference.push().getKey();
//            databaseReference.child(key).setValue(favorite).addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void aVoid) {
//                    Toast.makeText(context, "Bạn đã tích yêu thích món ăn này !", Toast.LENGTH_SHORT).show();
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(context, "Bạn đã gặp lỗi tích yêu thích món ăn này !", Toast.LENGTH_SHORT).show();
//
//                }
//            });
//        }else {
//            Toast.makeText(context, "Xóa", Toast.LENGTH_SHORT).show();
//        }
//
//
//        //                if (dataSnapshot.child(postKey).hasChild(user.getUid())) {
////
////
////                    holder.likeBtn.setImageResource(R.drawable.ic_heart_liked);
//////
////                } else {
////
////                    holder.likeBtn.setImageResource(R.drawable.ic_heart);
//////
////                }
//    }
//    private void likePost(String idfood) {
//        //get total number of likes for the post, whose like button clicked
//        //if currently signed in user has not liked it before
//        //increase value by 1, otherwise decrease value by 1
//        mProcessLike = true;
//        //get id of the post clicked
//        final DatabaseReference likesRef = FirebaseDatabase.getInstance().getReference("Favorite");
//        likesRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                if (mProcessLike){
//                    Favorite favorite = new Favorite(databaseReference.push().getKey(), user.getUid(), idfood);
//                    for (DataSnapshot snapshot :dataSnapshot.getChildren()){
//                        likesRef.child(idfood).setValue(favorite).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(context, "Bạn đã tích yêu thích món ăn này !", Toast.LENGTH_SHORT).show();
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(context, "Bạn đã gặp lỗi tích yêu thích món ăn này !", Toast.LENGTH_SHORT).show();
//
//                            }
//                        });;
//                        if (snapshot.child(idfood).hasChild(user.getUid())){
//                            likesRef.child(idfood).setValue(favorite);
//                        mProcessLike = false;
//
//                        }   else {
//                        // not liked, like it
//                        likesRef.child(idfood).child(user.getUid()).setValue("Liked"); //set any value
//                        mProcessLike = false;}
//
//                    }
////                    if (dataSnapshot.child(idfood).hasChild(user.getUid())){
////                        //already liked, so remove like
////                        postsRef.child(idfood).child("pLikes").setValue(""+(Integer.parseInt(pLikes)-1));
////                        likesRef.child(idfood).child(user.getUid()).removeValue();
////                        mProcessLike = false;
////                    }
////                    else {
////                        // not liked, like it
////                        postsRef.child(idfood).child("pLikes").setValue(""+(Integer.parseInt(pLikes)+1));
////                        likesRef.child(idfood).child(user.getUid()).setValue("Liked"); //set any value
////                        mProcessLike = false;
////
////
////                    }
////                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
}
