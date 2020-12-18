package com.example.food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.food.adapter.MessageAdapter;
import com.example.food.model.Chat;
import com.example.food.model.Store;
import com.example.food.model.Token;
import com.example.food.model.User;
import com.example.food.notification.Data;
import com.example.food.notification.Sender;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessegerActivity extends AppCompatActivity {
    private CircleImageView profileImage;
    private TextView username;
    private RecyclerView recycleviewMess;
    private EditText edtMessage;
    private ImageButton btnSendMess;
    private static final String AUTH_KEY = "key=AAAAxb_KheE:APA91bEXt6uisGWw6BEkNNV0OQ_bYydpwHjkZvXsE2CDauZU6DfiSlqjN0lL8dA1iwKk63iEybaxfS0pJ8atgUee49JfeDRSYzcwTQ15P6OHunaV7ZlKS9YB-4TfJtlp3UgjR6bsaLgI";
    Toolbar toolbar;

    FirebaseUser mUser;
    DatabaseReference reference;

    Intent intent;
    String userId;
    private RequestQueue requestQueue;
    MessageAdapter messageAdapter;
    List<Chat> mChat;
    String getAuthKey ="";
    ValueEventListener seenListener;


    boolean notify = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messeger);
        toolbar =  findViewById(R.id.toolbar);
        profileImage = (CircleImageView) findViewById(R.id.profile_image);
        username = (TextView) findViewById(R.id.username);
        recycleviewMess = (RecyclerView) findViewById(R.id.recycleview_mess);
        edtMessage = (EditText) findViewById(R.id.edtMessage);
        btnSendMess = (ImageButton) findViewById(R.id.btnSendMess);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setStatusBarColor(ContextCompat.getColor(MessegerActivity.this, R.color.cam));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MessegerActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        recycleviewMess.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recycleviewMess.setLayoutManager(linearLayoutManager);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        Bundle bundle = getIntent().getExtras();
        intent = getIntent();
        userId = intent.getStringExtra("userId");
        reference = FirebaseDatabase.getInstance().getReference("Store");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                        Store user = snapshot.getValue(Store.class);
                        if(user.getTokenstore().equalsIgnoreCase(userId)){
                            username.setText(user.getEmail());

                            if (user.getImage().equals("default")) {
                                profileImage.setImageResource(R.mipmap.ic_launcher);
                            } else {

                                //change
                                Glide.with(getApplicationContext()).load(user.getImage()).into(profileImage);
                            }
                        }


                    readMessage(mUser.getUid(), userId, user.getImage());
                }

                //        id user đang login/ id người nhận/ ảnh người nhận

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
seenMessage(userId);
    }
    private void seenMessage(final String userId) {
        reference = FirebaseDatabase.getInstance().getReference("Chat");

        seenListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(mUser.getUid()) && chat.getSender().equals(userId)) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("isseen", true);
                        snapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void sendMess(View view) {
        notify= true;
        String message = edtMessage.getText().toString();
        if (message.isEmpty()) {
            Toast.makeText(this, "Không thể gửi tin nhắn trống", Toast.LENGTH_SHORT).show();
        } else {

            sendMessage(mUser.getUid(), userId, message);
        }
        edtMessage.setText("");

    }
    private void sendMessage(String sender, final String receiver, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        long millis = System.currentTimeMillis();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("isseen", false);
        hashMap.put("time", millis);

        reference.child("Chat").push().setValue(hashMap);


        //thêm user vào màn hình ChatFragments
        //lấy mảng id người nhận theo id user đang login cho vào dữ liệu
        final DatabaseReference chatRef=FirebaseDatabase.getInstance().getReference("ChatList")
                .child(mUser.getUid())
                .child(userId);

        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    chatRef.child("id").setValue(userId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference chatRefReceiver = FirebaseDatabase.getInstance().getReference("ChatList")
                .child(userId)
                .child(mUser.getUid());
        chatRefReceiver.child("id").setValue(mUser.getUid());

        final String msg = message;

            reference = FirebaseDatabase.getInstance().getReference("User");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    if(user.getToken().equalsIgnoreCase(mUser.getUid())) {
                        if (notify) {
                            Toast.makeText(MessegerActivity.this, "Thành Công", Toast.LENGTH_SHORT).show();
                            sendNotifiaction(receiver, user.getEmail(), msg);
                        } else {
                            notify = false;
                            Toast.makeText(MessegerActivity.this, "Thất Công", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MessegerActivity.this, "Lỗi"+databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void sendNotifiaction(String receiver, final String username, final String message){
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(mUser.getUid(), R.mipmap.ic_launcher_round, username+": "+message, "New Message",
                            userId);
                    Sender sender = new Sender(data,token.getToken());
                    try {
                        JSONObject senderJsonObj = new JSONObject(new Gson().toJson(sender));
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://fcm.googleapis.com/fcm/send", senderJsonObj,
                                new com.android.volley.Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        //response of the request
                                        Log.d("JSON_RESPONSE", "onResponse: " + response.toString());

                                    }
                                }, new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("JSON_RESPONSE", "onResponse: " + error.toString());
                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                //put params
                                Map<String, String> headers = new HashMap<>();
                                headers.put("Content-Type", "application/json");
                                headers.put("Authorization", "key=AAAAxb_KheE:APA91bEXt6uisGWw6BEkNNV0OQ_bYydpwHjkZvXsE2CDauZU6DfiSlqjN0lL8dA1iwKk63iEybaxfS0pJ8atgUee49JfeDRSYzcwTQ15P6OHunaV7ZlKS9YB-4TfJtlp3UgjR6bsaLgI");


                                return headers;
                            }
                        };

                        //add this request to queue
                        requestQueue.add(jsonObjectRequest);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void readMessage(final String senderId, final String receiverId, final String imageUrl) {
        mChat = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chat");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);

                    //chỉ lấy chat giữa 2 user SENDER- RECEIVER
                    if (chat.getReceiver().equals(senderId) && chat.getSender().equals(receiverId)
                            || chat.getReceiver().equals(receiverId) && chat.getSender().equals(senderId)) {
                        mChat.add(chat);
                    }

                }
                messageAdapter = new MessageAdapter(MessegerActivity.this, mChat, imageUrl);
                recycleviewMess.setAdapter(messageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}