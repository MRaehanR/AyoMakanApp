package com.example.ayomakan.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.ayomakan.R;
import com.example.ayomakan.adapter.RestaurantAdapter;
import com.example.ayomakan.helper.RealmHelper;
import com.example.ayomakan.model.RestaurantModel;
import com.example.ayomakan.model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    RestaurantAdapter adapter;
    ArrayList<RestaurantModel> restaurantlist;
    EditText etSearch;
    String API = "https://restaurant-api.dicoding.dev/list";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.dashboard_rv);
        etSearch = view.findViewById(R.id.dashboard_search_btn);

        System.out.println("ASDASDASDASD"+ etSearch.getText().toString());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        restaurantlist = new ArrayList<>();

        getData();

        Realm.init(getContext());
        RealmConfiguration configuration = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
        Realm realm = Realm.getInstance(configuration);
        RealmHelper realmHelper = new RealmHelper(realm);

        UserModel userModel = new UserModel("USERNAME", null);
        realmHelper.saveProfile(userModel);


        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("KAS", String.valueOf(s));
//                restaurantlist.clear();
                API = "https://restaurant-api.dicoding.dev/search?q=".concat(String.valueOf(s));
//                getData();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (API == "https://restaurant-api.dicoding.dev/search?q="){
                    restaurantlist.clear();
                    API = "https://restaurant-api.dicoding.dev/list";
                }
                getData();
            }
        });
    }

    private void getData() {
        AndroidNetworking.get(API)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            restaurantlist.clear();
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
                                adapter = new RestaurantAdapter(restaurantlist, getActivity());
                                recyclerView.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                    }
                });
    }
}