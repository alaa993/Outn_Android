package com.alaan.outn.activity

import ModelProfile
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.alaan.outn.R
import com.alaan.outn.api.Repository
import com.alaan.outn.api.interfaces.CallBack
import com.alaan.outn.model.Api
import com.alaan.outn.model.Status
import com.alaan.outn.model.Token
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
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_rejester.*
import kotlinx.android.synthetic.main.activity_rejester.btnBack
import kotlinx.android.synthetic.main.activity_rejester.title_app
import java.io.File
import java.io.IOException
import java.nio.file.Files.createFile
import java.util.*

class RejesterActivity : AppCompatActivity() {

    private val PICK_FROM_FILE = 3
    var mobile: String = ""
    var imageRealState = false;
    var imagePathRealState = ""
    var mCurrentPhotoPath:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.setFillWindowAndTransparetStatusBar(this)

        setContentView(R.layout.activity_rejester)
        Utils.changeLanuge(this)
        readView()
        functionView()
        mobile = intent.extras!!.getString("mobile").toString()
        ed_mobile.setText(mobile)


        if (Preference.isLogin!!) {
            editProfile()
        }
    }


    fun editProfile() {

        ed_fName.setText(Preference.firstName.toString())
        ed_lName.setText(Preference.lastName.toString())
        ed_email.setText(Preference.email.toString())
        ed_mobile.setText(Preference.phone.toString())
        etAddress.setText(Preference.address.toString())

        Preference.real_state_description?.let {
            ed_des_real_state.setText(it)
        }

        val a = Preference.real_state_image

        if (a != null) {
            ed_business.setText( Preference.business_name.toString())
        }else {
            ed_business.hint = resources.getString(R.string.business_name)
        }

        Glide.with(this)
                .load(Preference.image)
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .fitCenter()
                .into(profile_image2)

        btn_rejester.setText(resources.getString(R.string.update))

        Glide.with(this)
                .load(Preference.real_state_image)
                .placeholder(R.drawable.camera)
                .error(R.drawable.camera)
                .fitCenter()
                .into(img_real_state)
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
        ed_mobile.isEnabled = false

    }


    fun functionView() {

        profile_image2.setOnClickListener {
            imageRealState = false
           checkPermission()
        }

        btn_rejester.setOnClickListener {
            if (Preference.isLogin!!) {
                updateProfile()
            } else {
                rejester()
            }

        }

        btnBack.setOnClickListener {
            finish()
        }

        img_real_state.setOnClickListener{
            imageRealState = true
            checkPermission()
        }

    }


    fun checkPermission() {

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        showPictureDialog()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                            permissions: List<PermissionRequest>,
                            token: PermissionToken
                    ) {
                        token.continuePermissionRequest()
                    }
                }).check()

    }

    fun rejester() {

        showLoading()
        val fname = ed_fName.text.toString().trim()
        val lname = ed_lName.text.toString().trim()
        val mobile = ed_mobile.text.toString().trim()
        val email = ed_email.text.toString()
        val des = ed_des_real_state.text.toString().trim()
        val address = etAddress.text.toString()

        val b = check(ed_fName, ed_lName, ed_mobile, etAddress)

        if (b) {
            return
        }

        if (email.isNotEmpty()) {
            if (!Utils.isValidMail(email)) {
                ed_email.error = "Not valid"
                return
            }
        }

        Repository().getInstance()?.register(fname, lname, email, mobile, address, "",ed_business.text.toString(), imagePath,imagePathRealState,des,

                object : CallBack<Api<Token>>() {

                    override fun onSuccess(t: Api<Token>) {
                        super.onSuccess(t)
                        hideLoading()
                        if (t.data?.token != null) {

                            Preference.token = t.data?.token
                            Toast.makeText(this@RejesterActivity, t.status?.message, Toast.LENGTH_LONG).show()
                            getProfile()

                        } else {
                            Toast.makeText(this@RejesterActivity, t.status?.message, Toast.LENGTH_LONG).show()
                        }

                    }

                    override fun onFail(e: Exception, code: Int) {
                        super.onFail(e, code)
                        hideLoading()
                        if (code == 404 || code == 422) {
                            val g = Gson()
                            val b = g.fromJson(e.message.toString(), Api::class.java)
                            if (b.status!!.code == 404) {
                                intent = Intent(applicationContext, RejesterActivity::class.java);
                                startActivity(intent)
                            } else {
                                Toast.makeText(this@RejesterActivity, e.message, Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this@RejesterActivity, e.message, Toast.LENGTH_SHORT).show()

                        }
                    }

                })

    }


    fun updateProfile() {


        showLoading()
        val fname = ed_fName.text.toString().trim()
        val lname = ed_lName.text.toString().trim()
        val mobile = ed_mobile.text.toString().trim()
        val email = ed_email.text.toString()
        val des = ed_des_real_state.text.toString().trim()
        val address = etAddress.text.toString()

        val b = check(ed_fName, ed_lName, ed_mobile, etAddress)

        if (b) {
            return
        }

        if (email.isNotEmpty()) {
            if (!Utils.isValidMail(email)) {
                ed_email.error = "Not valid"
                return
            }
        }

        Repository().getInstance()?.updateProfile(fname, lname, email, mobile, address, ed_business.text.toString(), imagePath,imagePathRealState,des,

                object : CallBack<Api<List<Void>>>() {

                    override fun onSuccess(t: Api<List<Void>>) {
                        super.onSuccess(t)
                        hideLoading()
                        if (t.status?.code == 200) {

                            Toast.makeText(this@RejesterActivity, t.status?.message, Toast.LENGTH_SHORT).show()
                            getProfile()
                        }

                    }

                    override fun onFail(e: Exception, code: Int) {
                        super.onFail(e, code)

                        hideLoading()
                        if (code == 409 || code == 422) {
                            Toast.makeText(this@RejesterActivity, e.message, Toast.LENGTH_SHORT).show()

                        } else {

                            Toast.makeText(this@RejesterActivity, resources.getString(R.string.try_agin), Toast.LENGTH_SHORT).show()
                        }
                    }
                })
    }


    fun getProfile() {

        showLoading()
        Repository().getInstance()?.getProfile(object : CallBack<Api<ModelProfile>>() {

            override fun onSuccess(t: Api<ModelProfile>) {
                super.onSuccess(t)

                hideLoading()
                if (t.data?.id != null) {

                    Preference.idUser = t.data?.id.toString()
                    Preference.email = t.data?.email
                    Preference.image = t.data?.avatar
                    Preference.firstName = t.data?.fname
                    Preference.address = t.data?.address
                    Preference.lastName = t.data?.lname
                    Preference.phone = t.data?.mobile
                    Preference.isLogin = true
                    Preference.business_name = t.data?.business_name
                    Preference.real_state_image = t.data?.real_state_image
                    Preference.real_state_description = t.data?.real_state_description
                    finish()

                } else {

                    Toast.makeText(this@RejesterActivity, resources.getString(R.string.try_agin), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFail(e: Exception, code: Int) {
                super.onFail(e, code)
                hideLoading()
                Toast.makeText(this@RejesterActivity, resources.getString(R.string.try_agin), Toast.LENGTH_LONG).show()
            }


        })

    }


    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle(resources.getString(R.string.select_action))
        val pictureDialogItems = arrayOf(resources.getString(R.string.select_gallery), resources.getString(R.string.select_camera))
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    fun choosePhotoFromGallary() {


        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, resources.getString(R.string.complete_action_using)), GALLERY)
    }

    val GALLERY = 1
    val CAMERA = 2
    @SuppressLint("NewApi")
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



    var imagePath: String? = null

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
                        if (imageRealState ) {
                            imagePathRealState = path
                            Glide.with(img_real_state).clear(img_real_state)
                            img_real_state.setImageURI(uri)
                        } else {
                            imagePath = path
                            Glide.with(profile_image2).clear(profile_image2)
                            profile_image2.setImageURI(uri)

                        }

                    }

                }
                GALLERY -> {
                    val path = data!!.data?.let { Utils.getPath(it) }
                    if (path != null) {
                        val file = File(path)
                        val uri = Uri.fromFile(file)
                        if (imageRealState ) {
                            Glide.with(img_real_state).clear(img_real_state)
                            imagePathRealState = path
                            img_real_state.setImageURI(uri)
                        } else {
                            imagePath = path
                            Glide.with(profile_image2).clear(profile_image2)
                            profile_image2.setImageURI(uri)
                        }

                    }
                }

                CAMERA -> {

                    val path = mCurrentPhotoPath
                    if (path != null) {
                        val file = File(path)
                        val uri = Uri.fromFile(file)
                        if (imageRealState ) {
                            Glide.with(img_real_state).clear(img_real_state)
                            imagePathRealState = path
                            img_real_state.setImageURI(uri)
                        } else {
                            Glide.with(profile_image2).clear(profile_image2)
                            imagePath = path
                            profile_image2.setImageURI(uri)
                        }
                    }
                }

            }
        }

    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (resultCode == Activity.RESULT_OK) {
//            when (requestCode) {
//
//                 CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
//                    val result = CropImage.getActivityResult(data)
//                    val path = result.uri.path
//                    val file = File(path)
//                    val uri = Uri.fromFile(file)
//                     if (imageRealState){
//                         Glide.with(this)
//                                 .load(uri)
//                                 .placeholder(R.drawable.camera)
//                                 .error(R.drawable.camera)
//                                 .fitCenter()
//                                 .into(img_real_state)
//                         imagePathRealState = path
//                     }else {
//                         Glide.with(this)
//                                 .load(uri)
//                                 .placeholder(R.drawable.profile)
//                                 .error(R.drawable.profile)
//                                 .fitCenter()
//                                 .into(profile_image2)
//                         imagePath = path
//                     }
//
//
//                }
//                PICK_FROM_FILE -> {
//
//                    val path: String = data!!.data?.let { Utils.getPath(it) }!!
//                    if (path != null) {
//                        val file = File(path)
//                        val uri = Uri.fromFile(file)
//                        startCropImageActivity(uri)
//                    }
//                }
//            }
//        }
//
//    }

    private fun startCropImageActivity(mImageCaptureUri: Uri) {
        CropImage.activity(mImageCaptureUri)
                .setAutoZoomEnabled(true)
                .setAspectRatio(1, 1)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this)
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

