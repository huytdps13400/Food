package com.example.foodstore.fragment;

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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodstore.R;


import com.example.foodstore.callback.Storecallback;
import com.example.foodstore.daofirebase.DaoStore;
import com.example.foodstore.model.Food;
import com.example.foodstore.model.Store;
import com.example.foodstore.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.example.foodstore.MainActivity.bnv;
import static com.example.foodstore.MainActivity.toolbar;


public class FragmentEditProfile extends Fragment {
   public static ImageView back;

    DaoStore daoUser;
    EditText edtname, edtphone, edtmail, edtaddress;
    Button btnupdateprofile;
    CircleImageView imgprofile;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 22;
    FirebaseStorage storage;
    StorageReference storageReference;
    String pass;

    FirebaseUser firebaseUser;
    String mail, name, phone, diachi, anh;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmenteditprofile, container, false);
        bnv.setVisibility(View.GONE);
        toolbar.setVisibility(View.GONE);
        back = view.findViewById(R.id.back);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_l, new Fragmentproflie()).commit();
                bnv.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.VISIBLE);
            }
        });
        edtname = view.findViewById(R.id.profilename);
        edtphone = view.findViewById(R.id.profilephone);
        edtmail = view.findViewById(R.id.profilemail);
        edtaddress = view.findViewById(R.id.profileaddress);
        imgprofile = view.findViewById(R.id.imgprofile);
        btnupdateprofile = view.findViewById(R.id.updateprofile);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        daoUser = new DaoStore(getContext());
        daoUser.getAll(new Storecallback() {
            @Override
            public void onSuccess(ArrayList<Store> lists) {
                for (int i = 0; i < lists.size(); i++) {
                    if (lists.get(i).getTokenstore().equalsIgnoreCase(firebaseUser.getUid())) {
                        name = lists.get(i).getName();
                        diachi=lists.get(i).getDiachi();
                        mail=lists.get(i).getEmail();
                        phone=lists.get(i).getPhone();
                        pass=lists.get(i).getPass();
                        anh=lists.get(i).getImage();
                    }
                }

                edtaddress.setText(diachi);
                edtmail.setText(mail);
                edtname.setText(name);
                edtphone.setText(phone);
                if (anh ==null){
                    Picasso.get().load("https://vnn-imgs-a1.vgcloud.vn/image1.ictnews.vn/_Files/2020/03/17/trend-avatar-1.jpg").into(imgprofile);
                }else if (anh !=null){
                    Picasso.get().load(anh).into(imgprofile);
                }
            }

            @Override
            public void onError(String message) {

            }
        });






        imgprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });
        btnupdateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filePath != null) {
                    String email = edtmail.getText().toString().trim();
                    String phone = edtphone.getText().toString().trim();
                    String ten = edtname.getText().toString().trim();
                    String diachi = edtaddress.getText().toString().trim();
                    if (email.isEmpty() || phone.isEmpty() || ten.isEmpty() || diachi.isEmpty()) {
                        Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ các trường", Toast.LENGTH_SHORT).show();
                    } else if (!email.matches("^[a-zA-Z][a-z0-9_\\.]{4,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$")) {
                        Toast.makeText(getActivity(), "Email Không Hợp Lệ", Toast.LENGTH_SHORT).show();
                    } else if (phone.length() < 10 || phone.length() > 12) {
                        Toast.makeText(getActivity(), "Vui lòng nhập đúng số điện thoại!", Toast.LENGTH_SHORT).show();
                    } else {
                        change();
                    }
                } else {
                    String email = edtmail.getText().toString().trim();
                    String phone = edtphone.getText().toString().trim();
                    String ten = edtname.getText().toString().trim();
                    String diachi = edtaddress.getText().toString().trim();
                    if (email.isEmpty() || phone.isEmpty() || ten.isEmpty() || diachi.isEmpty()) {
                        Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ các trường", Toast.LENGTH_SHORT).show();
                    } else if (!email.matches("^[a-zA-Z][a-z0-9_\\.]{4,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$")) {
                        Toast.makeText(getActivity(), "Email Không Hợp Lệ", Toast.LENGTH_SHORT).show();
                    } else if (phone.length() < 10 || phone.length() > 12) {
                        Toast.makeText(getActivity(), "Vui lòng nhập đúng số điện thoại!", Toast.LENGTH_SHORT).show();
                    } else {
                        Store store = new Store();
                        store.setEmail(edtmail.getText().toString());
                        store.setName(edtname.getText().toString());
                        store.setPhone(edtphone.getText().toString());
                        store.setDiachi(edtaddress.getText().toString());
                        store.setPass(pass);
                        store.setDiachi(edtaddress.getText().toString());
                        store.setImage(anh);
                        store.setTokenstore(firebaseUser.getUid());
                        daoUser = new DaoStore(getContext());
                        daoUser.update(store);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_l, new Fragmentproflie()).commit();
                        bnv.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.VISIBLE);
                    }
                }


            }
        });
        return view;
    }

    private void change() {

            final StorageReference imageFolder = storageReference.child("User/" + UUID.randomUUID().toString());
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
                            store.setImage(uri.toString());
                            store.setPass(pass);
                            store.setTokenstore(firebaseUser.getUid());
                            daoUser = new DaoStore(getContext());
                            daoUser.update(store);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_l, new Fragmentproflie()).commit();
                            bnv.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.VISIBLE);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

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
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }
}
