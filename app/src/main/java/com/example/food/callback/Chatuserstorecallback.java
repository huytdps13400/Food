package com.example.food.callback;

import com.example.food.model.ChatUserStore;
import com.example.food.model.Food;

import java.util.ArrayList;

public interface Chatuserstorecallback {
    void onSuccess(ArrayList<ChatUserStore> lists);
    void onError(String message);
}
