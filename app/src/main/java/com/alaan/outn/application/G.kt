package com.alaan.outn.application

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Environment
import android.os.Handler
import android.util.Log
import androidx.multidex.MultiDex
import com.alaan.outn.application.BuildVars.DEBUG_VERSION
import java.io.File

class G : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        File(DIR_APP).mkdirs()
        File(DIR_DOWNLOAD).mkdirs()
        context = applicationContext
        sharedPref = com.alaan.outn.application.G.Companion.context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPrefSale = com.alaan.outn.application.G.Companion.context?.getSharedPreferences(PREFS_NAME_SALE, Context.MODE_PRIVATE)
        sharedPrefRent = com.alaan.outn.application.G.Companion.context?.getSharedPreferences(PREFS_NAME_RENT, Context.MODE_PRIVATE)
        MultiDex.install(this);

    }

    companion object {
        lateinit var instance: Context
        var context: Context? = null

        var sharedPref: SharedPreferences? = null
        var sharedPrefSale: SharedPreferences? = null
        var sharedPrefRent: SharedPreferences? = null
        private const val PREFS_NAME = "outn"
        private const val PREFS_NAME_SALE = "sale"
        private const val PREFS_NAME_RENT = "rent"
        val HANDLER = Handler()
        val DEBUG: Boolean = DEBUG_VERSION
        val DIR_SDCARD = Environment.getExternalStorageDirectory().absolutePath
        val DIR_APP = "$DIR_SDCARD/Qdor"
        val DIR_DOWNLOAD = "$DIR_APP/Download"
        var typefaceRoman: Typeface? = null
        var typefaceTStd: Typeface? = null

        fun log(tag: String?, body: String?) {
            if (DEBUG) {
            }
        }

    }
}