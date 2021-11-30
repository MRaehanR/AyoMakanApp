package com.example.ayomakan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ayomakan.R;
import com.example.ayomakan.helper.RealmHelper;
import com.example.ayomakan.model.RestaurantModel;
import com.example.ayomakan.model.UserModel;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class EditProfileActivity extends AppCompatActivity {

    ImageButton btn_back;
    ImageView iv_image;
    Button btn_save;
    EditText etUsername;
    UserModel userModels;
    Realm realm;
    RealmHelper realmHelper;
    Bundle bundle;
    String username;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().hide();

        btn_back = findViewById(R.id.editProfile_back_btn);
        etUsername = findViewById(R.id.editProfile_Username_editText);
        btn_save = findViewById(R.id.editProfile_save_btn);
        iv_image = findViewById(R.id.editprofile_editFoto_logo);

        iv_image.setClickable(true);


        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
        realm = Realm.getInstance(configuration);
        realmHelper = new RealmHelper(realm);

        bundle = getIntent().getExtras();
        if (bundle != null){
            username = bundle.getString("username");
        }

        Log.d("MSG", username);


        btn_back.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
        });

        iv_image.setOnClickListener(v -> {
            openFileChooser();
        });

        btn_save.setOnClickListener(v -> {
            String newUsername = etUsername.getText().toString();
            AtomicReference<UserModel> model = new AtomicReference<>(realm.where(UserModel.class).equalTo("username", username).findFirst());
            userModels = new UserModel(newUsername);

            Log.d("LHO", String.valueOf(model.get()));

            if (etUsername.getText().toString().trim().isEmpty()){
                Toast.makeText(getApplicationContext(), "Username are required", Toast.LENGTH_SHORT).show();
            } else {
                if(model.get() == null){
                    realmHelper.saveProfile(userModels);
                    Toast.makeText(getApplicationContext(), "Profile Saved", Toast.LENGTH_SHORT).show();
                } else {
                    realmHelper.updateProfile(username, newUsername);
                    Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                finish();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
    }
}