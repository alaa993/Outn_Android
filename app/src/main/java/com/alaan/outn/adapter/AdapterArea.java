package com.alaan.outn.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alaan.outn.R;
import com.alaan.outn.interfac.ItemClickListener;
import com.alaan.outn.model.AreasItem;
import com.alaan.outn.model.CitiesItem;

import java.util.ArrayList;
import java.util.List;


public class AdapterArea extends RecyclerView.Adapter<AdapterArea.MyViewHolder> {


    private List<AreasItem> list = new ArrayList<>();
    private String lastIdAnbar;
    private ItemClickListener<AreasItem> listener;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;

        MyViewHolder(@NonNull View view) {
            super(view);
            txtName = itemView.findViewById(R.id.txtName);
        }



    }


    @NonNull
    @Override
    public AdapterArea.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_adapter_country, parent, false);
        return new AdapterArea.MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final AdapterArea.MyViewHolder holder, int position) {

        final AreasItem areasItem = list.get(position);

        holder.txtName.setText(areasItem.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.area(areasItem);
            }
        });
    }

    public void clearAndPut(@Nullable List<AreasItem> items) {

        if (list != null) {
            list.clear();
        }

        if (items != null) {
            list.addAll(items);
        }
        notifyDataSetChanged();

    }

    public void put(@Nullable List<AreasItem> items) {
        if (items != null) {
            list.addAll(items);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setListener(ItemClickListener<AreasItem> listener) {
        this.listener = listener;
    }

    public AreasItem getItem(int position) {
        return list.get(position);
    }


}


