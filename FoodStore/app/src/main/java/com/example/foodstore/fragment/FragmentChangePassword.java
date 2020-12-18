package com.example.foodstore.fragment;

import android.os.Bundle;
import android.util.Log;
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

import com.example.foodstore.R;

import com.example.foodstore.callback.Storecallback;
import com.example.foodstore.daofirebase.DaoStore;
import com.example.foodstore.model.Store;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.foodstore.MainActivity.bnv;
import static com.example.foodstore.MainActivity.toolbar;


public class FragmentChangePassword extends Fragment {
     ImageView back;
    EditText edtoldpass, edtpassnew, edtxacnhanpass;
    DaoStore daoUser;
    String mail, name, phone, diachi, anh,pass;
    FirebaseUser firebaseUser;
    Button btnchangepassword;
    ArrayList<Store> dataUser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentchangepassword, container, false);
        bnv.setVisibility(View.GONE);
        toolbar.setVisibility(View.GONE);
        back = view.findViewById(R.id.back);
        edtoldpass = view.findViewById(R.id.edtoldpass);
        edtpassnew = view.findViewById(R.id.edtpassnew);
        edtxacnhanpass = view.findViewById(R.id.edtxacnhanpass);
        btnchangepassword = view.findViewById(R.id.btnchangepassword);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        dataUser = new ArrayList<>();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_l, new Fragmentproflie()).commit();
                bnv.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.VISIBLE);
            }
        });

    btnchangepassword.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            daoUser = new DaoStore(getContext());
            daoUser.getAll(new Storecallback() {
                @Override
                public void onSuccess(ArrayList<Store> lists) {
                    dataUser.clear();
                    dataUser.addAll(lists);

                }

                @Override
                public void onError(String message) {

                }
            });

            if (edtoldpass.getText().toString().trim().isEmpty() || edtpassnew.getText().toString().trim().isEmpty() || edtxacnhanpass.getText().toString().trim().isEmpty()) {
                Toast.makeText(getActivity(), "Vui Lòng Nhập Đầy Đủ 3 Trường", Toast.LENGTH_SHORT).show();
            }
           else if (edtoldpass.getText().toString().trim().length()<6 ||
                    edtpassnew.getText().toString().trim().length()<6 || edtxacnhanpass.getText().toString().trim().length()<6){
                Toast.makeText(getActivity(), "Mật khẩu phải có ít nhất 6 ký tự!",
                    Toast.LENGTH_SHORT).show();}
            else {
                for (int i =0;i<dataUser.size();i++){
                    if (dataUser.get(i).getTokenstore().equalsIgnoreCase(firebaseUser.getUid())){


                    if (!(edtoldpass.getText().toString().trim().equalsIgnoreCase(dataUser.get(i).getPass()))){
                        Toast.makeText(getActivity(), "Password cũ bạn nhập không đúng", Toast.LENGTH_SHORT).show();
                    }else {
                        pass = dataUser.get(i).getPass();
                        name = dataUser.get(i).getName();
                        phone = dataUser.get(i).getPhone();
                        diachi = dataUser.get(i).getDiachi();
                        mail = dataUser.get(i).getEmail();
                        anh = dataUser.get(i).getImage();

                        if (edtpassnew.getText().toString().trim().equalsIgnoreCase(edtoldpass.getText().toString().trim())){
                            Toast.makeText(getActivity(), "Password cũ với password mới không được trùng", Toast.LENGTH_SHORT).show();
                        }else {
                            if (!(edtxacnhanpass.getText().toString().equalsIgnoreCase(edtpassnew.getText().toString()))) {
                                Toast.makeText(getActivity(), "Password Xác nhận phải trùng Với Password mới", Toast.LENGTH_SHORT).show();
                            } else {

                                Store user = new Store(mail,edtpassnew.getText().toString().trim(),name,phone,anh,diachi,null,firebaseUser.getUid());
                                daoUser.update(user);
                                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                String newPassword = edtpassnew.getText().toString().trim();

                                firebaseUser.updatePassword(newPassword)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getContext(), "User password updated.", Toast.LENGTH_SHORT).show();}
                                            }
                                        });
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_l, new Fragmentproflie()).commit();
                                bnv.setVisibility(View.VISIBLE);
                                toolbar.setVisibility(View.VISIBLE);
                            }
                        }
                        }
                    }

                }
            }
        }
    });

        return view;
    }
}
