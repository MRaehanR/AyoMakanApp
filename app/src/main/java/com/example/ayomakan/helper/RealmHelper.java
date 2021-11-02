package com.example.ayomakan.helper;

import android.util.Log;

import com.example.ayomakan.model.RestaurantModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {

    Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    public void save(final RestaurantModel restaurantModel) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null) {
                    Log.e("Created", "Database was created");
                    RestaurantModel model = realm.copyToRealm(restaurantModel);
                } else {
                    Log.e("Failed", "execute: Database not Exist");
                }
            }
        });
    }

    public List<RestaurantModel> getAllMovies() {
        RealmResults<RestaurantModel> results = realm.where(RestaurantModel.class).findAll();
        return results;
    }

    public void update(final String id, final String name, final String description, final String city, final Double rating){
        realm.executeTransaction(realm1 -> {
            RestaurantModel restaurantModel = realm.where(RestaurantModel.class)
                    .equalTo("id", id)
                    .findFirst();
            restaurantModel.setName(name);
            restaurantModel.setDescription(description);
            restaurantModel.setCity(city);
            
        });
    }

//    public void delete(String id){
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                final RealmResults<RestaurantModel> model = realm.where(RestaurantModel.class).equalTo("id", id).findAll();
//                Log.d("Model dari Delete", String.valueOf(model));
//                model.deleteFirstFromRealm();
//            }
//        });
//    }

    public void delete(String name){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                final RealmResults<RestaurantModel> model = realm.where(RestaurantModel.class).equalTo("name", name).findAll();
                Log.d("Model dari Delete", String.valueOf(model));
                model.deleteFirstFromRealm();
            }
        });
    }
}
