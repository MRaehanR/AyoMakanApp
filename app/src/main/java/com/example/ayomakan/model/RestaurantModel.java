package com.example.ayomakan.model;

import io.realm.RealmList;
import io.realm.RealmObject;

public class RestaurantModel extends RealmObject {

    private String id;
    private String name;
    private String description;
    private String pictureId;
    private String city;
    RealmList<String> categories, foods, drinks;
    private double rating;

    public RestaurantModel(String id, String name, String description, String pictureId, String city, RealmList<String> categories, RealmList<String> foods, RealmList<String> drinks, double rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pictureId = pictureId;
        this.city = city;
        this.categories = categories;
        this.foods = foods;
        this.drinks = drinks;
        this.rating = rating;
    }

    public RestaurantModel(){}

    public RestaurantModel(String id, String name, String description, String pictureId, String city, double rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pictureId = pictureId;
        this.city = city;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

}
