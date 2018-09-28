package com.example.newapp;

import java.io.Serializable;

public class Content implements Serializable{
    private String name;
    private String imageId;
    private String contentId = "";

    public Content(String name, String imageId, String contentId) {
        this.name = name;
        this.imageId = imageId;
        this.contentId = contentId;
    }

    public Content(String name, String imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public String getImageId() {
        return imageId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }
}
