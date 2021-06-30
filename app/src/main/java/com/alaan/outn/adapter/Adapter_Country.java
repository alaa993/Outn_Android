package com.alaan.outn.adapter;

import android.annotation.SuppressLint;
import android.view.Display;
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
import com.alaan.outn.model.Country;
import com.alaan.outn.model.ModelCountry;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Country extends RecyclerView.Adapter<Adapter_Country.MyViewHolder> {

    @NonNull
    private List<ModelCountry> list = new ArrayList<>();
    private String lastIdAnbar;
    private ItemClickListener<ModelCountry> listener;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;

        MyViewHolder(@NonNull View view) {
            super(view);
            txtName = itemView.findViewById(R.id.txtName);
        }


        ModelCountry item;

        public void bind(@NonNull final ModelCountry item) {
            this.item = item;
            txtName.setText(item.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(item);
                }
            });
        }

    }


    @NonNull
    @Override
    public Adapter_Country.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_adapter_country, parent, false);
        return new Adapter_Country.MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final Adapter_Country.MyViewHolder holder, int position) {

      // list.get(position);
        holder.bind( list.get(position));
    }

    public void clearAndPut(@Nullable List<ModelCountry> items) {
        list.clear();
        if (items != null) {
            list.addAll(items);
        }
        notifyDataSetChanged();
    }

    public void put(@Nullable List<ModelCountry> items) {
        if (items != null) {
            list.addAll(items);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setListener(ItemClickListener<ModelCountry> listener) {
        this.listener = listener;
    }

    public ModelCountry getItem(int position) {
        return list.get(position);
    }


}

