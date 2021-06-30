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
import kotlinx.android.synthetic.main.activity_rent.*
import kotlinx.android.synthetic.main.activity_rent.btnBack
import kotlinx.android.synthetic.main.activity_rent.btn_submit
import kotlinx.android.synthetic.main.activity_rent.edt_des
import kotlinx.android.synthetic.main.activity_rent.edt_meter
import kotlinx.android.synthetic.main.activity_rent.edt_rooms
import kotlinx.android.synthetic.main.activity_rent.txt_city
import kotlinx.android.synthetic.main.activity_rent.txt_country
import kotlinx.android.synthetic.main.activity_rent.txt_home_type
import kotlinx.android.synthetic.main.activity_rent.*
import kotlinx.android.synthetic.main.activity_rent.*
import kotlinx.android.synthetic.main.activity_rent.*
import kotlinx.android.synthetic.main.activity_sale.*
import org.jetbrains.anko.selector
import org.jetbrains.anko.textColor
import org.jetbrains.anko.toast
import java.io.File
import java.io.IOException
import java.util.*

class RentActivity : AppCompatActivity(), View.OnClickListener {
    private val listCity = mutableListOf<List<CitiesItem>>()
    private val listState = mutableListOf<List<StatesItem>>()

    private val listArae = mutableListOf<List<AreasItem>>()
    private var plateCountryCode: String? = "0"
    val GALLERY = 1
    val CAMERA = 2
    val GALLERY_MULTIPLE = 200
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
    var cityId = ""
    var idCurrency: Int = 0
    private var imagesUris = ArrayList<AlbumFile>()
    var modelEdit: ModelListHome? = null
    private var mCurrentPhotoPath: String? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.setFillWindowAndTransparetStatusBar(this)
        setContentView(R.layout.activity_rent)
        Utils.changeLanuge(this)
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

        txt_app.text = spannable

        image_first_rent.setOnClickListener(this)
        image_seconds_rent.setOnClickListener(this)
        image_three_rent.setOnClickListener(this)
        image_four_rent.setOnClickListener(this)
        image_five_rent.setOnClickListener(this)
        image_six_rent.setOnClickListener(this)
        image_seven_rent.setOnClickListener(this)
        image_eight_rent.setOnClickListener(this)
        image_nine_rent.setOnClickListener(this)
        image_ten_rent.setOnClickListener(this)
        image_eleven_rent.setOnClickListener(this)
        image_twelve_rent.setOnClickListener(this)



        txt_home_type.setOnClickListener(this)
        btn_submit.setOnClickListener(this)
        txt_country.setOnClickListener(this)
        btnBack.setOnClickListener(this)
        txt_city.setOnClickListener(this)
        txt_typ_currency.setOnClickListener(this)
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
        img_close_rent_one.setOnClickListener {
            path1 = ""
            Preference.saveRentImagePath1 = ""
            Preference.saveRentImage1 = ""
            Glide.with(this)
                    .load(R.drawable.camera)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .centerCrop()
                    .into(image_first_rent)
            img_close_rent_one.visibility = View.GONE
        }

        img_close_rent_two.setOnClickListener {
            path2 = ""
            Preference.saveRentImagePath2 = ""
            Preference.saveRentImage2 = ""
            Glide.with(this)
                    .load(R.drawable.camera)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .centerCrop()
                    .into(image_seconds_rent)
            img_close_rent_two.visibility = View.GONE
        }

