package com.alaan.outn.selectimage;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.alaan.outn.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MovieImagViewHolder> {
    private Context mContext;
    private List<MovieAddImag> movies;

    public ImageAdapter(Context mContext, List<MovieAddImag> movies) {
        this.mContext = mContext;
        this.movies = movies;

    }

    @NonNull
    @Override
    public MovieImagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_add_imag, parent, false);
        return new MovieImagViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MovieImagViewHolder holder, int position) {
        MovieAddImag movieAddImag1 = movies.get(position);
        if (position == 0) {
            holder.img.setImageResource(R.drawable.addimage);
        } else {
            if (movieAddImag1.getUrl().startsWith("http")) {
                Picasso.get()
                        .load(Uri.parse(movieAddImag1.getUrl()))
                        .centerCrop()
                        .fit().transform(new RoundedCornersTransformation(10, 2))
                        .into(holder.img);
            } else {
                File file = new File((movieAddImag1.getUrl()));
                if (file.exists()) {
                    Picasso.get()
                            .load(Uri.fromFile(file))
                            .centerCrop()
                            .fit()
                            .transform(new RoundedCornersTransformation(10, 2))
                            .into(holder.img);
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        if (movies.size() == 0) {
            return 1;
        }
        return movies.size();
    }

    public class MovieImagViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public MovieImagViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);

        }
    }

}
