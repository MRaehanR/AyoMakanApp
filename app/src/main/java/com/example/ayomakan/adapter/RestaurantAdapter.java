package com.example.ayomakan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ayomakan.R;
import com.example.ayomakan.activity.DetailActivity;
import com.example.ayomakan.model.RestaurantModel;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    public List<RestaurantModel> restaurantModels;
    Context context;

    public RestaurantAdapter(List<RestaurantModel> restaurantModels, Context context) {
        this.restaurantModels = restaurantModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RestaurantAdapter.RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resto_list, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantAdapter.RestaurantViewHolder holder, int position) {
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
    }

    @Override
    public int getItemCount() {
        return (restaurantModels != null) ? restaurantModels.size() : 0;
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvDescription, tvRating;
        ImageView ivPicture;
        CardView cvRestaurant;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.list_namaResto_txt);
            tvDescription = itemView.findViewById(R.id.list_deskripsi_txt);
            ivPicture = itemView.findViewById(R.id.list_poster);
            cvRestaurant = itemView.findViewById(R.id.cv_resto);
            tvRating = itemView.findViewById(R.id.list_rating_text);

        }
    }
}
