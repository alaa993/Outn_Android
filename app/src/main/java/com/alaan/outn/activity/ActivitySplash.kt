package com.alaan.outn.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.alaan.outn.R
import com.alaan.outn.utils.Utils

class ActivitySplash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.setFillWindowAndTransparetStatusBar(this)
        setContentView(R.layout.activitysplash)

        Handler().postDelayed({
            intent  = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)

        }, 2000)
    }

}