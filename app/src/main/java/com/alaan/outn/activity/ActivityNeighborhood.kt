package com.alaan.outn.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Switch
import androidx.viewpager.widget.ViewPager
import com.alaan.outn.R
import com.alaan.outn.adapter.MyPager
import com.alaan.outn.interfac.InterfaceShowListImage
import com.alaan.outn.model.ImagesItem
import com.alaan.outn.utils.Utils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_neighborhood.*
import kotlinx.android.synthetic.main.activity_partners_details.*
import kotlinx.android.synthetic.main.activity_show_imag.*
import me.relex.circleindicator.CircleIndicator

class ActivityNeighborhood : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.setFillWindowAndTransparetStatusBar(this)
        setContentView(R.layout.activity_neighborhood)

        val listimageIntent = intent.getSerializableExtra("listImage") as List<ImagesItem>


        if (listimageIntent?.size ?: 0 > 0) {

            checkTypeImage(listimageIntent.get(0)?.type)?.let {
             text1.setText(it)
            }
                Glide.with(this)
                        .load(listimageIntent.get(0)?.path)
                        .into(image1)
        }else {
            image1.visibility = View.GONE
            text1.visibility = View.GONE
        }

        if (listimageIntent.size ?: 0 > 1) {


            checkTypeImage(listimageIntent.get(1)?.type)?.let {
                text2.setText(it)
            }
                Glide.with(this)
                        .load(listimageIntent.get(1)?.path)
                        .into(image2)
        }else {
            image2.visibility = View.GONE
            text2.visibility = View.GONE
        }

        if (listimageIntent.size ?: 0 > 2) {
            checkTypeImage(listimageIntent.get(2)?.type)?.let {
                text3.setText(it)
            }
                Glide.with(this)
                        .load(listimageIntent.get(2)?.path)
                        .centerInside()
                        .into(image3)
        }else {
            image3.visibility = View.GONE
            text3.visibility = View.GONE
        }

        if (listimageIntent.size ?: 0 >3) {
            checkTypeImage(listimageIntent.get(3)?.type)?.let {
                text4.setText(it)
            }
                Glide.with(this)
                        .load(listimageIntent.get(3)?.path)
                        .into(image4)
        }else {
            image4.visibility = View.GONE
            text4.visibility = View.GONE
        }

        btnBack_neigh.setOnClickListener {
            finish()
        }

        image1.setOnClickListener{
            val arrayList = ArrayList<String>()
            val  a = listimageIntent.get(0).path?.let { it1 -> arrayList.add(it1)
                listImage(arrayList)
            }

        }
        image2.setOnClickListener{
            val arrayList = ArrayList<String>()
            val  a = listimageIntent.get(1).path?.let { it1 -> arrayList.add(it1)
                listImage(arrayList)
            }
        }
        image3.setOnClickListener{
            val arrayList = ArrayList<String>()
            val  a = listimageIntent.get(2).path?.let { it1 -> arrayList.add(it1)
                listImage(arrayList)
            }

        }
        image4.setOnClickListener{
            val arrayList = ArrayList<String>()
            val  a = listimageIntent.get(3).path?.let { it1 -> arrayList.add(it1)
                listImage(arrayList)
            }
        }
    }


    fun checkTypeImage(type: String?): String {
        if (type.equals("MAIN_STREET")) {
          return  getString(R.string.main_street)
        } else if (type.equals("NEAREST_SCHOOL")) {
           return getString(R.string.nearest_school)
        } else if (type.equals("NEAREST_MARKETS")) {
          return getString(R.string.nearst_markets)
        } else if (type.equals("SPORT_CLUB")) {
          return  getString(R.string.sport_club)
        }
            return ""
    }

     fun listImage(get: ArrayList<String>) {
        val intent = Intent(this,ActivityShowImag::class.java)
        intent.putExtra("items",get)
        intent.putExtra("position",0)
        startActivity(intent)
    }
}
