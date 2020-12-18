package com.example.foodstore.callback;






import com.example.foodstore.model.Food;

import java.util.ArrayList;

public interface Foodcallback {
    void onSuccess(ArrayList<Food> lists);
    void onError(String message);
}
