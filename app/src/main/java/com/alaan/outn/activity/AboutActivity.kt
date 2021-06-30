package com.alaan.outn.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alaan.outn.R
import com.alaan.outn.utils.Utils
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.setFillWindowAndTransparetStatusBar(this)
        setContentView(R.layout.activity_about)
        Utils.changeLanuge(this)

        btnBack.setOnClickListener {
            finish()
        }
    }
}
