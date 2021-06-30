package com.alaan.outn.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.alaan.outn.R
import com.alaan.outn.interfac.InterfaceShowListImage
import com.alaan.outn.model.ImagesItem
import com.bumptech.glide.Glide


class MyPager(context: Context, arrayList: List<String>?,position: Int, val interfaceShowListImage: InterfaceShowListImage) : PagerAdapter() {

     private val context: Context
     val arrayList:ArrayList<String>
    var positionRow:Int = 0

    init {
        this.context = context
        this.arrayList = arrayList as ArrayList<String>
        this.positionRow = position
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View = LayoutInflater.from(context).inflate(R.layout.pager_item, null)
        val imageView: ImageView = view.findViewById(R.id.image)
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context)
                .load(arrayList.get(position))
                .into(imageView)

        container.addView(view)

        imageView.setOnClickListener{
            interfaceShowListImage.listImage(arrayList,positionRow)
        }

        return view
    }

    /*
    Returns the count of the total pages
    */
    override fun getCount(): Int {
        return arrayList?.size!!
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as ImageView)
    }

    /*
    Used to determine whether the page view is associated with object key returned by instantiateItem.
    Since here view only is the key we return view==object
    */
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return `object` === view
    }

    private fun getImageAt(position: Int): String {
        return arrayList.get(position)
        }
    }