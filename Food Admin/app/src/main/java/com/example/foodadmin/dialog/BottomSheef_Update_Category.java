package com.example.foodadmin.dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.foodadmin.R;
import com.example.foodadmin.adapter.CategoryAdapter;
import com.example.foodadmin.callback.Categoriescallback;
import com.example.foodadmin.daofirebase.DaoCategories;
import com.example.foodadmin.model.Categories;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static com.example.foodadmin.fragment.Fragmentcategory.rcvcategory;


public class BottomSheef_Update_Category extends BottomSheetDialogFragment {
    EditText edtmatl,edttentl,edtmota;
    Button btnaddimg, btnadd;
    ImageView imghinhshow;
    ArrayList<Categories> datatheloai = new ArrayList<>();
    DaoCategories daoCategories;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 22;
    FirebaseStorage storage;
    StorageReference storageReference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomm_sheft_update_category,container,false);
        edtmatl = view.findViewById(R.id.edt_matl);
        edttentl = view.findViewById(R.id.edt_tentl);
        edtmota = view.findViewById(R.id.edt_mota);
        imghinhshow = view.findViewById(R.id.imghinhshow);
        btnaddimg = view.findViewById(R.id.btnaddhinh);
        btnadd = view.findViewById(R.id.btnadd);
        Bundle bundle = getArguments();
        edtmatl.setText(bundle.getString("MaTL"));
        edttentl.setText(bundle.getString("TenTL"));
        edtmota.setText(bundle.getString("MoTa"));
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        btnaddimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change();
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(getActivity());
    }

    private void change() {
        if(filePath!=null){

            final StorageReference imageFolder = storageReference.child("Category/"+UUID.randomUUID().toString());
            imageFolder.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Categories theLoai = new Categories();
                            theLoai.setMatheloai(edtmatl.getText().toString());
                            theLoai.setTentheloai(edttentl.getText().toString());
                            theLoai.setMota(edtmota.getText().toString());
                            theLoai.setImage(uri.toString());
                            daoCategories = new DaoCategories(getContext());
                            daoCategories.update(theLoai);

//                            daoCategories.getAll(new Categoriescallback() {
//                                @Override
//                                public void onSuccess(ArrayList<Categories> lists) {
//                                    datatheloai.clear();
//                                    datatheloai.addAll(lists);
//                                    CategoryAdapter categoryAdapter = new CategoryAdapter(datatheloai,getActivity());
//                                    categoryAdapter.notifyDataSetChanged();
//                                    recyclerView.setAdapter(categoryAdapter);
//                                }
//
//                                @Override
//                                public void onError(String message) {
//
//                                }
//                            });

                            dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(),""+e.getMessage(),Toast.LENGTH_SHORT ).show();
                        }
                    });
                }
            });
        }
    }

    private void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContext().getContentResolver(),
                                filePath);
                imghinhshow.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        daoCategories = new DaoCategories(getActivity());
        daoCategories.getAll(new Categoriescallback() {
            @Override
            public void onSuccess(ArrayList<Categories> lists) {
                datatheloai.clear();
                datatheloai.addAll(lists);
                CategoryAdapter categoryAdapter = new CategoryAdapter(datatheloai,getActivity());
                categoryAdapter.notifyDataSetChanged();
                rcvcategory.setAdapter(categoryAdapter);
            }

            @Override
            public void onError(String message) {

            }
        });

    }

}
