package com.example.foodstore.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodstore.R;
import com.example.foodstore.adapter.FoodAdapter;
import com.example.foodstore.callback.Foodcallback;
import com.example.foodstore.daofirebase.DaoFood;
import com.example.foodstore.dialog.BottomSheef_Add_Food;
import com.example.foodstore.dialog.BottomSheef_Update_Food;
import com.example.foodstore.model.Food;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static com.example.foodstore.MainActivity.idstore;
import static com.example.foodstore.MainActivity.toolbar;

public class Fragmentcategory extends Fragment {
    public static RecyclerView rcvcategory;
    FloatingActionButton floatbtnthem;
    ArrayList<Food> foodArrayList;
    DaoFood daoFood;
    FoodAdapter foodAdapter;
    FirebaseStorage storage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentcategory,container,false);
        rcvcategory = view.findViewById(R.id.rcvcategory);
        toolbar.setVisibility(View.VISIBLE);
        floatbtnthem=view.findViewById(R.id.floatbtnthem);
        daoFood = new DaoFood(getActivity());
        foodArrayList = new ArrayList<>();
        foodAdapter = new FoodAdapter(foodArrayList,getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        rcvcategory.setLayoutManager(gridLayoutManager);
        rcvcategory.setHasFixedSize(true);
        rcvcategory.setAdapter(foodAdapter);
        storage = FirebaseStorage.getInstance();
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
        floatbtnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheef_Add_Food bottomSheef_add_category = new BottomSheef_Add_Food();
                bottomSheef_add_category.show(getFragmentManager(),bottomSheef_add_category.getTag());
            }
        });
        intswipe(view);
        return view;
    }
    public void intswipe(final View v) {

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();

                Bundle args = new Bundle();
                daoFood =new DaoFood(getContext());

                daoFood.getAll(new Foodcallback() {
                    @Override
                    public void onSuccess(ArrayList<Food> lists) {
                        foodArrayList.clear();
                        foodArrayList.addAll(lists);
                        foodAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(String message) {

                    }
                });
                args.putString("idfood", foodArrayList.get(position).getIdfood() + "");
                args.putString("namefood", foodArrayList.get(position).getNamefood() + "");
                args.putString("gia", foodArrayList.get(position).getGia() + "");
                args.putString("soluong", foodArrayList.get(position).getSoluong() + "");
                args.putString("mota", foodArrayList.get(position).getMota() + "");
                args.putString("diachi", foodArrayList.get(position).getDiachi() + "");
                args.putString("status", foodArrayList.get(position).getStatus() + "");
                args.putString("idstore", foodArrayList.get(position).getIdstore() + "");
                args.putString("matl", foodArrayList.get(position).getMatheloai() + "");
                args.putString("img", foodArrayList.get(position).getImage());
                switch (direction) {
                    case ItemTouchHelper.LEFT:
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Thông Báo");
                        builder.setMessage("Bạn có chắc muốn xóa không");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String maloai = foodArrayList.get(position).getIdfood();
                                String vitri = foodArrayList.get(position).getImage();
                                foodArrayList = new ArrayList<>();
                                daoFood = new DaoFood(getContext());
                                daoFood.delete(maloai);
                                String storageUrl = vitri;
                                StorageReference img = storage.getReferenceFromUrl(storageUrl);
                                img.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // File deleted successfully
                                        Log.d("TAG", "onSuccess: deleted file");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Uh-oh, an error occurred!
                                        Log.d("TAG", "onFailure: did not delete file");
                                    }
                                });
                                daoFood.getAll(new Foodcallback() {
                                    @Override
                                    public void onSuccess(ArrayList<Food> lists) {
                                        foodArrayList.clear();
                                        foodArrayList.addAll(lists);
                                        foodAdapter = new FoodAdapter(foodArrayList,getActivity());
                                        rcvcategory.setAdapter(foodAdapter);


                                    }

                                    @Override
                                    public void onError(String message) {

                                    }
                                });
                                dialog.cancel();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                foodArrayList = new ArrayList<>();

                                daoFood =new DaoFood(getContext());

                                daoFood.getAll(new Foodcallback() {
                                    @Override
                                    public void onSuccess(ArrayList<Food> lists) {
                                        foodArrayList.clear();
                                        foodArrayList.addAll(lists);
                                        foodAdapter = new FoodAdapter(foodArrayList,getActivity());
                                        rcvcategory.setAdapter(foodAdapter);
                                        foodAdapter.notifyDataSetChanged();

                                    }

                                    @Override
                                    public void onError(String message) {

                                    }
                                });

                                dialog.cancel();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        break;
                    case ItemTouchHelper.RIGHT:
                        BottomSheef_Update_Food bottomSheef_update_theLoai = new BottomSheef_Update_Food();
                        bottomSheef_update_theLoai.setArguments(args);
                        bottomSheef_update_theLoai.show(getActivity().getSupportFragmentManager(), bottomSheef_update_theLoai.getTag());

                        break;

                }


            }

            public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                    int actionState, boolean isCurrentlyActive) {

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.Do))
                        .addSwipeLeftActionIcon(R.drawable.ic_delete_black_24dp)
                        .create()
                        .decorate();

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(), R.color.Xanh))
                        .addSwipeRightActionIcon(R.drawable.ic_sua)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rcvcategory);
    }
}
