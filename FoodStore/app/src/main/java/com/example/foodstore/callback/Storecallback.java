package com.example.foodstore.callback;




import com.example.foodstore.model.Store;

import java.util.ArrayList;

public interface Storecallback {
    void onSuccess(ArrayList<Store> lists);
    void onError(String message);
}
