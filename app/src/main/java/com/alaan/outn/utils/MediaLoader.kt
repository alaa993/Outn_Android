package com.alaan.outn.utils

import android.widget.ImageView
import com.alaan.outn.R

import com.bumptech.glide.Glide

import com.yanzhenjie.album.AlbumFile

import com.yanzhenjie.album.AlbumLoader


class MediaLoader : AlbumLoader {
    override fun load(imageView: ImageView, albumFile: AlbumFile) {
        load(imageView, albumFile.path)
    }

    override fun load(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
                .load(url)
                .error(R.drawable.camera)
                .placeholder(R.drawable.camera)
                .into(imageView)
    }
}