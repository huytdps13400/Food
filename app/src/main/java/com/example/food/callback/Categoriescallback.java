package com.example.food.callback;


import com.example.food.model.Categories;

import java.util.ArrayList;

public interface Categoriescallback {
    void onSuccess(ArrayList<Categories> lists);
    void onError(String message);
}
