package com.alaan.outn.activity

import android.Manifest.permission
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.alaan.outn.R
import com.alaan.outn.api.Repository
import com.alaan.outn.api.interfaces.CallBack
import com.alaan.outn.model.*
import com.alaan.outn.utils.MediaLoader
import com.alaan.outn.utils.Preference
import com.alaan.outn.utils.Utils
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.theartofdev.edmodo.cropper.CropImage
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig
import com.yanzhenjie.album.AlbumFile
import kotlinx.android.synthetic.main.activity_rent.btnBack
import kotlinx.android.synthetic.main.activity_rent.btn_submit
import kotlinx.android.synthetic.main.activity_rent.edt_des
import kotlinx.android.synthetic.main.activity_rent.edt_meter
import kotlinx.android.synthetic.main.activity_rent.edt_rooms
import kotlinx.android.synthetic.main.activity_rent.txt_city
import kotlinx.android.synthetic.main.activity_rent.txt_country
import kotlinx.android.synthetic.main.activity_rent.txt_home_type
import kotlinx.android.synthetic.main.activity_sale.*
import org.jetbrains.anko.selector
import org.jetbrains.anko.textColor
import org.jetbrains.anko.toast
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class SaleActivity : AppCompatActivity(), View.OnClickListener {

    private val listState = mutableListOf<List<StatesItem>>()
    private val listCity = mutableListOf<List<CitiesItem>>()
    private val listArae = mutableListOf<List<AreasItem>>()
    private var plateCountryCode: String? = ""
    val GALLERY = 1
    val GALLERY_MULTIPLE = 100
    val CAMERA = 2
    val COUNTRY = 500
    val STATE = 501
    var CITY = 502
    val Area = 503
    var itemClick = 1
    var path1 = ""
    var path2 = ""
    var path3 = ""
    var path4 = ""
    var path5 = ""
    var path6 = ""
    var path7 = ""
    var path8 = ""

    var path9 = ""
    var path10 = ""
    var path11 = ""
    var path12 = ""

    var homeType: Int = 0
    var adversType: Int = 0
    var cityId = "0"
    var idCurrency = 0

    var modelEdit: ModelListHome? = null
    private var mCurrentPhotoPath: String? = null;
    private var imagesUris = ArrayList<AlbumFile>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.setFillWindowAndTransparetStatusBar(this)
        Utils.changeLanuge(this)
        setContentView(R.layout.activity_sale)

        val extras: Bundle? = intent.extras;
        if (extras != null) {
            modelEdit = intent.getSerializableExtra("model") as ModelListHome;
        }
        readView()
        Album.initialize(AlbumConfig.newBuilder(this)
                .setAlbumLoader(MediaLoader())
                .build())
    }


    fun readView() {

        val spannable = SpannableString("outn")
        spannable.setSpan(
                ForegroundColorSpan(Color.rgb(244, 0, 12)),
                3,
                4,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        title_app.setText(spannable)

        image_first.setOnClickListener(this)
        image_seconds.setOnClickListener(this)
        image_three.setOnClickListener(this)
        image_four.setOnClickListener(this)
        image_five_sale.setOnClickListener(this)
        image_six_sale.setOnClickListener(this)
        image_seven_sale.setOnClickListener(this)
        image_eight_sale.setOnClickListener(this)
        image_nine_sale.setOnClickListener(this)
        image_ten_sale.setOnClickListener(this)
        image_eleven_sale.setOnClickListener(this)
        image_twelve_sale.setOnClickListener(this)


        txt_home_type.setOnClickListener(this)
        btn_submit.setOnClickListener(this)
        txt_country.setOnClickListener(this)
        btnBack.setOnClickListener(this)
        txt_city.setOnClickListener(this)
        txt_typ_currency1.setOnClickListener(this)
        edt_des.setOnTouchListener { v, event ->
            if (v?.id == R.id.edt_des) {
                v.parent.requestDisallowInterceptTouchEvent(true)
                when (event?.action?.and(MotionEvent.ACTION_MASK)) {
                    MotionEvent.ACTION_UP -> v.parent.requestDisallowInterceptTouchEvent(false)
                }
            }
            false
        }


        if (modelEdit != null) {
            setEditValue()
        } else {
            setPrefValues()
        }

        checkImagePath(path1, path2, path3, path4, path5, path6, path7, path8, path9, path10, path11, path12)
        img_close_sale_one.setOnClickListener {
            path1 = ""
            Preference.saveSaleImagePath1 = ""
            Preference.saveSaleImage1 = ""
            Glide.with(this)
                    .load(R.drawable.camera)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .centerCrop()
                    .into(image_first)
            img_close_sale_one.visibility = View.GONE
        }

        img_close_sale_two.setOnClickListener {
            path2 = ""
            Preference.saveSaleImagePath2 = ""
            Preference.saveSaleImage2 = ""
            Glide.with(this)
                    .load(R.drawable.camera)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .centerCrop()
                    .into(image_seconds)
            img_close_sale_two.visibility = View.GONE
        }

        img_close_sale_three.setOnClickListener {
            path3 = ""
            Preference.saveSaleImagePath3 = ""
            Preference.saveSaleImage3 = ""
            Glide.with(this)
                    .load(R.drawable.camera)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_three)
            img_close_sale_three.visibility = View.GONE
        }

        img_close_sale_four.setOnClickListener {
            path4 = ""
            Preference.saveSaleImagePath4 = ""
            Preference.saveSaleImage4 = ""
            Glide.with(this)
                    .load(R.drawable.camera)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_four)
            img_close_sale_four.visibility = View.GONE
        }

        img_close_sale_five.setOnClickListener {
            path5 = ""
            Preference.saveSaleImagePath5 = ""
            Preference.saveSaleImage5 = ""
            Glide.with(this)
                    .load(R.drawable.camera)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_five_sale)
            img_close_sale_five.visibility = View.GONE
        }

        img_close_sale_six.setOnClickListener {
            path6 = ""
            Preference.saveSaleImagePath6 = ""
            Preference.saveSaleImage6 = ""
            Glide.with(this)
                    .load(R.drawable.camera)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_six_sale)
            img_close_sale_six.visibility = View.GONE
        }

        img_close_sale_seven.setOnClickListener {
            path7 = ""
            Preference.saveSaleImagePath7 = ""
            Preference.saveSaleImage7 = ""
            Glide.with(this)
                    .load(R.drawable.camera)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_seven_sale)
            img_close_sale_seven.visibility = View.GONE
        }

        img_close_sale_eight.setOnClickListener {
            path8 = ""
            Preference.saveSaleImagePath8 = ""
            Preference.saveSaleImage8 = ""
            Glide.with(this)
                    .load(R.drawable.camera)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_eight_sale)
            img_close_sale_eight.visibility = View.GONE
        }

        img_close_sale_nine.setOnClickListener {
            path9 = ""
            Preference.saveSaleImagePath9 = ""
            Preference.saveSaleImage9 = ""
            Glide.with(this)
                    .load(R.drawable.camera)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_nine_sale)
            img_close_sale_nine.visibility = View.GONE
        }

        img_close_sale_ten.setOnClickListener {
            path10 = ""
            Preference.saveSaleImagePath10 = ""
            Preference.saveSaleImage10 = ""
            Glide.with(this)
                    .load(R.drawable.camera)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_ten_sale)
            img_close_sale_ten.visibility = View.GONE
        }

        img_close_sale_eleven.setOnClickListener {
            path11 = ""
            Preference.saveSaleImagePath11 = ""
            Preference.saveSaleImage11 = ""
            Glide.with(this)
                    .load(R.drawable.camera)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_eleven_sale)
            img_close_sale_eleven.visibility = View.GONE
        }

        img_close_sale_twelve.setOnClickListener {
            path12 = ""
            Preference.saveSaleImagePath12 = ""
            Preference.saveSaleImage12 = ""
            Glide.with(this)
                    .load(R.drawable.camera)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_twelve_sale)
            img_close_sale_twelve.visibility = View.GONE
        }
    }

    private fun manageEditTextsPrefValues() {
        edt_rooms.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Preference.saveSaleRoom = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        edt_meter.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Preference.saveSaleSpace = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        edt_price.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Preference.saveSalePrice = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        ed_phon_number1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Preference.saveSalePhone = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        edt_des.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Preference.saveSaleDescription = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        edt_neighborhood_sale.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Preference.saveSaleAboutTheNeigh = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun setPrefValues() {
        manageEditTextsPrefValues()
        managePrefValues()
    }

    private fun managePrefValues() {
        Preference.saveSaleHomeTypeText?.let { text ->
            txt_home_type.text = text
            Preference.saveSaleHomeTypeId?.let { id ->
                homeType = id
            }
            txt_home_type.setTextColor(Color.BLACK)
        }

        Preference.saveSaleCountryText?.let { text ->
            txt_country.text = text
            Preference.saveSaleCountryId?.let { id ->
                plateCountryCode = id.toString()
            }
            txt_country.setTextColor(Color.BLACK)
        }

        Preference.saveSaleCityText?.let { text ->
            txt_city.text = text
            Preference.saveSaleCityId?.let { id ->
                cityId = id.toString()
            }
            txt_city.setTextColor(Color.BLACK)
        }

        Preference.saveSaleRoom?.let { text ->
            edt_rooms.setText(text)
            edt_rooms.setTextColor(Color.BLACK)
        }

        Preference.saveSaleSpace?.let { text ->
            edt_meter.setText(text)
            edt_meter.setTextColor(Color.BLACK)
        }

        Preference.saveSalePrice?.let { text ->
            edt_price.setText(text)
            edt_price.setTextColor(Color.BLACK)
        }

        Preference.saveSaleUnitText?.let { text ->
            txt_typ_currency1.text = text
            Preference.saveSaleUnitId?.let { id ->
                idCurrency = id
            }
            txt_typ_currency1.setTextColor(Color.BLACK)
        }

        Preference.saveSalePhone?.let { text ->
            ed_phon_number1.setText(text)
            ed_phon_number1.setTextColor(Color.BLACK)
        }

        Preference.saveSaleDescription?.let { text ->
            edt_des.setText(text)
            edt_des.setTextColor(Color.BLACK)
        }

        Preference.saveSaleAboutTheNeigh?.let { text ->
            edt_neighborhood_sale.setText(text)
            edt_neighborhood_sale.setTextColor(Color.BLACK)
        }

        Preference.saveSaleImage1?.let { text ->
            Glide.with(this)
                    .load(text)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_first)
            Preference.saveSaleImagePath1?.let { path ->
                path1 = path
            }
        }

        Preference.saveSaleImage2?.let { text ->
            Glide.with(this)
                    .load(text)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_seconds)
            Preference.saveSaleImagePath2?.let { path ->
                path2 = path
            }
        }

        Preference.saveSaleImage3?.let { text ->
            Glide.with(this)
                    .load(text)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_three)
            Preference.saveSaleImagePath3?.let { path ->
                path3 = path
            }
        }

        Preference.saveSaleImage4?.let { text ->
            Glide.with(this)
                    .load(text)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_four)
            Preference.saveSaleImagePath4?.let { path ->
                path4 = path
            }
        }

        Preference.saveSaleImage5?.let { text ->
            Glide.with(this)
                    .load(text)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_five_sale)
            Preference.saveSaleImagePath5?.let { path ->
                path5 = path
            }
        }

        Preference.saveSaleImage6?.let { text ->
            Glide.with(this)
                    .load(text)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_six_sale)
            Preference.saveSaleImagePath6?.let { path ->
                path6 = path
            }
        }

        Preference.saveSaleImage7?.let { text ->
            Glide.with(this)
                    .load(text)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_seven_sale)
            Preference.saveSaleImagePath7?.let { path ->
                path7 = path
            }
        }

        Preference.saveSaleImage8?.let { text ->
            Glide.with(this)
                    .load(text)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_eight_sale)
            Preference.saveSaleImagePath8?.let { path ->
                path8 = path
            }
        }

        Preference.saveSaleImage9?.let { text ->
            Glide.with(this)
                    .load(text)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_nine_sale)
            Preference.saveSaleImagePath9?.let { path ->
                path9 = path
            }
        }

        Preference.saveSaleImage10?.let { text ->
            Glide.with(this)
                    .load(text)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_ten_sale)
            Preference.saveSaleImagePath10?.let { path ->
                path10 = path
            }
        }

        Preference.saveSaleImage11?.let { text ->
            Glide.with(this)
                    .load(text)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_eleven_sale)
            Preference.saveSaleImagePath11?.let { path ->
                path11 = path
            }
        }

        Preference.saveSaleImage12?.let { text ->
            Glide.with(this)
                    .load(text)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_twelve_sale)
            Preference.saveSaleImagePath12?.let { path ->
                path12 = path
            }
        }
    }

    fun setEditValue() {


        edt_rooms.setText(modelEdit?.rooms.toString())
        edt_meter.setText(modelEdit?.area.toString())
        edt_price.setText(modelEdit?.price.toString())
        edt_des.setText(modelEdit?.description)
        txt_home_type.setText(modelEdit?.homeType)
        txt_country.setText(modelEdit?.location?.country)

        txt_city.setText(if (modelEdit?.location?.city != null) modelEdit?.location?.city else resources.getString(R.string.city))

        txt_typ_currency1.setText(if (modelEdit?.currency?.data?.name != "") modelEdit?.currency?.data?.name else resources.getString(R.string.currency))
        ed_phon_number1.setText(modelEdit?.phone)

        txt_home_type.textColor = Color.BLACK
        txt_country.textColor = Color.BLACK
        txt_city.textColor = Color.BLACK

        homeType = if (modelEdit?.homeTypeId != null) modelEdit?.homeTypeId!! else 0
        adversType = if (modelEdit?.adTypeId != null) modelEdit?.adTypeId!! else 0
        idCurrency = if (modelEdit?.currency?.data != null) modelEdit?.currency?.data!!.id!! else 0
        cityId = modelEdit?.location_id.toString()
        plateCountryCode = modelEdit?.country_id.toString()
        edt_neighborhood_sale.setText(modelEdit?.neighborhood)

        btn_submit.text = resources.getString(R.string.update)

        if (modelEdit?.images?.size ?: 0 > 0) {
            Glide.with(this)
                    .load(modelEdit?.images?.get(0)?.path)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_first)
        }

        if (modelEdit?.images?.size ?: 0 > 1) {

            Glide.with(this)
                    .load(modelEdit?.images?.get(1)?.path)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_seconds)
        }

        if (modelEdit?.images?.size ?: 0 > 2) {

            Glide.with(this)
                    .load(modelEdit?.images?.get(2)?.path)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_three)
        }
        if (modelEdit?.images?.size ?: 0 > 3) {

            Glide.with(this)
                    .load(modelEdit?.images?.get(3)?.path)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_four)
        }


        if (modelEdit?.images?.size ?: 0 > 4) {

            Glide.with(this)
                    .load(modelEdit?.images?.get(4)?.path)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_five_sale)
        }

        if (modelEdit?.images?.size ?: 0 > 5) {

            Glide.with(this)
                    .load(modelEdit?.images?.get(5)?.path)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_six_sale)
        }

        if (modelEdit?.images?.size ?: 0 > 6) {

            Glide.with(this)
                    .load(modelEdit?.images?.get(6)?.path)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_seven_sale)
        }

        if (modelEdit?.images?.size ?: 0 > 7) {

            Glide.with(this)
                    .load(modelEdit?.images?.get(7)?.path)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_eight_sale)
        }



        if (modelEdit?.neighborhood_images?.size ?: 0 > 0) {

            checkTypeImage(modelEdit?.neighborhood_images?.get(0)?.type)?.let {
                Glide.with(this)
                        .load(modelEdit?.neighborhood_images?.get(0)?.path)
                        .placeholder(R.drawable.camera)
                        .error(R.drawable.camera)
                        .into(it)
            }
        }
        if (modelEdit?.neighborhood_images?.size ?: 0 > 1) {

            checkTypeImage(modelEdit?.neighborhood_images?.get(1)?.type)?.let {
                Glide.with(this)
                        .load(modelEdit?.neighborhood_images?.get(1)?.path)
                        .placeholder(R.drawable.camera)
                        .error(R.drawable.camera)
                        .into(it)
            }
        }
        if (modelEdit?.neighborhood_images?.size ?: 0 > 2) {

            checkTypeImage(modelEdit?.neighborhood_images?.get(2)?.type)?.let {
                Glide.with(this)
                        .load(modelEdit?.neighborhood_images?.get(2)?.path)
                        .placeholder(R.drawable.camera)
                        .error(R.drawable.camera)
                        .into(it)
            }
        }
        if (modelEdit?.neighborhood_images?.size ?: 0 > 3) {

            checkTypeImage(modelEdit?.neighborhood_images?.get(3)?.type)?.let {
                Glide.with(this)
                        .load(modelEdit?.neighborhood_images?.get(3)?.path)
                        .placeholder(R.drawable.camera)
                        .error(R.drawable.camera)
                        .into(it)
            }
        }
    }


    fun checkTypeImage(type: String?): ImageView? {


        if (type.equals("MAIN_STREET")) {
            return image_nine_sale
        } else if (type.equals("NEAREST_SCHOOL")) {
            return image_ten_sale
        } else if (type.equals("NEAREST_MARKETS")) {
            return image_eleven_sale
        } else if (type.equals("SPORT_CLUB")) {
            return image_twelve_sale
        }

        return null
    }


    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.image_first -> {
                itemClick = 1
                checkPermission(0)
            }

            R.id.image_seconds -> {
                itemClick = 2
                checkPermission(0)
            }

            R.id.image_three -> {
                itemClick = 3
                checkPermission(0)
            }

            R.id.image_four -> {
                itemClick = 4
                checkPermission(0)
            }
            R.id.image_five_sale -> {
                itemClick = 5
                checkPermission(0)

            }
            R.id.image_six_sale -> {
                itemClick = 6
                checkPermission(0)
            }

            R.id.image_seven_sale -> {
                itemClick = 7
                checkPermission(0)
            }

            R.id.image_eight_sale -> {
                itemClick = 8
                checkPermission(0)
            }

            R.id.image_nine_sale -> {
                itemClick = 9
                checkPermission(1)
            }

            R.id.image_ten_sale -> {
                itemClick = 10
                checkPermission(1)
            }

            R.id.image_eleven_sale -> {
                itemClick = 11
                checkPermission(1)
            }

            R.id.image_twelve_sale -> {
                itemClick = 12
                checkPermission(1)
            }

            R.id.txt_home_type -> {
                getHomeType()
            }


            R.id.btn_submit -> {
                if (modelEdit == null) {
                    AddHome()
                } else {
                    updateHome()
                }

            }

            R.id.txt_country -> {

                val intent = Intent(this, CountryList::class.java)
                startActivityForResult(intent, COUNTRY)
            }


            R.id.txt_city -> {

                if (!plateCountryCode.equals("0")) {
                    val intent = Intent(this, ActivityCity::class.java)
                    intent.putExtra("params", plateCountryCode)
                    startActivityForResult(intent, CITY)

                } else {
                    toast(resources.getString(R.string.place_first_select_state))
                }
            }


            R.id.txt_typ_currency1 -> {
                getCurrency()
            }

            R.id.btnBack -> {
                finish()
            }

        }

    }

    fun AddHome() {

       showLoading()
        val rooms = edt_rooms.text
        val arae = edt_meter.text
        val des = edt_des.text
        val neighborhood = edt_neighborhood_sale.text
        val b = check(edt_rooms, edt_meter)

        if (b) {
            hideLoading()
            return
        }

        if (homeType == 0) {
            hideLoading()
            toast(resources.getString(R.string.select_homeType))
            return
        }

        if (cityId.equals("0")) {
            hideLoading()
            toast(resources.getString(R.string.city))
            return
        }

//        Log.e("TYTY", path1 +"//"+ path2 +"//"+ path3 +"//"+ path4 +"//"+ path5 +"//"+ path6 +"//"+ path7 +"//"+ path8 +"//"+ path9 +"//"+ path10 +"//"+ path11 +"//"+ path12)
        val PhonNumber = ed_phon_number1.text
        Repository().getInstance()?.addHome(homeType, 2, cityId, 0.0, 0.0,
                rooms.toString(), arae.toString(), edt_price.text.toString(),
                "0", "0", des.toString(), PhonNumber.toString(), "" + idCurrency, neighborhood.toString(),
                path1, path2, path3, path4, path5, path6, path7, path8, path9, path10, path11, path12, object : CallBack<Api<ModelAddHome>>() {


            override fun onSuccess(t: Api<ModelAddHome>) {
                super.onSuccess(t)
                hideLoading()
                if (t.status?.code == 200) {
                    toast(t.status?.message.toString())
                    Preference.clearPrefSale()
                    finish()
                }
            }

            override fun onFail(e: Exception, code: Int) {
                super.onFail(e, code)
                hideLoading()
                if (code == 422) {
                    val g = Gson()
                    val b = g.fromJson(e.message.toString(), Api::class.java)
                    b.status?.message?.let { toast(it) }

                } else {
                    toast(R.string.try_agin)

                }
            }
        })

    }


    fun updateHome() {

        showLoading()
        val rooms = edt_rooms.text
        val arae = edt_meter.text
        val des = edt_des.text
        val b = check(edt_rooms, edt_meter, edt_price)
        val neighborhood = edt_neighborhood_sale.text
        if (b) {
            hideLoading()
            return
        }

        Repository().getInstance()?.updateHome(modelEdit?.id!!, homeType, 2, cityId.toString(), 0.0, 0.0,
                rooms.toString(), arae.toString(), edt_price.text.toString(),
                "0", "0", des.toString(),
                ed_phon_number1.text.toString(), "" + idCurrency, neighborhood.toString(),
                path1, path2, path3, path4, path5, path6, path7, path8, path9, path10, path11, path12, object : CallBack<Api<List<Void>>>() {


            override fun onSuccess(t: Api<List<Void>>) {
                super.onSuccess(t)
                hideLoading()
                if (t.status?.code == 200) {
                    t.status?.message?.let { toast(it) }
                    finish()
                }
            }

            override fun onFail(e: Exception, code: Int) {
                super.onFail(e, code)
                hideLoading()
                if (code == 422) {
                    val g = Gson()
                    val b = g.fromJson(e.message.toString(), Api::class.java)
                    b.status?.message?.let { toast(it) }

                } else {
                    toast(R.string.try_agin)

                }
            }
        })
    }


    fun getHomeType() {

        showLoading()
        val list = arrayListOf<String>()
        Repository().getInstance()?.getHomeType(Preference.language!!, object : CallBack<Api<List<ModelHomeType>>>() {

            override fun onSuccess(t: Api<List<ModelHomeType>>) {
                super.onSuccess(t)
                hideLoading()
                list.clear()
                t.data?.forEach {
                    it
                    list.add(it.title.toString())
                }

                selector(resources.getString(R.string.select_homeType), list) { dialogInterface, i ->
                    homeType = t.data!![i].id
                    txt_home_type.text = t.data!![i].title
                    txt_home_type.setTextColor(Color.BLACK)
                    Preference.saveSaleHomeTypeId = t.data!![i].id
                    Preference.saveSaleHomeTypeText = t.data!![i].title

                }

            }

            override fun onFail(e: Exception, code: Int) {
                super.onFail(e, code)
                hideLoading()
                toast(R.string.try_agin)

            }

        })


    }

    fun checkPermission(tag: Int) {

        Dexter.withActivity(this)
                .withPermissions(
                        permission.READ_EXTERNAL_STORAGE,
                        permission.WRITE_EXTERNAL_STORAGE,
                        permission.CAMERA
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        showPictureDialog(tag)
                    }

                    override fun onPermissionRationaleShouldBeShown(
                            permissions: List<PermissionRequest>,
                            token: PermissionToken
                    ) {
                        token.continuePermissionRequest()
                    }
                }).check()

    }

    fun getCurrency() {

        showLoading()
        val list = arrayListOf<String>()
        Repository().getInstance()?.getCurrencies(object : CallBack<Api<List<ModelCurrency>>>() {

            override fun onSuccess(t: Api<List<ModelCurrency>>) {
                super.onSuccess(t)
                hideLoading()
                list.clear()
                t.data?.forEach {
                    it
                    list.add(it.name.toString())
                }

                selector("", list) { dialogInterface, i ->
                    idCurrency = t.data?.get(i)!!.id
                    txt_typ_currency1.text = t.data?.get(i)!!.name
                    txt_typ_currency1.setTextColor(Color.BLACK)
                    Preference.saveSaleUnitText = t.data?.get(i)!!.name
                    Preference.saveSaleUnitId = t.data?.get(i)!!.id

                }

            }

            override fun onFail(e: Exception, code: Int) {
                super.onFail(e, code)
                hideLoading()
            }

        })
    }

    private fun showPictureDialog(tag: Int) {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle(resources.getString(R.string.select_action))
        var pictureDialogItems = arrayOf<String>()
        if (tag == 0) {
            pictureDialogItems = arrayOf(resources.getString(R.string.select_gallery), resources.getString(R.string.select_multiple_image), resources.getString(R.string.select_camera))
            pictureDialog.setItems(pictureDialogItems
            ) { dialog, which ->
                when (which) {
                    0 -> choosePhotoFromGallary()
                    1 -> choosePhotoFromGallaryMultiple()
                    2 -> takePhotoFromCamera()
                }
            }
        } else {
            pictureDialogItems = arrayOf(resources.getString(R.string.select_gallery), resources.getString(R.string.select_camera))
            pictureDialog.setItems(pictureDialogItems
            ) { dialog, which ->
                when (which) {
                    0 -> choosePhotoFromGallary()
                    1 -> takePhotoFromCamera()
                }
            }
        }
        pictureDialog.show()
    }

    fun choosePhotoFromGallary() {
//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(Intent.createChooser(intent, resources.getString(R.string.complete_action_using)), GALLERY)

        Album.image(this) // Image selection.
                .multipleChoice()
                .camera(true)
                .columnCount(4)
                .selectCount(1)
                .checkedList(imagesUris)
                .onResult {
                    val file = File(it[0].path)
                    val uri = Uri.fromFile(file)
                    if (itemClick == 1) {
                        path1 = it[0].path
                        Preference.saveSaleImage1 = uri.toString()
                        Preference.saveSaleImagePath1 = path1
                        img_close_sale_one.visibility = View.VISIBLE
//                            image_first.setImageURI(uri)
                        Glide.with(this)
                                .load(uri)
                                .placeholder(R.drawable.camera)
                                .error(R.drawable.camera)
                                .into(image_first)

                    } else if (itemClick == 2) {

                        path2 = it[0].path
                        Preference.saveSaleImage2 = uri.toString()
                        Preference.saveSaleImagePath2 = path2
                        img_close_sale_two.visibility = View.VISIBLE
//                            image_seconds.setImageURI(uri)
                        Glide.with(this)
                                .load(uri)
                                .placeholder(R.drawable.camera)
                                .error(R.drawable.camera)
                                .into(image_seconds)
                    } else if (itemClick == 3) {

                        path3 = it[0].path
                        Preference.saveSaleImage3 = uri.toString()
                        Preference.saveSaleImagePath3 = path3
                        img_close_sale_three.visibility = View.VISIBLE
//                            image_three.setImageURI(uri)
                        Glide.with(this)
                                .load(uri)
                                .placeholder(R.drawable.camera)
                                .error(R.drawable.camera)
                                .into(image_three)
                    } else if (itemClick == 4) {

                        path4 = it[0].path
                        Preference.saveSaleImage4 = uri.toString()
                        Preference.saveSaleImagePath4 = path4
                        img_close_sale_four.visibility = View.VISIBLE
//                            image_four.setImageURI(uri)
                        Glide.with(this)
                                .load(uri)
                                .placeholder(R.drawable.camera)
                                .error(R.drawable.camera)
                                .into(image_four)
                    } else if (itemClick == 5) {

                        path5 = it[0].path
                        Preference.saveSaleImage5 = uri.toString()
                        Preference.saveSaleImagePath5 = path5
                        img_close_sale_five.visibility = View.VISIBLE
//                            image_five_sale.setImageURI(uri)
                        Glide.with(this)
                                .load(uri)
                                .placeholder(R.drawable.camera)
                                .error(R.drawable.camera)
                                .into(image_five_sale)
                    } else if (itemClick == 6) {

                        path6 = it[0].path
                        Preference.saveSaleImage6 = uri.toString()
                        Preference.saveSaleImagePath6 = path6
                        img_close_sale_six.visibility = View.VISIBLE
//                            image_six_sale.setImageURI(uri)
                        Glide.with(this)
                                .load(uri)
                                .placeholder(R.drawable.camera)
                                .error(R.drawable.camera)
                                .into(image_six_sale)
                    } else if (itemClick == 7) {
                        path7 = it[0].path
                        Preference.saveSaleImage7 = uri.toString()
                        Preference.saveSaleImagePath7 = path7
                        img_close_sale_seven.visibility = View.VISIBLE
//                            image_seven_sale.setImageURI(uri)
                        Glide.with(this)
                                .load(uri)
                                .placeholder(R.drawable.camera)
                                .error(R.drawable.camera)
                                .into(image_seven_sale)
                    } else if (itemClick == 8) {
                        path8 = it[0].path
                        Preference.saveSaleImage8 = uri.toString()
                        Preference.saveSaleImagePath8 = path8
                        img_close_sale_eight.visibility = View.VISIBLE
//                            image_eight_sale.setImageURI(uri)
                        Glide.with(this)
                                .load(uri)
                                .placeholder(R.drawable.camera)
                                .error(R.drawable.camera)
                                .into(image_eight_sale)
                    } else if (itemClick == 9) {
                        path9 = it[0].path
                        Preference.saveSaleImage9 = uri.toString()
                        Preference.saveSaleImagePath9 = path9
                        img_close_sale_nine.visibility = View.VISIBLE
//                            image_nine_sale.setImageURI(uri)
                        Glide.with(this)
                                .load(uri)
                                .placeholder(R.drawable.camera)
                                .error(R.drawable.camera)
                                .into(image_nine_sale)
                    } else if (itemClick == 10) {
                        path10 = it[0].path
                        Preference.saveSaleImage10 = uri.toString()
                        Preference.saveSaleImagePath10 = path10
                        img_close_sale_ten.visibility = View.VISIBLE
//                            image_ten_sale.setImageURI(uri)
                        Glide.with(this)
                                .load(uri)
                                .placeholder(R.drawable.camera)
                                .error(R.drawable.camera)
                                .into(image_ten_sale)
                    } else if (itemClick == 11) {
                        path11 = it[0].path
                        Preference.saveSaleImage11 = uri.toString()
                        Preference.saveSaleImagePath11 = path11
                        img_close_sale_eleven.visibility = View.VISIBLE
//                            image_eleven_sale.setImageURI(uri)
                        Glide.with(this)
                                .load(uri)
                                .placeholder(R.drawable.camera)
                                .error(R.drawable.camera)
                                .into(image_eleven_sale)
                    } else if (itemClick == 12) {
                        path12 = it[0].path
                        Preference.saveSaleImage12 = uri.toString()
                        Preference.saveSaleImagePath12 = path12
                        img_close_sale_twelve.visibility = View.VISIBLE
//                            image_twelve_sale.setImageURI(uri)
                        Glide.with(this)
                                .load(uri)
                                .placeholder(R.drawable.camera)
                                .error(R.drawable.camera)
                                .into(image_twelve_sale)
                    }
                }
                .onCancel {

                }
                .start()

    }


    fun choosePhotoFromGallaryMultiple() {
        Album.image(this) // Image selection.
                .multipleChoice()
                .camera(true)
                .columnCount(4)
                .selectCount(8)
                .checkedList(imagesUris)
                .onResult {
                    it.forEachIndexed { index, path ->
                        when (index) {
                            0 -> {
                                val file = File(path.path)
                                val uri = Uri.fromFile(file)
//                                image_first.setImageURI(uri)
                                img_close_sale_one.visibility = View.VISIBLE
                                Glide.with(this)
                                        .load(uri)
                                        .placeholder(R.drawable.camera)
                                        .error(R.drawable.camera)
                                        .into(image_first)
                                path1 = path.path
                                Preference.saveSaleImagePath1 = path1
                                Preference.saveSaleImage1 = uri.toString()

                            }
                            1 -> {
                                val file = File(path.path)
                                val uri = Uri.fromFile(file)
                                img_close_sale_two.visibility = View.VISIBLE
//                                image_seconds.setImageURI(uri)
                                Glide.with(this)
                                        .load(uri)
                                        .placeholder(R.drawable.camera)
                                        .error(R.drawable.camera)
                                        .into(image_seconds)
                                path2 = path.path
                                Preference.saveSaleImagePath2 = path2
                                Preference.saveSaleImage2 = uri.toString()

                            }
                            2 -> {
                                val file = File(path.path)
                                val uri = Uri.fromFile(file)
                                img_close_sale_three.visibility = View.VISIBLE
//                                image_three.setImageURI(uri)
                                Glide.with(this)
                                        .load(uri)
                                        .placeholder(R.drawable.camera)
                                        .error(R.drawable.camera)
                                        .into(image_three)
                                path3 = path.path
                                Preference.saveSaleImagePath3 = path3
                                Preference.saveSaleImage3 = uri.toString()
                            }
                            3 -> {
                                val file = File(path.path)
                                val uri = Uri.fromFile(file)
                                img_close_sale_four.visibility = View.VISIBLE
//                                image_four.setImageURI(uri)
                                Glide.with(this)
                                        .load(uri)
                                        .placeholder(R.drawable.camera)
                                        .error(R.drawable.camera)
                                        .into(image_four)
                                path4 = path.path
                                Preference.saveSaleImagePath4 = path4
                                Preference.saveSaleImage4 = uri.toString()
                            }
                            4 -> {
                                val file = File(path.path)
                                val uri = Uri.fromFile(file)
//                                image_five_sale.setImageURI(uri)
                                img_close_sale_five.visibility = View.VISIBLE
                                Glide.with(this)
                                        .load(uri)
                                        .placeholder(R.drawable.camera)
                                        .error(R.drawable.camera)
                                        .into(image_five_sale)
                                path5 = path.path
                                Preference.saveSaleImagePath5 = path5
                                Preference.saveSaleImage5 = uri.toString()
                            }
                            5 -> {
                                val file = File(path.path)
                                val uri = Uri.fromFile(file)
                                img_close_sale_six.visibility = View.VISIBLE
//                                image_six_sale.setImageURI(uri)
                                Glide.with(this)
                                        .load(uri)
                                        .placeholder(R.drawable.camera)
                                        .error(R.drawable.camera)
                                        .into(image_six_sale)
                                path6 = path.path
                                Preference.saveSaleImagePath6 = path6
                                Preference.saveSaleImage6 = uri.toString()
                            }
                            6 -> {
                                val file = File(path.path)
                                val uri = Uri.fromFile(file)
                                img_close_sale_seven.visibility = View.VISIBLE
//                                image_seven_sale.setImageURI(uri)
                                Glide.with(this)
                                        .load(uri)
                                        .placeholder(R.drawable.camera)
                                        .error(R.drawable.camera)
                                        .into(image_seven_sale)
                                path7 = path.path
                                Preference.saveSaleImagePath7 = path7
                                Preference.saveSaleImage7 = uri.toString()

                            }
                            7 -> {
                                val file = File(path.path)
                                val uri = Uri.fromFile(file)
                                img_close_sale_eight.visibility = View.VISIBLE
//                                image_eight_sale.setImageURI(uri)
                                Glide.with(this)
                                        .load(uri)
                                        .placeholder(R.drawable.camera)
                                        .error(R.drawable.camera)
                                        .into(image_eight_sale)
                                path8 = path.path
                                Preference.saveSaleImagePath8 = path8
                                Preference.saveSaleImage8 = uri.toString()
                            }
                            else -> return@forEachIndexed
                        }
                    }
                }
                .onCancel {

                }
                .start()



    }


    private fun takePhotoFromCamera() {

        val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file: File = createFile()
        val uri: Uri = FileProvider.getUriForFile(
                this,
                "com.alaan.outn.provider",
                file)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(intent, CAMERA)
    }

    @SuppressLint("NewApi")
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                    val result = CropImage.getActivityResult(data)
                    val path = result.uri.path

                    if (path != null) {
                        val file = File(path)
                        val uri = Uri.fromFile(file)
                        if (itemClick == 1) {

                            path1 = path
                            img_close_sale_one.visibility = View.VISIBLE
                            image_first.setImageURI(uri)

                        } else if (itemClick == 2) {

                            path2 = path
                            img_close_sale_two.visibility = View.VISIBLE
                            image_seconds.setImageURI(uri)

                        } else if (itemClick == 3) {

                            path3 = path
                            img_close_sale_three.visibility = View.VISIBLE
                            image_three.setImageURI(uri)

                        } else if (itemClick == 4) {

                            path4 = path
                            img_close_sale_four.visibility = View.VISIBLE
                            image_four.setImageURI(uri)
                        } else if (itemClick == 5) {

                            path5 = path
                            img_close_sale_five.visibility = View.VISIBLE
                            image_five_sale.setImageURI(uri)
                        } else if (itemClick == 6) {

                            path6 = path
                            img_close_sale_six.visibility = View.VISIBLE
                            image_six_sale.setImageURI(uri)
                        } else if (itemClick == 7) {

                            path7 = path
                            img_close_sale_seven.visibility = View.VISIBLE
                            image_seven_sale.setImageURI(uri)
                        } else if (itemClick == 8) {
                            path8 = path
                            img_close_sale_eight.visibility = View.VISIBLE
                            image_eight_sale.setImageURI(uri)
                        } else if (itemClick == 9) {
                            path9 = path
                            img_close_sale_nine.visibility = View.VISIBLE
                            image_nine_sale.setImageURI(uri)
                        } else if (itemClick == 10) {
                            path10 = path
                            img_close_sale_ten.visibility = View.VISIBLE
                            image_ten_sale.setImageURI(uri)
                        } else if (itemClick == 11) {
                            path11 = path
                            img_close_sale_eleven.visibility = View.VISIBLE
                            image_eleven_sale.setImageURI(uri)
                        } else if (itemClick == 12) {
                            path12 = path
                            img_close_sale_twelve.visibility = View.VISIBLE
                            image_twelve_sale.setImageURI(uri)
                        }

                    }

                }
                GALLERY -> {
                    val path = data!!.data?.let {
                        Utils.getPath(it)
                    }
                    if (path != null) {
                        val file = File(path)
                        val uri = Uri.fromFile(file)
                        if (itemClick == 1) {
                            path1 = path
                            Preference.saveSaleImage1 = uri.toString()
                            Preference.saveSaleImagePath1 = path1
                            img_close_sale_one.visibility = View.VISIBLE
//                            image_first.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_first)

                        } else if (itemClick == 2) {

                            path2 = path
                            Preference.saveSaleImage2 = uri.toString()
                            Preference.saveSaleImagePath2 = path2
                            img_close_sale_two.visibility = View.VISIBLE
//                            image_seconds.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_seconds)
                        } else if (itemClick == 3) {

                            path3 = path
                            Preference.saveSaleImage3 = uri.toString()
                            Preference.saveSaleImagePath3 = path3
                            img_close_sale_three.visibility = View.VISIBLE
//                            image_three.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_three)
                        } else if (itemClick == 4) {

                            path4 = path
                            Preference.saveSaleImage4 = uri.toString()
                            Preference.saveSaleImagePath4 = path4
                            img_close_sale_four.visibility = View.VISIBLE
//                            image_four.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_four)
                        } else if (itemClick == 5) {

                            path5 = path
                            Preference.saveSaleImage5 = uri.toString()
                            Preference.saveSaleImagePath5 = path5
                            img_close_sale_five.visibility = View.VISIBLE
//                            image_five_sale.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_five_sale)
                        } else if (itemClick == 6) {

                            path6 = path
                            Preference.saveSaleImage6 = uri.toString()
                            Preference.saveSaleImagePath6 = path6
                            img_close_sale_six.visibility = View.VISIBLE
//                            image_six_sale.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_six_sale)
                        } else if (itemClick == 7) {
                            path7 = path
                            Preference.saveSaleImage7 = uri.toString()
                            Preference.saveSaleImagePath7 = path7
                            img_close_sale_seven.visibility = View.VISIBLE
//                            image_seven_sale.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_seven_sale)
                        } else if (itemClick == 8) {
                            path8 = path
                            Preference.saveSaleImage8 = uri.toString()
                            Preference.saveSaleImagePath8 = path8
                            img_close_sale_eight.visibility = View.VISIBLE
//                            image_eight_sale.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_eight_sale)
                        } else if (itemClick == 9) {
                            path9 = path
                            Preference.saveSaleImage9 = uri.toString()
                            Preference.saveSaleImagePath9 = path9
                            img_close_sale_nine.visibility = View.VISIBLE
//                            image_nine_sale.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_nine_sale)
                        } else if (itemClick == 10) {
                            path10 = path
                            Preference.saveSaleImage10 = uri.toString()
                            Preference.saveSaleImagePath10 = path10
                            img_close_sale_ten.visibility = View.VISIBLE
//                            image_ten_sale.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_ten_sale)
                        } else if (itemClick == 11) {
                            path11 = path
                            Preference.saveSaleImage11 = uri.toString()
                            Preference.saveSaleImagePath11 = path11
                            img_close_sale_eleven.visibility = View.VISIBLE
//                            image_eleven_sale.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_eleven_sale)
                        } else if (itemClick == 12) {
                            path12 = path
                            Preference.saveSaleImage12 = uri.toString()
                            Preference.saveSaleImagePath12 = path12
                            img_close_sale_twelve.visibility = View.VISIBLE
//                            image_twelve_sale.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_twelve_sale)
                        }

                    }
                }
                CAMERA -> {

                    val path = mCurrentPhotoPath

                    if (path != null) {
                        val file = File(path)
                        val uri = Uri.fromFile(file)
                        if (itemClick == 1) {
                            path1 = path
                            Preference.saveSaleImage1 = uri.toString()
                            Preference.saveSaleImagePath1 = path1
                            img_close_sale_one.visibility = View.VISIBLE
//                            image_first.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_first)
                        } else if (itemClick == 2) {

                            path2 = path
                            Preference.saveSaleImage2 = uri.toString()
                            Preference.saveSaleImagePath2 = path2
                            img_close_sale_two.visibility = View.VISIBLE
//                            image_seconds.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_seconds)
                        } else if (itemClick == 3) {

                            path3 = path
                            Preference.saveSaleImage3 = uri.toString()
                            Preference.saveSaleImagePath3 = path3
                            img_close_sale_three.visibility = View.VISIBLE
//                            image_three.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_three)
                        } else if (itemClick == 4) {

                            path4 = path
                            Preference.saveSaleImage4 = uri.toString()
                            Preference.saveSaleImagePath4 = path4
                            img_close_sale_four.visibility = View.VISIBLE
//                            image_four.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_four)
                        } else if (itemClick == 5) {

                            path5 = path
                            Preference.saveSaleImage5 = uri.toString()
                            Preference.saveSaleImagePath5 = path5
                            img_close_sale_five.visibility = View.VISIBLE
//                            image_five_sale.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_five_sale)
                        } else if (itemClick == 6) {

                            path6 = path
                            Preference.saveSaleImage6 = uri.toString()
                            Preference.saveSaleImagePath6 = path6
                            img_close_sale_six.visibility = View.VISIBLE
//                            image_six_sale.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_six_sale)
                        } else if (itemClick == 7) {
                            path7 = path
                            Preference.saveSaleImage7 = uri.toString()
                            Preference.saveSaleImagePath7 = path7
                            img_close_sale_seven.visibility = View.VISIBLE
//                            image_seven_sale.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_seven_sale)
                        } else if (itemClick == 8) {
                            path8 = path
                            Preference.saveSaleImage8 = uri.toString()
                            Preference.saveSaleImagePath8 = path8
//                            image_eight_sale.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_eight_sale)
                        } else if (itemClick == 9) {
                            path9 = path
                            Preference.saveSaleImage9 = uri.toString()
                            Preference.saveSaleImagePath9 = path9
                            img_close_sale_nine.visibility = View.VISIBLE
//                            image_nine_sale.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_nine_sale)
                        } else if (itemClick == 10) {
                            path10 = path
                            Preference.saveSaleImage10 = uri.toString()
                            Preference.saveSaleImagePath10 = path10
                            img_close_sale_ten.visibility = View.VISIBLE
//                            image_ten_sale.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_ten_sale)
                        } else if (itemClick == 11) {
                            path11 = path
                            Preference.saveSaleImage11 = uri.toString()
                            Preference.saveSaleImagePath11 = path11
                            img_close_sale_eleven.visibility = View.VISIBLE
//                            image_eleven_sale.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_eleven_sale)
                        } else if (itemClick == 12) {
                            path12 = path
                            Preference.saveSaleImage12 = uri.toString()
                            Preference.saveSaleImagePath12 = path12
                            img_close_sale_twelve.visibility = View.VISIBLE
//                            image_twelve_sale.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_twelve_sale)
                        }

                    }
                }

                COUNTRY -> {

                    if (data != null) {

                        plateCountryCode = data?.getIntExtra("id", 0).toString()
                        txt_country.setText(data?.getStringExtra("name"))
                        cityId = "0"
                        txt_country.setTextColor(Color.BLACK)
                        Preference.saveSaleCountryText = data?.getStringExtra("name")
                        Preference.saveSaleCountryId = data?.getIntExtra("id", 0)

                    }
                }


                CITY -> {


                    cityId = data?.getIntExtra("id", 0).toString()
                    txt_city.text = data?.getStringExtra("name")
                    txt_city.setTextColor(Color.BLACK)
                    Preference.saveSaleCityText = data?.getStringExtra("name")
                    Preference.saveSaleCityId = data?.getIntExtra("id", 0)

                }


            }
        }

    }


    private fun check(vararg editTexts: EditText): Boolean {
        for (editText in editTexts) {
            if (editText.text != null) {
                if (editText.text.length == 0) {
                    editText.error = Utils.getString(R.string.not_empty)
                    return true
                }
            }
        }
        return false
    }

    private fun checkImagePath(path1: String, path2: String, path3: String, path4: String, path5: String, path6: String, path7: String, path8: String, path9: String, path10: String, path11: String, path12: String){

        if (path1 == ""){
            img_close_sale_one.visibility = View.GONE
        }else{
            img_close_sale_one.visibility = View.VISIBLE
        }

        if (path2 == ""){
            img_close_sale_two.visibility = View.GONE
        }else{
            img_close_sale_two.visibility = View.VISIBLE
        }

        if (path3 == ""){
            img_close_sale_three.visibility = View.GONE
        }else{
            img_close_sale_three.visibility = View.VISIBLE
        }

        if (path4 == ""){
            img_close_sale_four.visibility = View.GONE
        }else{
            img_close_sale_four.visibility = View.VISIBLE
        }

        if (path5 == ""){
            img_close_sale_five.visibility = View.GONE
        }else{
            img_close_sale_five.visibility = View.VISIBLE
        }

        if (path6 == ""){
            img_close_sale_six.visibility = View.GONE
        }else{
            img_close_sale_six.visibility = View.VISIBLE
        }

        if (path7 == ""){
            img_close_sale_seven.visibility = View.GONE
        }else{
            img_close_sale_seven.visibility = View.VISIBLE
        }

        if (path8 == ""){
            img_close_sale_eight.visibility = View.GONE
        }else{
            img_close_sale_eight.visibility = View.VISIBLE
        }
        if (path9 == ""){
            img_close_sale_nine.visibility = View.GONE
        }else{
            img_close_sale_nine.visibility = View.VISIBLE
        }

        if (path10 == ""){
            img_close_sale_ten.visibility = View.GONE
        }else{
            img_close_sale_ten.visibility = View.VISIBLE
        }

        if (path11 == ""){
            img_close_sale_eleven.visibility = View.GONE
        }else{
            img_close_sale_eleven.visibility = View.VISIBLE
        }

        if (path12 == ""){
            img_close_sale_twelve.visibility = View.GONE
        }else{
            img_close_sale_twelve.visibility = View.VISIBLE
        }

    }
    var progressDialog: ProgressDialog? = null

    private fun showLoading() {
        progressDialog = ProgressDialog(this)
        progressDialog!!.setCancelable(false)
        progressDialog!!.setMessage(getString(R.string.pls_wait))
        progressDialog!!.show()
    }

    private fun hideLoading() {
        if (progressDialog != null) {
            if (progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
            }
        }
    }


    @Throws(IOException::class)
    private fun createFile(): File {

        val storageDir: File = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
                UUID.randomUUID().toString(), /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = absolutePath
        }
    }

}
