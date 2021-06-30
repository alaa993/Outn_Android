package com.alaan.outn.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.alaan.outn.R
import com.alaan.outn.utils.ZoomOutPageTransformer
import com.alaan.outn.adapter.AdapterImageSlider
import com.alaan.outn.interfac.interface_clickItemImge
import com.alaan.outn.utils.Utils
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import kotlinx.android.synthetic.main.activity_show_imag.*

import java.util.ArrayList


class ActivityShowImag : AppCompatActivity(),interface_clickItemImge {

    var viewPager: ViewPager? = null
    var item: List<String>? = null
    var mCustomPagerAdapter: AdapterImageSlider? = null
    var imgBack: ImageView? = null
    var worm_dots_indicator: WormDotsIndicator? = null
    var position = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.setFillWindowAndTransparetStatusBar(this)
        setContentView(R.layout.activity_show_imag)
        readView()
        item = intent.getStringArrayListExtra("items")
        position = intent.getIntExtra("position", 0)
        setupViewPager()
    }

    private fun readView() {
        viewPager = findViewById(R.id.viewPager)
      //  imgBack = findViewById(R.id.imgBack)
        worm_dots_indicator = findViewById(R.id.worm_dots_indicator)
        btnBack.setOnClickListener(View.OnClickListener { onBackPressed() })
    }

    private fun setupViewPager() {

        mCustomPagerAdapter = item?.let { AdapterImageSlider(this, it,this) }
        viewPager!!.adapter = mCustomPagerAdapter
        viewPager!!.currentItem = position
        worm_dots_indicator!!.setViewPager(viewPager)
        viewPager!!.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                viewPager!!.setPageTransformer(true, ZoomOutPageTransformer())
            }

            override fun onPageSelected(position: Int) {}
        })
    }

    override fun onItem(images: List<String?>?, pos: Int) {

    }
}