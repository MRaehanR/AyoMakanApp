package com.example.ayomakan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ayomakan.R;

public class DetailActivity extends AppCompatActivity {

    String id, name, description, pictureId, city;
    Double rating;
    ImageView ivPicture;
    TextView tvName, tvDescription, tvAlamat, tvRating;
    Bundle bundle;

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

        bundle = getIntent().getExtras();
        if (bundle != null){
            id = bundle.getString("id");
            name = bundle.getString("name");
            description = bundle.getString("description");
            pictureId = bundle.getString("pictureId");
            city = bundle.getString("city");
            rating = bundle.getDouble("rating");
        }

        tvName.setText(name);
        tvDescription.setText(description);
        tvAlamat.setText(city);
        tvRating.setText(rating.toString());
        Glide.with(getApplicationContext())
                .load(pictureId)
                .into(ivPicture);

    }
}