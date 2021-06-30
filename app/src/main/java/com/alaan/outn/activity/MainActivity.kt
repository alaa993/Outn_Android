package com.alaan.outn.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.viewpager.widget.ViewPager
import com.alaan.outn.R
import com.alaan.outn.adapter.MyPager
import com.alaan.outn.api.Repository
import com.alaan.outn.api.interfaces.CallBack
import com.alaan.outn.application.G
import com.alaan.outn.interfac.InterfaceShowListImage
import com.alaan.outn.model.Api
import com.alaan.outn.model.CitiesItem
import com.alaan.outn.model.ModelSetting
import com.alaan.outn.utils.*
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_profile.*
import me.relex.circleindicator.CircleIndicator
import org.jetbrains.anko.toast
import java.text.ParsePosition
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener,InterfaceShowListImage {

    val COUNTRY = 500
    var CITY = 502
    private var plateCountryCode = "0"
    var cityId = "0"
    var nameCity = ""
    var namecountry = ""
    var text = ""
    private var myPager: MyPager? = null
    var viewPager: ViewPager? = null
    var circleIndicator: CircleIndicator? = null
    private val listCity = mutableListOf<List<CitiesItem>>()
    lateinit var mAdView : AdView
    private var latestVersion = "1.0"
    private var currentVersion = "1.0"

    private var mTimer: Timer? = null
    private var mPage = 0
    private var mPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.setFillWindowAndTransparetStatusBar(this)
        setLanguage()
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        readView()
        functionView()

        //for Google Ad
        MobileAds.initialize(this,"ca-app-pub-6606021354718512~1107820858")
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

//        getAdv()
        checkVersion()
        getSetting()
    }


    private fun setLanguage() {
        val languageCode: kotlin.String = Preference.language!!//SharedHelper.getKey(this, "language")
        LocaleUtils.setLocale(this, languageCode)
        Utils.changeLanuge(this)
    }


    fun readView() {
        val spannable = SpannableString("outn")
        spannable.setSpan(
                ForegroundColorSpan(Color.rgb(244, 0, 12)),
                3,
                4,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        title_app.text = spannable

        btnRent.setOnClickListener(this)
        btnSale.setOnClickListener(this)
        txt_city_main.setOnClickListener(this)
        btn_search.setOnClickListener(this)

//        mPager = view_pager_main


    }


    fun functionView() {

        profile_image.setOnClickListener {

            if (Preference.isLogin!!) {
                intent = Intent(applicationContext, ProfileActivity::class.java)
                startActivity(intent)
            } else {
                intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            }

        }

        btn_login.setOnClickListener {

            intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }

        info.setOnClickListener {

            intent = Intent(applicationContext, HelpActivity::class.java)
            startActivity(intent)
        }

        txt_country.setOnClickListener {
            intent = Intent(applicationContext, CountryList::class.java)
            startActivityForResult(intent, COUNTRY)
        }

        btn_activity.setOnClickListener{
              intent = Intent(applicationContext, ShowActivityPlatform::class.java)
            startActivity(intent)
        }

        btn_partner.setOnClickListener{
            intent = Intent(applicationContext,ActivityPartners::class.java)
            startActivity(intent)
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                COUNTRY -> {
                    if (data != null) {

                        plateCountryCode = data.getIntExtra("id", 0).toString()
                        namecountry = data.getStringExtra("name").toString()
                        cityId = plateCountryCode
                        txt_country.text = data.getStringExtra("name")
                        txt_country.setTextColor(Color.BLACK)

                    }
                }


                CITY -> {

                    cityId = data?.getIntExtra("id", 0).toString()
                    nameCity = data?.getStringExtra("name").toString()
                    txt_city_main.text = data?.getStringExtra("name")
                    txt_city_main.setTextColor(Color.BLACK)

                }


            }
        }


    }


    override fun onClick(view: View) {

        when (view.id) {

            R.id.btnRent -> {

                if (plateCountryCode.equals("0") && cityId.equals("0")){
                    cityId = ""
                }
                intent = Intent(applicationContext, ActivitySearchResult::class.java)
                intent.putExtra("search", "")
                intent.putExtra("ad_type_id", 1)
                intent.putExtra("location",cityId)
                startActivity(intent)

            }

            R.id.btnSale -> {

                if (plateCountryCode == "0" && cityId == "0"){
                    cityId = ""
                }
                intent = Intent(applicationContext, ActivitySearchResult::class.java)
                intent.putExtra("search", "")
                intent.putExtra("ad_type_id", 2)
                intent.putExtra("location",cityId)
                startActivity(intent)
            }

            R.id.txt_city_main -> {
                if (!plateCountryCode.equals("0")){

                val intent = Intent(this, ActivityCity::class.java)
                    intent.putExtra("params",plateCountryCode)
                startActivityForResult(intent, CITY)
                }else {
                    toast(resources.getString(R.string.place_first_select_country))
                }
            }

            R.id.txt_country -> {

                val intent = Intent(this, CountryList::class.java)
                startActivityForResult(intent, COUNTRY)

            }
            R.id.btn_search ->{

                if (plateCountryCode.equals("0") && cityId.equals("0")){
                    cityId = ""
                }
                intent = Intent(applicationContext, ActivitySearchResult::class.java)
                intent.putExtra("search", "")
                intent.putExtra("location",cityId)
                startActivity(intent)
            }

        }

    }


    override fun onResume() {
        super.onResume()

        lLogin.isVisible = !Preference.isLogin!!

        if (Preference.business_name != null && Preference.business_name != ""){
            txt_business.visibility = View.VISIBLE
            txt_business.text = Preference.business_name
            title_app.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 40F)
            updateTokenPushNotfication()
        }else {
            txt_business.visibility = View.GONE
            title_app.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 40F)
        }

        Glide.with(this)
                .load(Preference.image)
                .placeholder(R.drawable.profile)
                .fitCenter()
                .into(profile_image)

        getSetting()
    }

