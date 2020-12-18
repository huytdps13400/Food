package com.example.food.adapterSlide;

public class ModelItem {
    private String description;
    private String imageurl;

    public ModelItem() {
    }

    public ModelItem(String description, String imageurl) {
        this.description = description;
        this.imageurl = imageurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
