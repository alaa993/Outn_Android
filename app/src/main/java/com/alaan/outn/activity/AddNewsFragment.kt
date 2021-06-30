package com.alaan.outn.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.alaan.outn.R
import com.alaan.outn.api.Repository
import com.alaan.outn.api.interfaces.CallBack
import com.alaan.outn.model.Api
import com.alaan.outn.model.ShowActivityViewModel
import com.alaan.outn.utils.Utils
import com.bumptech.glide.Glide
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.dialog_add_news.*
import kotlinx.android.synthetic.main.dialog_add_news.img_real_state
import org.jetbrains.anko.support.v4.toast
import java.io.File
import java.io.IOException
import java.util.*


class AddNewsFragment<T: ShowActivityViewModel> : BaseDialogFragment() {

    var imageRealState = false;
    var imagePathRealState = ""
    var mCurrentPhotoPath: String? = null
    var imagePath: String? = null
    private lateinit var viewModel: T
    private lateinit var modelClass: Class<T>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        yesBtn.setOnClickListener {

            if (ed_title.text.count() < 1) {
                toast(getString(R.string.field_cannot_empty))
                return@setOnClickListener
            }
            if (ed_body.text.count() < 10) {
                toast(getString(R.string.lenght_canot_less_10))
                return@setOnClickListener
            }
            if (imagePath == null) {
                toast(getString(R.string.select_an_photo_for_your_opinion))
                return@setOnClickListener
            }

            imagePath?.let {
                addNewsFunc(ed_title.text.toString(), ed_body.text.toString(), it)
            }
        }
        noBtn.setOnClickListener {
            this.dismiss()
        }

        img_real_state.setOnClickListener {
            checkPermission()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Utils.setFillWindowAndTransparetStatusBar(requireActivity())

        return inflater.inflate(R.layout.dialog_add_news, container, false)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = savedInstanceState ?: arguments ?: return
        modelClass = bundle.getSerializable(ARG_MODEL_CLASS) as? Class<T>
                ?: throw IllegalArgumentException()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(modelClass)
    }
    private fun checkPermission() {

        Dexter.withActivity(requireActivity())
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

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(requireContext())
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

    val GALLERY = 100
    val CAMERA = 200

    @SuppressLint("NewApi")
    private fun takePhotoFromCamera() {

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file: File = createFile()
        val uri: Uri = FileProvider.getUriForFile(
                requireContext(),
                "com.alaan.outn.provider",
                file)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(intent, CAMERA)
    }

    @Throws(IOException::class)
    private fun createFile(): File {

        val storageDir: File = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
                UUID.randomUUID().toString(), /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = absolutePath
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY -> {
                    val path = data?.data?.let { Utils.getPath(it) }
                    path?.let {
                        val file = File(path)
                        val uri = Uri.fromFile(file)
                        if (imageRealState) {
                            Glide.with(this).clear(img_real_state)
                            imagePathRealState = it
                            img_real_state.setImageURI(uri)
                        } else {
                            Glide.with(this).clear(img_real_state)
                            imagePath = it
                            img_real_state.setImageURI(uri)
                        }
                    }

                }
                CAMERA -> {
                    val path = mCurrentPhotoPath
                    if (path != null) {
                        val file = File(path)
                        val uri = Uri.fromFile(file)
                        if (imageRealState) {
                            Glide.with(this).clear(img_real_state)
                            imagePathRealState = path
                            img_real_state.setImageURI(uri)
                        } else {
                            Glide.with(this).clear(img_real_state)
                            imagePath = path
                            img_real_state.setImageURI(uri)
                        }
                    }
                }

            }
        }

    }
    fun addNewsFunc(title: String, body: String, image: String) {
       showLoading()
        Repository().getInstance()?.addNews(title, body, image, object : CallBack<Api<List<Void>>>() {
            override fun onSuccess(t: Api<List<Void>>) {
                super.onSuccess(t)
                viewModel._result.value = "success"
                this@AddNewsFragment.dismiss()
                hideLoading()
//                getData()
            }

            override fun onFail(e: Exception, code: Int) {
                super.onFail(e, code)
                this@AddNewsFragment.dismiss()
                viewModel._result.value ="error"
                hideLoading()
//                getData()
            }
        })
    }

    companion object {
        val TAG = AddNewsFragment::class.java.simpleName
        private const val ARG_MODEL_CLASS = "modelClass"

        fun<T : ShowActivityViewModel> newInstance(modelClass: Class<T>): AddNewsFragment<T> {
            val args = Bundle()
            saveInstanceState(args, modelClass)
            return AddNewsFragment<T>().apply {
                arguments = args
            }
        }
        private fun saveInstanceState(bundle: Bundle, modelClass: Class<*>) {
            bundle.putSerializable(ARG_MODEL_CLASS, modelClass)
        }
    }

    var progressDialog: ProgressDialog? = null
    private fun showLoading() {
        progressDialog = ProgressDialog(activity)
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

}