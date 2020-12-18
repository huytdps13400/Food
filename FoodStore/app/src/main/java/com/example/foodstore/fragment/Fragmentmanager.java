package com.example.foodstore.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodstore.R;
import com.example.foodstore.adapter.ChatsAdapter;
import com.example.foodstore.daofirebase.DaoStore;
import com.example.foodstore.model.Chat;
import com.example.foodstore.model.Store;
import com.example.foodstore.model.Token;
import com.example.foodstore.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

import static com.example.foodstore.MainActivity.toolbar;

public class Fragmentmanager extends Fragment {
    RecyclerView rcvuserchat;
    private ChatsAdapter chatsAdapter;
    private List<User> mUsers;
    FirebaseUser fuser;
    DatabaseReference reference;
    DaoStore daoStore;
    private List<String> usersList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentmanager,container,false);
        rcvuserchat = view.findViewById(R.id.chatuser);
        toolbar.setVisibility(View.VISIBLE);
        rcvuserchat.setHasFixedSize(true);
        rcvuserchat.setLayoutManager(new LinearLayoutManager(getContext()));
        // lấy được user đang login
        daoStore = new DaoStore(getActivity());
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        byChats(view);
        updateToken(FirebaseInstanceId.getInstance().getToken());

        return view;
    }
    public void byChats(View view) {
        usersList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chat");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);

                    if (chat.getSender().equals(fuser.getUid())) {
                        if (!usersList.contains(chat.getReceiver())) {
                            usersList.add(chat.getReceiver());

                        }
                    }
                    if (chat.getReceiver().equals(fuser.getUid())) {
                        if (!usersList.contains(chat.getSender())) {
                            usersList.add(chat.getSender());
                        }
                    }

                }
                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void readChats() {
        mUsers = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("User");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User store = snapshot.getValue(User.class);
                    for (String id : usersList) {

                        if (store.getToken().equalsIgnoreCase(id)) {
                            mUsers.add(store);
                        }
                    }
                }
                chatsAdapter = new ChatsAdapter(getContext(), mUsers, true);
                rcvuserchat.setAdapter(chatsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void updateToken(String token) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(fuser.getUid()).setValue(token1);
    }
}
