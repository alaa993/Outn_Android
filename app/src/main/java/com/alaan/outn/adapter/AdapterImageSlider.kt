package com.alaan.outn.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.viewpager.widget.PagerAdapter
import com.alaan.outn.R
import com.alaan.outn.interfac.interface_clickItemImge
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.squareup.picasso.Picasso

class AdapterImageSlider( private val mContext: Context, imageAddresses: List<String?>,val itemListener:interface_clickItemImge) : PagerAdapter() {

    private val mLayoutInflater: LayoutInflater?
    private val mResources: List<String?>

    override fun getCount(): Int {
        return mResources.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    @SuppressLint("CheckResult")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val itemView = mLayoutInflater!!.inflate(R.layout.pager_item_show_image, container, false)
        val imageView = itemView.findViewById<ImageView>(R.id.imgKala)
        imageView.setOnClickListener {

            itemListener.onItem(mResources,position)

        }


        Glide.with(mContext).load(mResources.get(position)).into(imageView)


        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as FrameLayout)
    }


    init {
        mLayoutInflater =
            mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mResources = imageAddresses
    }
}