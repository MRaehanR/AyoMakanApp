package com.example.ayomakan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.ayomakan.R;
import com.example.ayomakan.adapter.RestaurantAdapter;
import com.example.ayomakan.model.RestaurantModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RestaurantAdapter adapter;
    ArrayList<RestaurantModel> restaurantlist;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getSupportActionBar().hide();

        pd = new ProgressDialog(DashboardActivity.this);

        recyclerView = findViewById(R.id.dashboard_rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        restaurantlist = new ArrayList<>();

        getData();

        adapter = new RestaurantAdapter(restaurantlist, this);
        recyclerView.setAdapter(adapter);

    }

    private void getData() {
        pd.setMessage("loading");
        pd.show();
        AndroidNetworking.get("https://restaurant-api.dicoding.dev/list")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray result = response.getJSONArray("restaurants");
                            for (int i = 0; i < result.length(); i++) {
                                JSONObject resultObj = result.getJSONObject(i);

                                String id = resultObj.getString("id");
                                String name = resultObj.getString("name");
                                String description = resultObj.getString("description");
                                String pictureId = "https://restaurant-api.dicoding.dev/images/medium/".concat(resultObj.getString("pictureId"));
                                String city = resultObj.getString("city");
                                double rating = resultObj.getDouble("rating");

                                restaurantlist.add(new RestaurantModel(id, name, description, pictureId, city, rating));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                        pd.dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                    }
                });
    }
}