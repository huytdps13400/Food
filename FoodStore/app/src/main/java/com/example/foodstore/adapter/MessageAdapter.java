package com.example.foodstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.foodstore.R;
import com.example.foodstore.model.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ChatHolder> {

    public static final int MSG_TYPE_RIGHT = 1;
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_TIME= 2;
    private Context mContext;
    private List<Chat> mChat;
    String imageUrl;

    FirebaseUser fuser;

    public MessageAdapter(Context mContext, List<Chat> mChat, String imageUrl) {
        this.mContext = mContext;
        this.mChat = mChat;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public MessageAdapter.ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new ChatHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new ChatHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
        Chat chat = mChat.get(position);
        holder.tvMessage.setText(chat.getMessage());
        if (imageUrl.equals("default")) {
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mContext).load(imageUrl).into(holder.profile_image);
        }

        if(position==mChat.size()-1){

            if(chat.isIsseen()){
                
                holder.tvSeen.setText("Đã xem");
            }else{
                holder.tvSeen.setText("Đã gửi");
            }
        }else{
            holder.tvSeen.setVisibility(View.GONE);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(chat.getTime());
        holder.tvTime.setText(sdf.format(resultdate));
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ChatHolder extends RecyclerView.ViewHolder {
        private CircleImageView profile_image;
        private TextView tvMessage;
        private TextView tvSeen;
        private TextView tvTime;

        public ChatHolder(View itemView) {
            super(itemView);
            profile_image = (CircleImageView) itemView.findViewById(R.id.profile_image);
            tvMessage = (TextView) itemView.findViewById(R.id.tvMessage);
            tvSeen = (TextView) itemView.findViewById(R.id.tvSeen);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(fuser.getUid())) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}
