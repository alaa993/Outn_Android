package com.alaan.outn.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.alaan.outn.R
import com.alaan.outn.dialog.CustomDialog
import com.alaan.outn.utils.LocaleUtils
import com.alaan.outn.utils.Preference
import com.alaan.outn.utils.Utils
import kotlinx.android.synthetic.main.activity_settinge.*

class ActivitySettings : AppCompatActivity() {

    private var customDialogNew: CustomDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_settinge)
        Utils.changeLanuge(this)
        init()
    }

    private fun init() {

        btnBack?.setOnClickListener { onBackPressed() }
        if (Preference.language.equals("en")) {
            radioEnglish?.setChecked(true)
        } else if (Preference.language.equals("ar")) {
            radioArabic?.setChecked(true)
        } else if (Preference.language.equals("ku")) {
            radioKurdish?.setChecked(true)
        } else if (Preference.language.equals("tr")) {
            radioTurkish?.setChecked(true)
        } else {
            radioEnglish.setChecked(true)
        }
        lnrEnglish.setOnClickListener {
            radioArabic.setChecked(false)
            radioKurdish.setChecked(false)
            radioTurkish.setChecked(false)
            radioEnglish.setChecked(true)
        }
        lnrKurdish.setOnClickListener {
            radioEnglish.setChecked(false)
            radioArabic.setChecked(false)
            radioTurkish.setChecked(false)
            radioKurdish.setChecked(true)
        }
        lnrArabic.setOnClickListener {
            radioEnglish.setChecked(false)
            radioKurdish.setChecked(false)
            radioTurkish.setChecked(false)
            radioArabic.setChecked(true)
        }
        lnrTurkish.setOnClickListener {
            radioEnglish.setChecked(false)
            radioKurdish.setChecked(false)
            radioArabic.setChecked(false)
            radioTurkish.setChecked(true)
        }
        radioKurdish.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                radioEnglish.setChecked(false)
                radioArabic.setChecked(false)
                radioTurkish.setChecked(false)
                Preference.language = "ku"
                //SharedHelper.putKey(this@ActivitySettings, "language", "ku")
                setLanguage()
                GoToMainActivity()
            }
        }
        radioArabic.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                radioEnglish.setChecked(false)
                radioKurdish.setChecked(false)
                radioTurkish.setChecked(false)
                Preference.language = "ar"
               // SharedHelper.putKey(this@ActivitySettings, "language", "ar")
                setLanguage()
                GoToMainActivity()
            }
        }
        radioEnglish.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                radioArabic.setChecked(false)
                radioKurdish.setChecked(false)
                radioTurkish.setChecked(false)
                Preference.language = "en"
                //SharedHelper.putKey(this@ActivitySettings, "language", "en")
                setLanguage()
                GoToMainActivity()
            }
        }
        radioTurkish.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                radioArabic.setChecked(false)
                radioKurdish.setChecked(false)
                radioEnglish.setChecked(false)
                Preference.language = "tr"
                //SharedHelper.putKey(this@ActivitySettings, "language", "en")
                setLanguage()
                GoToMainActivity()
            }
        }
    }

    fun GoToMainActivity() {


        val mainIntent = Intent(this@ActivitySettings, MainActivity::class.java)
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(mainIntent)
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
        finish()

    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleUtils.onAttach(base))
    }

    private fun setLanguage() {
        val languageCode: String = Preference.language!!
        LocaleUtils.setLocale(this, languageCode)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
