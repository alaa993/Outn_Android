package com.alaan.outn.utils

class DetailOnPageChangeListener(val imageUrls: List<String>?) : androidx.viewpager.widget.ViewPager.OnPageChangeListener {
    var currentPage: Int = 0
    override fun onPageSelected(position: Int) {
        currentPage = position

    }

    override fun onPageScrollStateChanged(arg0: Int) {}

    override fun onPageScrolled(arg0: Int, Offset: Float, positionOffsetPixels: Int) {
        if (Offset > 0.5f) {
        }
    }
}