        img_close_rent_three.setOnClickListener {
            path3 = ""
            Preference.saveRentImagePath3 = ""
            Preference.saveRentImage3 = ""
            Glide.with(this)
                    .load(R.drawable.camera)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_three_rent)
            img_close_rent_three.visibility = View.GONE
        }

        img_close_rent_four.setOnClickListener {
            path4 = ""
            Preference.saveRentImagePath4 = ""
            Preference.saveRentImage4 = ""
            Glide.with(this)
                    .load(R.drawable.camera)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_four_rent)
            img_close_rent_four.visibility = View.GONE
        }

        img_close_rent_five.setOnClickListener {
            path5 = ""
            Preference.saveRentImagePath5 = ""
            Preference.saveRentImage5 = ""
            Glide.with(this)
                    .load(R.drawable.camera)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_five_rent)
            img_close_rent_five.visibility = View.GONE
        }

        img_close_rent_six.setOnClickListener {
            path6 = ""
            Preference.saveRentImagePath6 = ""
            Preference.saveRentImage6 = ""
            Glide.with(this)
                    .load(R.drawable.camera)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_six_rent)
            img_close_rent_six.visibility = View.GONE
        }

        img_close_rent_seven.setOnClickListener {
            path7 = ""
            Preference.saveRentImagePath7 = ""
            Preference.saveRentImage7 = ""
            Glide.with(this)
                    .load(R.drawable.camera)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_seven_rent)
            img_close_rent_seven.visibility = View.GONE
        }

        img_close_rent_eight.setOnClickListener {
            path8 = ""
            Preference.saveRentImagePath8 = ""
            Preference.saveRentImage8 = ""
            Glide.with(this)
                    .load(R.drawable.camera)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_eight_rent)
            img_close_rent_eight.visibility = View.GONE
        }

        img_close_rent_nine.setOnClickListener {
            path9 = ""
            Preference.saveRentImagePath9 = ""
            Preference.saveRentImage9 = ""
            Glide.with(this)
                    .load(R.drawable.camera)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_nine_rent)
            img_close_rent_nine.visibility = View.GONE
        }

        img_close_rent_ten.setOnClickListener {
            path10 = ""
            Preference.saveRentImagePath10 = ""
            Preference.saveRentImage10 = ""
            Glide.with(this)
                    .load(R.drawable.camera)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_ten_rent)
            img_close_rent_ten.visibility = View.GONE
        }

        img_close_rent_eleven.setOnClickListener {
            path11 = ""
            Preference.saveRentImagePath11 = ""
            Preference.saveRentImage11 = ""
            Glide.with(this)
                    .load(R.drawable.camera)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_eleven_rent)
            img_close_rent_eleven.visibility = View.GONE
        }

        img_close_rent_twelve.setOnClickListener {
            path12 = ""
            Preference.saveRentImagePath12 = ""
            Preference.saveRentImage12 = ""
            Glide.with(this)
                    .load(R.drawable.camera)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_twelve_rent)
            img_close_rent_twelve.visibility = View.GONE
        }
    }

    private fun checkImagePath(path1: String, path2: String, path3: String, path4: String, path5: String, path6: String, path7: String, path8: String, path9: String, path10: String, path11: String, path12: String){

        if (path1 == ""){
            img_close_rent_one.visibility = View.GONE
        }else{
            img_close_rent_one.visibility = View.VISIBLE
        }

        if (path2 == ""){
            img_close_rent_two.visibility = View.GONE
        }else{
            img_close_rent_two.visibility = View.VISIBLE
        }

        if (path3 == ""){
            img_close_rent_three.visibility = View.GONE
        }else{
            img_close_rent_three.visibility = View.VISIBLE
        }

        if (path4 == ""){
            img_close_rent_four.visibility = View.GONE
        }else{
            img_close_rent_four.visibility = View.VISIBLE
        }

        if (path5 == ""){
            img_close_rent_five.visibility = View.GONE
        }else{
            img_close_rent_five.visibility = View.VISIBLE
        }

        if (path6 == ""){
            img_close_rent_six.visibility = View.GONE
        }else{
            img_close_rent_six.visibility = View.VISIBLE
        }

        if (path7 == ""){
            img_close_rent_seven.visibility = View.GONE
        }else{
            img_close_rent_seven.visibility = View.VISIBLE
        }

        if (path8 == ""){
            img_close_rent_eight.visibility = View.GONE
        }else{
            img_close_rent_eight.visibility = View.VISIBLE
        }
        if (path9 == ""){
            img_close_rent_nine.visibility = View.GONE
        }else{
            img_close_rent_nine.visibility = View.VISIBLE
        }

        if (path10 == ""){
            img_close_rent_ten.visibility = View.GONE
        }else{
            img_close_rent_ten.visibility = View.VISIBLE
        }

        if (path11 == ""){
            img_close_rent_eleven.visibility = View.GONE
        }else{
            img_close_rent_eleven.visibility = View.VISIBLE
        }

        if (path12 == ""){
            img_close_rent_twelve.visibility = View.GONE
        }else{
            img_close_rent_twelve.visibility = View.VISIBLE
        }

    }


    private fun manageEditTextsPrefValues() {
        edt_rooms.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Preference.saveRentRoom = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        edt_meter.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Preference.saveRentSpace = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        /* edt_price.addTextChangedListener(object : TextWatcher {
             override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

             }

             override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                 Preference.saveRentPrice = s.toString()
             }

             override fun afterTextChanged(s: Editable?) {
             }
         })
 */
        ed_phon_number.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Preference.saveRentPhone = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        edt_des.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Preference.saveRentDescription = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        edt_neighborhood.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Preference.saveRentAboutTheNeigh = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        edt_down_payment.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Preference.saveRentYearly = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        edt_monthly_payment.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Preference.saveRentMonthly = s.toString()
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
        Preference.saveRentHomeTypeText?.let { text ->
            txt_home_type.text = text
            Preference.saveRentHomeTypeId?.let { id ->
                homeType = id
            }
            txt_home_type.setTextColor(Color.BLACK)
        }

        Preference.saveRentCountryText?.let { text ->
            txt_country.text = text
            Preference.saveRentCountryId?.let { id ->
                plateCountryCode = id.toString()
            }
            txt_country.setTextColor(Color.BLACK)
        }

        Preference.saveRentCityText?.let { text ->
            txt_city.text = text
            Preference.saveRentCityId?.let { id ->
                cityId = id.toString()
            }
            txt_city.setTextColor(Color.BLACK)
        }

        Preference.saveRentRoom?.let { text ->
            edt_rooms.setText(text)
            edt_rooms.setTextColor(Color.BLACK)
        }

        Preference.saveRentSpace?.let { text ->
            edt_meter.setText(text)
            edt_meter.setTextColor(Color.BLACK)
        }

        Preference.saveRentYearly?.let { text ->
            edt_down_payment.setText(text)
            edt_down_payment.setTextColor(Color.BLACK)
        }

        Preference.saveRentMonthly?.let { text ->
            edt_monthly_payment.setText(text)
            edt_monthly_payment.setTextColor(Color.BLACK)
        }

        /*   Preference.saveRentPrice?.let { text ->
               edt_price.setText(text)
               edt_price.setTextColor(Color.BLACK)
           }*/

        Preference.saveRentUnitText?.let { text ->
            txt_typ_currency.text = text
            Preference.saveRentUnitId?.let { id ->
                idCurrency = id
            }
            txt_typ_currency.setTextColor(Color.BLACK)
        }

        Preference.saveRentPhone?.let { text ->
            ed_phon_number.setText(text)
            ed_phon_number.setTextColor(Color.BLACK)
        }

        Preference.saveRentDescription?.let { text ->
            edt_des.setText(text)
            edt_des.setTextColor(Color.BLACK)
        }

        Preference.saveRentAboutTheNeigh?.let { text ->
            edt_neighborhood.setText(text)
            edt_neighborhood.setTextColor(Color.BLACK)
        }

        Preference.saveRentImage1?.let { text ->
            Glide.with(this)
                    .load(text)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_first_rent)
            Preference.saveRentImagePath1?.let { path ->
                path1 = path
            }
        }

        Preference.saveRentImage2?.let { text ->
            Glide.with(this)
                    .load(text)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_seconds_rent)
            Preference.saveRentImagePath2?.let { path ->
                path2 = path
            }
        }

        Preference.saveRentImage3?.let { text ->
            Glide.with(this)
                    .load(text)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_three_rent)
            Preference.saveRentImagePath3?.let { path ->
                path3 = path
            }
        }

        Preference.saveRentImage4?.let { text ->
            Glide.with(this)
                    .load(text)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_four_rent)
            Preference.saveRentImagePath4?.let { path ->
                path4 = path
            }
        }

        Preference.saveRentImage5?.let { text ->
            Glide.with(this)
                    .load(text)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_five_rent)
            Preference.saveRentImagePath5?.let { path ->
                path5 = path
            }
        }

        Preference.saveRentImage6?.let { text ->
            Glide.with(this)
                    .load(text)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_six_rent)
            Preference.saveRentImagePath6?.let { path ->
                path6 = path
            }
        }

        Preference.saveRentImage7?.let { text ->
            Glide.with(this)
                    .load(text)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_seven_rent)
            Preference.saveRentImagePath7?.let { path ->
                path7 = path
            }
        }

        Preference.saveRentImage8?.let { text ->
            Glide.with(this)
                    .load(text)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_eight_rent)
            Preference.saveRentImagePath8?.let { path ->
                path8 = path
            }
        }

        Preference.saveRentImage9?.let { text ->
            Glide.with(this)
                    .load(text)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_nine_rent)
            Preference.saveRentImagePath9?.let { path ->
                path9 = path
            }
        }

        Preference.saveRentImage10?.let { text ->
            Glide.with(this)
                    .load(text)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_ten_rent)
            Preference.saveRentImagePath10?.let { path ->
                path10 = path
            }
        }

        Preference.saveRentImage11?.let { text ->
            Glide.with(this)
                    .load(text)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_eleven_rent)
            Preference.saveRentImagePath11?.let { path ->
                path11 = path
            }
        }

        Preference.saveRentImage12?.let { text ->
            Glide.with(this)
                    .load(text)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_twelve_rent)
            Preference.saveRentImagePath12?.let { path ->
                path12 = path
            }
        }
    }

    fun setEditValue() {

        edt_rooms.setText(modelEdit?.rooms.toString())
        edt_meter.setText(modelEdit?.area.toString())
        edt_down_payment.setText(modelEdit?.downPayment)
        edt_monthly_payment.setText(modelEdit?.monthlyPayment)
        edt_des.setText(modelEdit?.description)
        txt_home_type.setText(modelEdit?.homeType)
        txt_country.setText(modelEdit?.location?.country)
        txt_city.setText(if (modelEdit?.location?.city != null) modelEdit?.location?.city else resources.getString(R.string.city))
        txt_typ_currency.setText(if (modelEdit?.currency?.data?.name != "") modelEdit?.currency?.data?.name else resources.getString(R.string.currency))
        ed_phon_number.setText(modelEdit?.phone)

        txt_home_type.textColor = Color.BLACK
        txt_country.textColor = Color.BLACK
        txt_city.textColor = Color.BLACK

        homeType = if (modelEdit?.homeTypeId != null) modelEdit?.homeTypeId!! else 0
        adversType = if (modelEdit?.adTypeId != null) modelEdit?.adTypeId!! else 0
        idCurrency = if (modelEdit?.currency?.data != null) modelEdit?.currency?.data!!.id!! else 0
        cityId = modelEdit?.location_id.toString()
        plateCountryCode = modelEdit?.country_id.toString()
        edt_neighborhood.setText(modelEdit?.neighborhood)

        btn_submit.text = resources.getString(R.string.update)

        if (modelEdit?.images?.size ?: 0 > 0) {
            Glide.with(this)
                    .load(modelEdit?.images?.get(0)?.path)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_first_rent)
        }

        if (modelEdit?.images?.size ?: 0 > 1) {

            Glide.with(this)
                    .load(modelEdit?.images?.get(1)?.path)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_seconds_rent)
        }

        if (modelEdit?.images?.size ?: 0 > 2) {

            Glide.with(this)
                    .load(modelEdit?.images?.get(2)?.path)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_three_rent)
        }
        if (modelEdit?.images?.size ?: 0 > 3) {

            Glide.with(this)
                    .load(modelEdit?.images?.get(3)?.path)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_four_rent)
        }

        if (modelEdit?.images?.size ?: 0 > 4) {

            Glide.with(this)
                    .load(modelEdit?.images?.get(4)?.path)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_five_rent)
        }

        if (modelEdit?.images?.size ?: 0 > 5) {

            Glide.with(this)
                    .load(modelEdit?.images?.get(5)?.path)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_six_rent)
        }

        if (modelEdit?.images?.size ?: 0 > 6) {

            Glide.with(this)
                    .load(modelEdit?.images?.get(6)?.path)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_seven_rent)
        }

        if (modelEdit?.images?.size ?: 0 > 7) {

            Glide.with(this)
                    .load(modelEdit?.images?.get(7)?.path)
                    .placeholder(R.drawable.camera)
                    .error(R.drawable.camera)
                    .into(image_eight_rent)
        }


        ///neighborhood_images

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
            return image_nine_rent
        } else if (type.equals("NEAREST_SCHOOL")) {
            return image_ten_rent
        } else if (type.equals("NEAREST_MARKETS")) {
            return image_eleven_rent
        } else if (type.equals("SPORT_CLUB")) {
            return image_twelve_rent
        }

        return null
    }


    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.image_first_rent -> {
                itemClick = 1
                checkPermission(0)
            }

            R.id.image_seconds_rent -> {
                itemClick = 2
                checkPermission(0)
            }

            R.id.image_three_rent -> {
                itemClick = 3
                checkPermission(0)
            }

            R.id.image_four_rent -> {
                itemClick = 4
                checkPermission(0)
            }

            R.id.image_five_rent -> {
                itemClick = 5
                checkPermission(0)

            }
            R.id.image_six_rent -> {
                itemClick = 6
                checkPermission(0)
            }

            R.id.image_seven_rent -> {
                itemClick = 7
                checkPermission(0)
            }

            R.id.image_eight_rent -> {
                itemClick = 8
                checkPermission(0)
            }

            R.id.image_nine_rent -> {
                itemClick = 9
                checkPermission(1)
            }

            R.id.image_ten_rent -> {
                itemClick = 10
                checkPermission(1)
            }

            R.id.image_eleven_rent -> {
                itemClick = 11
                checkPermission(1)
            }

            R.id.image_twelve_rent -> {
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

            R.id.txt_typ_currency -> {
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
        val down_payment = edt_down_payment.text
        val monthly_payment = edt_monthly_payment.text
        val des = edt_des.text
        val PhonNumber = ed_phon_number.text
        val neighborhood = edt_neighborhood.text

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
            toast(resources.getString(R.string.place_first_select_city))
            return
        }

        Repository().getInstance()?.addHome(homeType, 1, cityId, 0.0, 0.0,
                rooms.toString(), arae.toString(), "0",
                down_payment.toString(), monthly_payment.toString(), des.toString(), PhonNumber.toString(), "" + idCurrency, neighborhood.toString(),
                path1, path2, path3, path4, path5, path6, path7, path8, path9, path10, path11, path12, object : CallBack<Api<ModelAddHome>>() {


            override fun onSuccess(t: Api<ModelAddHome>) {
                super.onSuccess(t)
                hideLoading()
                if (t.status?.code == 200) {
                    toast(t.status?.message.toString())
                    Preference.clearPrefRent()
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
        val down_payment = edt_down_payment.text
        val monthly_payment = edt_monthly_payment.text
        val des = edt_des.text
        val neighborhood = edt_neighborhood.text

        val b = check(edt_rooms, edt_meter)

        if (b) {
            hideLoading()
            return
        }


        Repository().getInstance()?.updateHome(modelEdit?.id!!, homeType, 1, cityId.toString(), 0.0, 0.0,
                rooms.toString(), arae.toString(), "0",
                down_payment.toString(), monthly_payment.toString(), des.toString(), ed_phon_number.text.toString(),
                "" + idCurrency, neighborhood.toString(),
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
                    Preference.saveRentHomeTypeText = t.data!![i].title
                    Preference.saveRentHomeTypeId = t.data!![i].id

                }

            }

            override fun onFail(e: Exception, code: Int) {
                super.onFail(e, code)
                hideLoading()
                toast(R.string.try_agin)

            }

        })


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
                    txt_typ_currency.setText(t.data?.get(i)!!.name)
                    txt_typ_currency.setTextColor(Color.BLACK)
                    Preference.saveRentUnitId = t.data?.get(i)!!.id
                    Preference.saveRentUnitText = t.data?.get(i)!!.name

                }

            }

            override fun onFail(e: Exception, code: Int) {
                super.onFail(e, code)
                hideLoading()
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
                        Preference.saveRentImage1 = uri.toString()
                        Preference.saveRentImagePath1 = path1
                        img_close_rent_one.visibility = View.VISIBLE
//                            image_first.setImageURI(uri)
                        Glide.with(this)
                                .load(uri)
                                .placeholder(R.drawable.camera)
                                .error(R.drawable.camera)
                                .into(image_first_rent)

                    } else if (itemClick == 2) {

                        path2 = it[0].path
                        Preference.saveRentImage2 = uri.toString()
                        Preference.saveRentImagePath2 = path2
                        img_close_rent_two.visibility = View.VISIBLE
//                            image_seconds.setImageURI(uri)
                        Glide.with(this)
                                .load(uri)
                                .placeholder(R.drawable.camera)
                                .error(R.drawable.camera)
                                .into(image_seconds_rent)
                    } else if (itemClick == 3) {

                        path3 = it[0].path
                        Preference.saveRentImage3 = uri.toString()
                        Preference.saveRentImagePath3 = path3
                        img_close_rent_three.visibility = View.VISIBLE
//                            image_three.setImageURI(uri)
                        Glide.with(this)
                                .load(uri)
                                .placeholder(R.drawable.camera)
                                .error(R.drawable.camera)
                                .into(image_three_rent)
                    } else if (itemClick == 4) {

                        path4 = it[0].path
                        Preference.saveRentImage4 = uri.toString()
                        Preference.saveRentImagePath4 = path4
                        img_close_rent_four.visibility = View.VISIBLE
//                            image_four.setImageURI(uri)
                        Glide.with(this)
                                .load(uri)
                                .placeholder(R.drawable.camera)
                                .error(R.drawable.camera)
                                .into(image_four_rent)
                    } else if (itemClick == 5) {

                        path5 = it[0].path
                        Preference.saveRentImage5 = uri.toString()
                        Preference.saveRentImagePath5 = path5
                        img_close_rent_five.visibility = View.VISIBLE
//                            image_five_rent.setImageURI(uri)
                        Glide.with(this)
                                .load(uri)
                                .placeholder(R.drawable.camera)
                                .error(R.drawable.camera)
                                .into(image_five_rent)
                    } else if (itemClick == 6) {

                        path6 = it[0].path
                        Preference.saveRentImage6 = uri.toString()
                        Preference.saveRentImagePath6 = path6
                        img_close_rent_six.visibility = View.VISIBLE
//                            image_six_rent.setImageURI(uri)
                        Glide.with(this)
                                .load(uri)
                                .placeholder(R.drawable.camera)
                                .error(R.drawable.camera)
                                .into(image_six_rent)
                    } else if (itemClick == 7) {
                        path7 = it[0].path
                        Preference.saveRentImage7 = uri.toString()
                        Preference.saveRentImagePath7 = path7
                        img_close_rent_seven.visibility = View.VISIBLE
//                            image_seven_rent.setImageURI(uri)
                        Glide.with(this)
                                .load(uri)
                                .placeholder(R.drawable.camera)
                                .error(R.drawable.camera)
                                .into(image_seven_rent)
                    } else if (itemClick == 8) {
                        path8 = it[0].path
                        Preference.saveRentImage8 = uri.toString()
                        Preference.saveRentImagePath8 = path8
                        img_close_rent_eight.visibility = View.VISIBLE
//                            image_eight_rent.setImageURI(uri)
                        Glide.with(this)
                                .load(uri)
                                .placeholder(R.drawable.camera)
                                .error(R.drawable.camera)
                                .into(image_eight_rent)
                    } else if (itemClick == 9) {
                        path9 = it[0].path
                        Preference.saveRentImage9 = uri.toString()
                        Preference.saveRentImagePath9 = path9
                        img_close_rent_nine.visibility = View.VISIBLE
//                            image_nine_rent.setImageURI(uri)
                        Glide.with(this)
                                .load(uri)
                                .placeholder(R.drawable.camera)
                                .error(R.drawable.camera)
                                .into(image_nine_rent)
                    } else if (itemClick == 10) {
                        path10 = it[0].path
                        Preference.saveRentImage10 = uri.toString()
                        Preference.saveRentImagePath10 = path10
                        img_close_rent_ten.visibility = View.VISIBLE
//                            image_ten_rent.setImageURI(uri)
                        Glide.with(this)
                                .load(uri)
                                .placeholder(R.drawable.camera)
                                .error(R.drawable.camera)
                                .into(image_ten_rent)
                    } else if (itemClick == 11) {
                        path11 = it[0].path
                        Preference.saveRentImage11 = uri.toString()
                        Preference.saveRentImagePath11 = path11
                        img_close_rent_eleven.visibility = View.VISIBLE
//                            image_eleven_rent.setImageURI(uri)
                        Glide.with(this)
                                .load(uri)
                                .placeholder(R.drawable.camera)
                                .error(R.drawable.camera)
                                .into(image_eleven_rent)
                    } else if (itemClick == 12) {
                        path12 = it[0].path
                        Preference.saveRentImage12 = uri.toString()
                        Preference.saveRentImagePath12 = path12
                        img_close_rent_twelve.visibility = View.VISIBLE
//                            image_twelve_rent.setImageURI(uri)
                        Glide.with(this)
                                .load(uri)
                                .placeholder(R.drawable.camera)
                                .error(R.drawable.camera)
                                .into(image_twelve_rent)
                    }
                }
                .onCancel {

                }
                .start()

    }

    fun choosePhotoFromGallaryMultiple() {
//        imagesUris = mutableListOf()
//        val intent = Intent()
//        intent.type = "image/*"
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(Intent.createChooser(intent, "android.intent.action.SEND_MULTIPLE"), GALLERY_MULTIPLE)

        Album.image(this) // Image selection.
                .multipleChoice()
                .camera(false)
                .columnCount(4)
                .selectCount(8)
                .checkedList(imagesUris)
                .onResult {
                    it.forEachIndexed { index, path ->
                        when (index) {
                            0 -> {
                                val file = File(path.path)
                                val uri = Uri.fromFile(file)
                                image_first_rent.setImageURI(uri)
                                path1 = path.path
                                Preference.saveRentImagePath1 = path1
                                Preference.saveRentImage1 = uri.toString()

                            }
                            1 -> {
                                val file = File(path.path)
                                val uri = Uri.fromFile(file)
                                image_seconds_rent.setImageURI(uri)
                                path2 = path.path
                                Preference.saveRentImagePath2 = path2
                                Preference.saveRentImage2 = uri.toString()

                            }
                            2 -> {
                                val file = File(path.path)
                                val uri = Uri.fromFile(file)
                                image_three_rent.setImageURI(uri)
                                path3 = path.path
                                Preference.saveRentImagePath3 = path3
                                Preference.saveRentImage3 = uri.toString()
                            }
                            3 -> {
                                val file = File(path.path)
                                val uri = Uri.fromFile(file)
                                image_four_rent.setImageURI(uri)
                                path4 = path.path
                                Preference.saveRentImagePath4 = path4
                                Preference.saveRentImage4 = uri.toString()
                            }
                            4 -> {
                                val file = File(path.path)
                                val uri = Uri.fromFile(file)
                                image_five_rent.setImageURI(uri)
                                path5 = path.path
                                Preference.saveRentImagePath5 = path5
                                Preference.saveRentImage5 = uri.toString()
                            }
                            5 -> {
                                val file = File(path.path)
                                val uri = Uri.fromFile(file)
                                image_six_rent.setImageURI(uri)
                                path6 = path.path
                                Preference.saveRentImagePath6 = path6
                                Preference.saveRentImage6 = uri.toString()
                            }
                            6 -> {
                                val file = File(path.path)
                                val uri = Uri.fromFile(file)
                                image_seven_rent.setImageURI(uri)
                                path7 = path.path
                                Preference.saveRentImagePath7 = path7
                                Preference.saveRentImage7 = uri.toString()

                            }
                            7 -> {
                                val file = File(path.path)
                                val uri = Uri.fromFile(file)
                                image_eight_rent.setImageURI(uri)
                                path8 = path.path
                                Preference.saveRentImagePath8 = path8
                                Preference.saveRentImage8 = uri.toString()
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
        val uri: Uri = FileProvider.getUriForFile(this, "com.alaan.outn.provider", file)
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
                            Preference.saveRentImage1 = uri.toString()
                            Preference.saveRentImagePath1 = path1
                            image_first_rent.setImageURI(uri)

                        } else if (itemClick == 2) {

                            path2 = path
                            Preference.saveRentImage2 = uri.toString()
                            Preference.saveRentImagePath2 = path2
                            image_seconds_rent.setImageURI(uri)

                        } else if (itemClick == 3) {

                            path3 = path
                            Preference.saveRentImage3 = uri.toString()
                            Preference.saveRentImagePath3 = path3
                            image_three_rent.setImageURI(uri)
                        } else if (itemClick == 4) {

                            path4 = path
                            Preference.saveRentImage4 = uri.toString()
                            Preference.saveRentImagePath4 = path4
                            image_four_rent.setImageURI(uri)
                        } else if (itemClick == 5) {

                            path5 = path
                            Preference.saveRentImage5 = uri.toString()
                            Preference.saveRentImagePath5 = path5
                            image_five_rent.setImageURI(uri)
                        } else if (itemClick == 6) {

                            path6 = path
                            Preference.saveRentImage6 = uri.toString()
                            Preference.saveRentImagePath6 = path6
                            image_six_rent.setImageURI(uri)
                        } else if (itemClick == 7) {

                            path7 = path
                            Preference.saveRentImage7 = uri.toString()
                            Preference.saveRentImagePath7 = path7
                            image_seven_rent.setImageURI(uri)
                        } else if (itemClick == 8) {

                            path8 = path
                            Preference.saveRentImage8 = uri.toString()
                            Preference.saveRentImagePath8 = path8
                            image_eight_rent.setImageURI(uri)

                        } else if (itemClick == 9) {
                            path9 = path
                            Preference.saveRentImage9 = uri.toString()
                            Preference.saveRentImagePath9 = path9
                            image_nine_rent.setImageURI(uri)
                        } else if (itemClick == 10) {
                            path10 = path
                            Preference.saveRentImage10 = uri.toString()
                            Preference.saveRentImagePath10 = path10
                            image_ten_rent.setImageURI(uri)
                        } else if (itemClick == 11) {
                            path11 = path
                            Preference.saveRentImage11 = uri.toString()
                            Preference.saveRentImagePath11 = path11
                            image_eleven_rent.setImageURI(uri)
                        } else if (itemClick == 12) {
                            path12 = path
                            Preference.saveRentImage12 = uri.toString()
                            Preference.saveRentImagePath12 = path12
                            image_twelve_rent.setImageURI(uri)
                        }

                    }

                }
                GALLERY -> {
                    val path = data!!.data?.let { Utils.getPath(it) }
                    if (path != null) {
                        val file = File(path)
                        val uri = Uri.fromFile(file)

                        if (itemClick == 1) {
                            path1 = path
                            Preference.saveRentImage1 = uri.toString()
                            Preference.saveRentImagePath1 = path1
                            img_close_rent_one.visibility = View.VISIBLE
//                            image_first.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_first_rent)

                        } else if (itemClick == 2) {

                            path2 = path
                            Preference.saveRentImage2 = uri.toString()
                            Preference.saveRentImagePath2 = path2
                            img_close_rent_two.visibility = View.VISIBLE
//                            image_seconds.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_seconds_rent)
                        } else if (itemClick == 3) {

                            path3 = path
                            Preference.saveRentImage3 = uri.toString()
                            Preference.saveRentImagePath3 = path3
                            img_close_rent_three.visibility = View.VISIBLE
//                            image_three.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_three_rent)
                        } else if (itemClick == 4) {

                            path4 = path
                            Preference.saveRentImage4 = uri.toString()
                            Preference.saveRentImagePath4 = path4
                            img_close_rent_four.visibility = View.VISIBLE
//                            image_four.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_four_rent)
                        } else if (itemClick == 5) {

                            path5 = path
                            Preference.saveRentImage5 = uri.toString()
                            Preference.saveRentImagePath5 = path5
                            img_close_rent_five.visibility = View.VISIBLE
//                            image_five_rent.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_five_rent)
                        } else if (itemClick == 6) {

                            path6 = path
                            Preference.saveRentImage6 = uri.toString()
                            Preference.saveRentImagePath6 = path6
                            img_close_rent_six.visibility = View.VISIBLE
//                            image_six_rent.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_six_rent)
                        } else if (itemClick == 7) {
                            path7 = path
                            Preference.saveRentImage7 = uri.toString()
                            Preference.saveRentImagePath7 = path7
                            img_close_rent_seven.visibility = View.VISIBLE
//                            image_seven_rent.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_seven_rent)
                        } else if (itemClick == 8) {
                            path8 = path
                            Preference.saveRentImage8 = uri.toString()
                            Preference.saveRentImagePath8 = path8
                            img_close_rent_eight.visibility = View.VISIBLE
//                            image_eight_rent.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_eight_rent)
                        } else if (itemClick == 9) {
                            path9 = path
                            Preference.saveRentImage9 = uri.toString()
                            Preference.saveRentImagePath9 = path9
                            img_close_rent_nine.visibility = View.VISIBLE
//                            image_nine_rent.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_nine_rent)
                        } else if (itemClick == 10) {
                            path10 = path
                            Preference.saveRentImage10 = uri.toString()
                            Preference.saveRentImagePath10 = path10
                            img_close_rent_ten.visibility = View.VISIBLE
//                            image_ten_rent.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_ten_rent)
                        } else if (itemClick == 11) {
                            path11 = path
                            Preference.saveRentImage11 = uri.toString()
                            Preference.saveRentImagePath11 = path11
                            img_close_rent_eleven.visibility = View.VISIBLE
//                            image_eleven_rent.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_eleven_rent)
                        } else if (itemClick == 12) {
                            path12 = path
                            Preference.saveRentImage12 = uri.toString()
                            Preference.saveRentImagePath12 = path12
                            img_close_rent_twelve.visibility = View.VISIBLE
//                            image_twelve_rent.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_twelve_rent)
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
                            Preference.saveRentImage1 = uri.toString()
                            Preference.saveRentImagePath1 = path1
                            img_close_rent_one.visibility = View.VISIBLE
//                            image_first.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_first_rent)
                        } else if (itemClick == 2) {

                            path2 = path
                            Preference.saveRentImage2 = uri.toString()
                            Preference.saveRentImagePath2 = path2
                            img_close_rent_two.visibility = View.VISIBLE
//                            image_seconds.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_seconds_rent)
                        } else if (itemClick == 3) {

                            path3 = path
                            Preference.saveRentImage3 = uri.toString()
                            Preference.saveRentImagePath3 = path3
                            img_close_rent_three.visibility = View.VISIBLE
//                            image_three.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_three_rent)
                        } else if (itemClick == 4) {

                            path4 = path
                            Preference.saveRentImage4 = uri.toString()
                            Preference.saveRentImagePath4 = path4
                            img_close_rent_four.visibility = View.VISIBLE
//                            image_four.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_four_rent)
                        } else if (itemClick == 5) {

                            path5 = path
                            Preference.saveRentImage5 = uri.toString()
                            Preference.saveRentImagePath5 = path5
                            img_close_rent_five.visibility = View.VISIBLE
//                            image_five_rent.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_five_rent)
                        } else if (itemClick == 6) {

                            path6 = path
                            Preference.saveRentImage6 = uri.toString()
                            Preference.saveRentImagePath6 = path6
                            img_close_rent_six.visibility = View.VISIBLE
//                            image_six_rent.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_six_rent)
                        } else if (itemClick == 7) {
                            path7 = path
                            Preference.saveRentImage7 = uri.toString()
                            Preference.saveRentImagePath7 = path7
                            img_close_rent_seven.visibility = View.VISIBLE
//                            image_seven_rent.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_seven_rent)
                        } else if (itemClick == 8) {
                            path8 = path
                            Preference.saveRentImage8 = uri.toString()
                            Preference.saveRentImagePath8 = path8
//                            image_eight_rent.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_eight_rent)
                        } else if (itemClick == 9) {
                            path9 = path
                            Preference.saveRentImage9 = uri.toString()
                            Preference.saveRentImagePath9 = path9
                            img_close_rent_nine.visibility = View.VISIBLE
//                            image_nine_rent.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_nine_rent)
                        } else if (itemClick == 10) {
                            path10 = path
                            Preference.saveRentImage10 = uri.toString()
                            Preference.saveRentImagePath10 = path10
                            img_close_rent_ten.visibility = View.VISIBLE
//                            image_ten_rent.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_ten_rent)
                        } else if (itemClick == 11) {
                            path11 = path
                            Preference.saveRentImage11 = uri.toString()
                            Preference.saveRentImagePath11 = path11
                            img_close_rent_eleven.visibility = View.VISIBLE
//                            image_eleven_rent.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_eleven_rent)
                        } else if (itemClick == 12) {
                            path12 = path
                            Preference.saveRentImage12 = uri.toString()
                            Preference.saveRentImagePath12 = path12
                            img_close_rent_twelve.visibility = View.VISIBLE
//                            image_twelve_rent.setImageURI(uri)
                            Glide.with(this)
                                    .load(uri)
                                    .placeholder(R.drawable.camera)
                                    .error(R.drawable.camera)
                                    .into(image_twelve_rent)
                        }
                    }
                }

                COUNTRY -> {

                    if (data != null) {
                        listCity.clear()
                        plateCountryCode = data?.getIntExtra("id", 0).toString()
                        txt_country.setText(data?.getStringExtra("name"))
                        cityId = "0"
                        txt_country.setTextColor(Color.BLACK)
                        Preference.saveRentCountryText = data?.getStringExtra("name")
                        Preference.saveRentCountryId = data?.getIntExtra("id", 0)
                    }
                }


                CITY -> {
                    cityId = data?.getIntExtra("id", 0).toString()
                    txt_city.setText(data?.getStringExtra("name"))
                    txt_city.setTextColor(Color.BLACK)
                    Preference.saveRentCityText = data?.getStringExtra("name")
                    Preference.saveRentCityId = data?.getIntExtra("id", 0)
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