package com.example.ayomakan.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ayomakan.R;
import com.example.ayomakan.activity.DetailActivity;
import com.example.ayomakan.helper.RealmHelper;
import com.example.ayomakan.model.RestaurantModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>{

    RealmHelper realmHelper;
    Realm realm;
    public List<RestaurantModel> restaurantModels;
    Context context;

    public FavoriteAdapter(List<RestaurantModel> restaurantModels, Context context) {
        this.restaurantModels = restaurantModels;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resto_list, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FavoriteAdapter.FavoriteViewHolder holder, int position) {
        Double rating = restaurantModels.get(position).getRating();
        holder.tvRating.setText(rating.toString());
        holder.tvName.setText(restaurantModels.get(position).getName());
        holder.tvDescription.setText(restaurantModels.get(position).getDescription().substring(0, 80) + "...");
        Glide.with(context)
                .load(restaurantModels.get(position).getPictureId())
                .into(holder.ivPicture);
        holder.cvRestaurant.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("id", restaurantModels.get(position).getId());
            intent.putExtra("description", restaurantModels.get(position).getDescription());
            intent.putExtra("name", restaurantModels.get(position).getName());
            intent.putExtra("city", restaurantModels.get(position).getCity());
            intent.putExtra("rating", restaurantModels.get(position).getRating());
            intent.putExtra("pictureId", restaurantModels.get(position).getPictureId());
            context.startActivity(intent);
        });
        holder.positionItem = restaurantModels.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return (restaurantModels != null) ? restaurantModels.size() : 0;
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{

        String positionItem;
        TextView tvName, tvDescription, tvRating;
        ImageView ivPicture;
        CardView cvRestaurant;

        public FavoriteViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.list_namaResto_txt);
            tvDescription = itemView.findViewById(R.id.list_deskripsi_txt);
            ivPicture = itemView.findViewById(R.id.list_poster);
            cvRestaurant = itemView.findViewById(R.id.cv_resto);
            tvRating = itemView.findViewById(R.id.list_rating_text);

            cvRestaurant.setOnCreateContextMenuListener(this);

            RealmConfiguration configuration = new RealmConfiguration.Builder().build();
            realm = Realm.getInstance(configuration);
            realmHelper = new RealmHelper(realm);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.option2:
                    Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                    realmHelper.delete(positionItem);
                    break;
            }
            notifyDataSetChanged();
            return false;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(R.menu.context_menu, R.id.option2, 2, "Delete").setOnMenuItemClickListener(this);
        }
    }
}
