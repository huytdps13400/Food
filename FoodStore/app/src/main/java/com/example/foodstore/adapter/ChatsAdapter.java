package com.example.foodstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.foodstore.ChatActivity;
import com.example.foodstore.R;
import com.example.foodstore.model.Chat;
import com.example.foodstore.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatHolder> {
    private Context mContext;
    private List<User> users;
    private boolean isChat;

    String theLastMessage;
    Long theTimeMessage;
    public ChatsAdapter(Context mContext, List<User> users, boolean isChat) {
        this.mContext = mContext;
        this.users = users;
        this.isChat = isChat;
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_chat, parent, false);
        return new ChatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
        final User user = users.get(position);

        //title
        holder.tvItemChat.setText(user.getEmail());

        //image
        if (user.getImage().equals("default")) {
            holder.imgItemChat.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mContext).load(user.getImage()).into(holder.imgItemChat);
        }

        //last message

        if(isChat){
            lastMessage(user.getToken(),holder.tvItemMess,holder.tvItemTime);
        }else{
            holder.tvItemMess.setVisibility(View.GONE);
        }

//        if (isChat) {
//            if (user.getStatus().equals("online")) {
//                holder.imgOn.setVisibility(View.VISIBLE);
//                holder.imgOff.setVisibility(View.GONE);
//            } else {
//                holder.imgOn.setVisibility(View.GONE);
//                holder.imgOff.setVisibility(View.VISIBLE);
//            }
//        } else {
//            holder.imgOn.setVisibility(View.GONE);
//            holder.imgOff.setVisibility(View.GONE);
//        }

        holder.itemChats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("userId", user.getToken());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ChatHolder extends RecyclerView.ViewHolder {
        private CircleImageView imgItemChat;
        private TextView tvItemChat;
        private TextView tvItemMess;
        private TextView tvItemTime;
        private CircleImageView imgOn;
        private CircleImageView imgOff;
        private RelativeLayout itemChats;





        public ChatHolder(View itemView) {
            super(itemView);

            imgItemChat = (CircleImageView) itemView.findViewById(R.id.imgItemChat);
            tvItemChat = (TextView) itemView.findViewById(R.id.tvItemChat);
            tvItemMess = (TextView) itemView.findViewById(R.id.tvItemMess);
            tvItemTime = (TextView) itemView.findViewById(R.id.tvItemTime);
            imgOn = (CircleImageView) itemView.findViewById(R.id.img_on);
            imgOff = (CircleImageView) itemView.findViewById(R.id.img_off);
            itemChats = (RelativeLayout) itemView.findViewById(R.id.itemChats);
        }
    }

    //check for last message
    private void lastMessage(final String userid, final TextView last_msg, final TextView lastTime){
        //time
        final SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");


        theLastMessage = "default";
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chat");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (firebaseUser != null && chat != null) {
                        if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid) ||
                                chat.getReceiver().equals(userid) && chat.getSender().equals(firebaseUser.getUid())) {
                            theLastMessage = chat.getMessage();
                            theTimeMessage=chat.getTime();
                        }
                    }
                }

                switch (theLastMessage){
                    case  "default":
                        last_msg.setText("No Message");
                        break;

                    default:
                        last_msg.setText(theLastMessage);
                        lastTime.setText(sdf.format(theTimeMessage));
                        break;
                }

                theLastMessage = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
