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
import com.alaan.outn.interfac.ListItem;
import com.alaan.outn.model.Param;
import com.alaan.outn.model.StatesItem;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AdapterState extends RecyclerView.Adapter<AdapterState.MyViewHolder> {


    private List<StatesItem> list = new ArrayList<>();
    private String lastIdAnbar;
    private ItemClickListener<StatesItem> listener;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;

        MyViewHolder(@NonNull View view) {
            super(view);
            txtName = itemView.findViewById(R.id.txtName);
        }



    }


    @NonNull
    @Override
    public AdapterState.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_adapter_country, parent, false);
        return new AdapterState.MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final AdapterState.MyViewHolder holder, int position) {

        final StatesItem statesItem = list.get(position);

        holder.txtName.setText(statesItem.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.state(statesItem);
            }
        });
    }

    public void clearAndPut(@Nullable List<StatesItem> items) {

        if (list != null) {
            list.clear();
        }

        if (items != null) {
            list.addAll(items);
        }
        notifyDataSetChanged();

    }

    public void put(@Nullable List<StatesItem> items) {
        if (items != null) {
            list.addAll(items);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setListener(ItemClickListener<StatesItem> listener) {
        this.listener = listener;
    }

    public StatesItem getItem(int position) {
        return list.get(position);
    }


}

