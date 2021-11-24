package com.example.ayomakan.model;

import io.realm.RealmObject;

public class UserModel extends RealmObject {

    private String username;
    private String imageUrl;

    public UserModel(){}

    public UserModel(String username) {
        this.username = username;
    }

    public UserModel(String username, String imageUrl) {
        this.username = username;
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
