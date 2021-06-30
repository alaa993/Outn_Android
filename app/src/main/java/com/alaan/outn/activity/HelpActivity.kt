package com.alaan.outn.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alaan.outn.R
import com.alaan.outn.utils.Utils
import kotlinx.android.synthetic.main.activity_help.*

class HelpActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.setFillWindowAndTransparetStatusBar(this)
        setContentView(R.layout.activity_help)
        Utils.changeLanuge(this)
        lb_email.setOnClickListener(this)
        btnBack.setOnClickListener(this)
        lb_phone_Number_whatsapp.setOnClickListener(this)

    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnBack -> finish()
            R.id.lb_email -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/html"
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("info@outn.net"))
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name) + "-" + getString(R.string.help))
                intent.putExtra(Intent.EXTRA_TEXT, "Hello team")
                startActivity(Intent.createChooser(intent, "Send Email"))
            }

            R.id.lb_phone_Number_whatsapp -> {
                val url = "https://api.whatsapp.com/send?phone="+"+447732830221";
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }
        }
    }
}


