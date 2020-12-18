package com.example.food;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetpasswordActivity extends AppCompatActivity {
    EditText edtemail;
    Button btnresetpassword;
    ImageView back;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        edtemail = findViewById(R.id.edtemail);
        back = findViewById(R.id.back);
        btnresetpassword = findViewById(R.id.btnresetpassword);
        getWindow().setStatusBarColor(ContextCompat.getColor(ResetpasswordActivity.this, R.color.cam));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResetpasswordActivity.this,LoginActivity.class));
            }
        });
        btnresetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();


                auth.sendPasswordResetEmail(edtemail.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ResetpasswordActivity.this, "Email sent.", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ResetpasswordActivity.this,LoginActivity.class));
                                }
                            }
                        });
            }
        });
    }
}