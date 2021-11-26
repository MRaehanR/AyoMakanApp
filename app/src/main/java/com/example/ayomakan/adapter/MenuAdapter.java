package com.example.ayomakan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayomakan.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder>{

    private List<String> dataList;

    public MenuAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @NotNull
    @Override
    public MenuAdapter.MenuViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.menu_list, parent, false);
        return new MenuAdapter.MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MenuAdapter.MenuViewHolder holder, int position) {
        holder.tvNamaMenu.setText(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        TextView tvNamaMenu;

        public MenuViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tvNamaMenu = itemView.findViewById(R.id.list_namaMenu_txt);
        }
    }
}
