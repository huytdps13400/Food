package com.example.foodstore.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.foodstore.MainActivity;
import com.example.foodstore.R;

import com.example.foodstore.daofirebase.DaoStore;

import com.example.foodstore.model.Store;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.example.foodstore.MainActivity.toolbar;

public class FragmentUpdateProfile extends BottomSheetDialogFragment {

    DaoStore daoStore;
    EditText edtname, edtphone, edtmail, edtaddress;
    Button btnupdateprofile;
    CircleImageView imgprofile;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 22;
    FirebaseStorage storage;
    StorageReference storageReference;
    String pass;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentupdateprofile, container, false);
        toolbar.setVisibility(View.GONE);
        edtname = view.findViewById(R.id.profilename);
        edtphone = view.findViewById(R.id.profilephone);
        edtmail = view.findViewById(R.id.profilemail);
        edtaddress = view.findViewById(R.id.profileaddress);
        imgprofile = view.findViewById(R.id.imgprofile);
        btnupdateprofile = view.findViewById(R.id.updateprofile);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        Bundle getdata = getArguments();
        edtmail.setText(getdata.getString("email"));
        pass= getdata.getString("pass");
        imgprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });
        btnupdateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtmail.getText().toString().trim();
                String phone = edtphone.getText().toString().trim();
                String ten = edtname.getText().toString().trim();
                String diachi = edtaddress.getText().toString().trim();
                if (email.isEmpty() || phone.isEmpty() || ten.isEmpty() || diachi.isEmpty()) {
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ các trường", Toast.LENGTH_SHORT).show();
                } else if (!email.matches("^[a-zA-Z][a-z0-9_\\.]{4,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$")) {
                    Toast.makeText(getActivity(), "Email Không Hợp Lệ", Toast.LENGTH_SHORT).show();
                }else if(phone.length() < 10 || phone.length() > 12){
                    Toast.makeText(getActivity(), "Vui lòng nhập đúng số điện thoại!", Toast.LENGTH_SHORT).show();
                }else {
                    change();
                }

            }
        });
        return view;
    }
    private void change() {
        if(filePath!=null){

            final StorageReference imageFolder = storageReference.child("Store/"+ UUID.randomUUID().toString());
            imageFolder.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Store store = new Store();
                            store.setEmail(edtmail.getText().toString());
                            store.setName(edtname.getText().toString());
                            store.setPhone(edtphone.getText().toString());
                            store.setDiachi(edtaddress.getText().toString());
                            store.setPass(pass);
                            store.setImage(uri.toString());
                            daoStore = new DaoStore(getContext());
                            daoStore.update(store);
                            startActivity(new Intent(getActivity(), MainActivity.class));
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
                imgprofile.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }
}
