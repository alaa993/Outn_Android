package com.alaan.outn.activity

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.alaan.outn.R
import com.alaan.outn.adapter.AdapterImageSlider
import com.alaan.outn.interfac.interface_clickItemImge
import kotlinx.android.synthetic.main.activity_details.pager

class DetailsActivity : AppCompatActivity(), interface_clickItemImge {

    var mViewPager: ViewPager? = null
    var mCustomPagerAdapter: AdapterImageSlider? = null
    var list = arrayListOf<String>("https://moodle.htwchur.ch/pluginfile.php/124614/mod_page/content/4/example.jpg")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setupViewPager()
    }

    private fun setupViewPager() {

        mCustomPagerAdapter = AdapterImageSlider(this, list, this);
        pager!!.adapter = mCustomPagerAdapter
        pager!!.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                // sliderListener.onItemsClick(true, position)
            }
        })
    }

    override fun onItem(images: List<String?>?, pos: Int) {

        val intent = Intent(this, ActivityShowImag::class.java)
        intent.putStringArrayListExtra("items", images?.let { ArrayList(it) })
        intent.putExtra("position", pos)
        startActivity(intent)
    }
}



