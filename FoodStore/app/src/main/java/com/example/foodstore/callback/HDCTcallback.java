package com.example.foodstore.callback;
import com.example.foodstore.model.HDCT;


import java.util.ArrayList;

public interface HDCTcallback {
    void onSuccess(ArrayList<HDCT> lists);
    void onError(String message);
}