//    fun getAdv() {
//
//        Repository().getInstance()?.getAdv(object : CallBack<Api<List<kotlin.String>>>(){
//
//            override fun onSuccess(t: Api<List<kotlin.String>>) {
//                super.onSuccess(t)
//                var count = 0
//
//                pageSwitcher(6, t.data?.size!!)
//                mPager?.apply { addOnPageChangeListener(DetailOnPageChangeListener(t.data)) }
//                myPager = MyPager(this@MainActivity, t.data ,1,this@MainActivity)
//                view_pager_main.adapter = myPager
//                circle_main.setViewPager(view_pager_main)
//
//            }
//
//        })
//
//
//
//
//    }

    override fun listImage(get: ArrayList<kotlin.String>,position:Int) {

        val intent = Intent(this,ActivityShowImag::class.java)
        intent.putExtra("items",get)
        intent.putExtra("position",0)
        startActivity(intent)
    }


    internal inner class RemindTask(var noOfBanners: Int) : TimerTask() {
        override fun run() {
            runOnUiThread {
                if (mPage > noOfBanners)
                    mTimer?.cancel()
                else if (mPage == noOfBanners - 1) {
                    mPager?.currentItem = 0
                    mPage = 0
                } else
                    mPager?.currentItem = ++mPage
            }
        }
    }


    fun pageSwitcher(seconds: Int, length: Int) {
        val swipeAtInterval = RemindTask(length)
        mTimer = Timer()
        mTimer?.scheduleAtFixedRate(swipeAtInterval, 0, (seconds * 1500).toLong())
    }


    private fun checkVersion() {
        try {
            this.let {
                VersionChecker(object : VersionChecker.GetVersionCode {
                    override fun getVersionCode(version: String) {
                        latestVersion = version
                        compareVersion()
                    }
                }, it).execute()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            latestVersion = "1.0"
            compareVersion()
        }
    }

    private fun compareVersion() {
        try {
            val pInfo = this.packageManager?.getPackageInfo(this.packageName, 0)
            currentVersion = pInfo?.versionName!!
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (versionCompare(latestVersion, currentVersion) >= 1) {

            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle(getString(R.string.warning))
            builder.setMessage(getString(R.string.new_version_available))
            builder.setPositiveButton(getString(R.string.update)){dialog, which ->
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(this.let { it1 -> "https://play.google.com/store/apps/details?id=" + G.context?.applicationContext?.packageName })
                this.startActivity(i)
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }


    companion object {
        fun versionCompare(str1: String, str2: String): Int {
            Log.d("VersionCompare", "======>" + str1 + "======>" + str2)
            if (str1 == str2) {
                return -1
            } else {
                val vals1 = str1.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val vals2 = str2.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                var i = 0
                while (i < vals1.size && i < vals2.size && vals1[i] == vals2[i]) {
                    i++
                }
                if (i < vals1.size && i < vals2.size) {
                    val diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]))
                    return Integer.signum(diff)
                }
                return Integer.signum(vals1.size - vals2.size)
            }
        }
    }



    @SuppressLint("HardwareIds")
    private fun updateTokenPushNotfication(){
        Log.d("token",Preference.idPushNotfcation!!)
        val deviceID = Settings.Secure.getString(contentResolver,
                Settings.Secure.ANDROID_ID)
      Repository().remoteRepository?.pushToken(Preference.idPushNotfcation!!,deviceID,"Android",object :CallBack<Api<List<Void>>>() {
          override fun onSuccess(t: Api<List<Void>>) {
              super.onSuccess(t)
              Log.d("token",t.status?.message.toString())
          }

          override fun onFail(e: Exception, code: Int) {
              super.onFail(e, code)
              Log.d("token",e.localizedMessage)
          }
      })

    }



    fun getSetting(){
        Repository().getInstance()?.getSetting(Preference.language,object :CallBack<Api<ModelSetting>>(){
            override fun onSuccess(t: Api<ModelSetting>) {
                super.onSuccess(t)


                    if (Preference.language == "en"){
                        Preference.vision = t.data?.vision?.en
                    }else if (Preference.language == "ar"){
                        Preference.vision = t.data?.vision?.ar
                    }else {
                        Preference.vision = t.data?.vision?.ku
                    }

            }

            override fun onFail(e: Exception, code: Int) {
                super.onFail(e, code)
            }

        })
    }

}










