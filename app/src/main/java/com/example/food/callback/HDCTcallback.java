package com.example.food.callback;
import com.example.food.model.HDCT;


import java.util.ArrayList;

public interface HDCTcallback {
    void onSuccess(ArrayList<HDCT> lists);
    void onError(String message);
}
