package com.example.newapp;

public class MyListItem {
    private String itemName;
    private int itemImageId;

    public MyListItem(String itemName, int itemImageId){
        this.itemImageId = itemImageId;
        this.itemName = itemName;
    }

    public int getItemImageId() {
        return itemImageId;
    }

    public String getItemName() {
        return itemName;
    }
}
