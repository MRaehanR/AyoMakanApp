package com.example.ayomakan.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ayomakan.R;
import com.example.ayomakan.fragment.HomeFragment;
import com.example.ayomakan.helper.RealmHelper;
import com.example.ayomakan.model.RestaurantModel;

import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DetailActivity extends AppCompatActivity {

    String id, name, description, pictureId, city;
    Double rating;
    ImageView ivPicture;
    TextView tvName, tvDescription, tvAlamat, tvRating;
    Bundle bundle;
    ImageButton ibBack, ibFavorite;
    Realm realm;
    RealmHelper realmHelper;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().hide();

        tvName = findViewById(R.id.detail_namaRestoran_txt);
        tvDescription = findViewById(R.id.detail_deskripsi_txt);
        tvAlamat = findViewById(R.id.detail_alamat_txt);
        tvRating = findViewById(R.id.detail_rating_txt);
        ivPicture = findViewById(R.id.detail_detailImage);
        ibBack = findViewById(R.id.detail_back_btn);
        ibFavorite = findViewById(R.id.detail_fav_btn);

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
        realm = Realm.getInstance(configuration);
        realmHelper = new RealmHelper(realm);

        bundle = getIntent().getExtras();
        if (bundle != null){
            id = bundle.getString("id");
            name = bundle.getString("name");
            description = bundle.getString("description");
            pictureId = bundle.getString("pictureId");
            city = bundle.getString("city");
            rating = bundle.getDouble("rating");
        }

        AtomicReference<RestaurantModel> model = new AtomicReference<>(realm.where(RestaurantModel.class).equalTo("name", name).findFirst());
        Log.d("TAG", String.valueOf(model));
        if(model.get() == null){
            ibFavorite.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4C4C4C")));
        }else {
            ibFavorite.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
        }

        tvName.setText(name);
        tvDescription.setText(description);
        tvAlamat.setText(city);
        tvRating.setText(rating.toString());
        Glide.with(getApplicationContext())
                .load(pictureId)
                .into(ivPicture);

        ibBack.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
        });

        ibFavorite.setOnClickListener(v -> {
            RestaurantModel restaurantModel = new RestaurantModel(id, name, description, pictureId, city, rating);
            model.set(realm.where(RestaurantModel.class).equalTo("name", name).findFirst());

            if(model.get() == null){
                realmHelper.save(restaurantModel);
                ibFavorite.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
                Toast.makeText(getApplicationContext(), "add to your favorite", Toast.LENGTH_SHORT).show();
            } else {
                realmHelper.delete(name);
                ibFavorite.setImageTintList(ColorStateList.valueOf(Color.parseColor("#4C4C4C")));
                Toast.makeText(getApplicationContext(), "remove from your favorite", Toast.LENGTH_SHORT).show();
            }
        });
    }
}