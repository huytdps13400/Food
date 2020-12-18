package com.example.admin.fragment;

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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.R;
import com.example.admin.adapter.CategoryAdapter;
import com.example.admin.callback.Categoriescallback;
import com.example.admin.daofirebase.DaoCategories;
import com.example.admin.dialog.BottomSheef_Add_Category;
import com.example.admin.dialog.BottomSheef_Update_Category;
import com.example.admin.model.Categories;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class Fragmentcategory extends Fragment {
    public static RecyclerView rcvcategory;
    FloatingActionButton floatbtnthem;
    ArrayList<Categories> datacategories;
    DaoCategories daoCategories;
    CategoryAdapter categoryAdapter;
    FirebaseStorage storage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentcategory,container,false);
        rcvcategory = view.findViewById(R.id.rcvcategory);
        floatbtnthem=view.findViewById(R.id.floatbtnthem);
        daoCategories = new DaoCategories(getActivity());
        datacategories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(datacategories,getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rcvcategory.setLayoutManager(linearLayoutManager);
        rcvcategory.setHasFixedSize(true);
        rcvcategory.setAdapter(categoryAdapter);
        storage = FirebaseStorage.getInstance();
        daoCategories.getAll(new Categoriescallback() {
                                 @Override
                                 public void onSuccess(ArrayList<Categories> lists) {
                                     datacategories.clear();
                                     datacategories.addAll(lists);
                                     categoryAdapter.notifyDataSetChanged();
                                 }
                                 @Override
                                 public void onError(String message) {

                                 }
                             });
        floatbtnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheef_Add_Category bottomSheef_add_category = new BottomSheef_Add_Category();
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
                daoCategories =new DaoCategories(getContext());

                daoCategories.getAll(new Categoriescallback() {
                    @Override
                    public void onSuccess(ArrayList<Categories> lists) {
                        datacategories.clear();
                        datacategories.addAll(lists);
                        categoryAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(String message) {

                    }
                });
                args.putString("MaTL", datacategories.get(position).getMatheloai() + "");
                args.putString("TenTL", datacategories.get(position).getTentheloai() + "");
                args.putString("MoTa", datacategories.get(position).getMota() + "");
                switch (direction) {
                    case ItemTouchHelper.LEFT:
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Thông Báo");
                        builder.setMessage("Bạn có chắc muốn xóa không");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String maloai = datacategories.get(position).getMatheloai();
                                String tenloai = datacategories.get(position).getTentheloai();
                                String mota = datacategories.get(position).getMota();
                                String vitri = datacategories.get(position).getImage();
                                datacategories = new ArrayList<>();
                                daoCategories = new DaoCategories(getContext());
                                daoCategories.delete(maloai);
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
                                daoCategories.getAll(new Categoriescallback() {
                                    @Override
                                    public void onSuccess(ArrayList<Categories> lists) {
                                        datacategories.clear();
                                        datacategories.addAll(lists);
                                        categoryAdapter = new CategoryAdapter(datacategories,getActivity());
                                        rcvcategory.setAdapter(categoryAdapter);


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
                                datacategories = new ArrayList<>();

                                daoCategories =new DaoCategories(getContext());

                                daoCategories.getAll(new Categoriescallback() {
                                    @Override
                                    public void onSuccess(ArrayList<Categories> lists) {
                                        datacategories.clear();
                                        datacategories.addAll(lists);
                                        categoryAdapter = new CategoryAdapter(datacategories,getActivity());
                                        rcvcategory.setAdapter(categoryAdapter);
                                        categoryAdapter.notifyDataSetChanged();

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
                        BottomSheef_Update_Category bottomSheef_update_theLoai = new BottomSheef_Update_Category();
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
