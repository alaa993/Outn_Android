package com.alaan.outn.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import androidx.core.content.ContextCompat
import com.alaan.outn.R
import com.alaan.outn.application.G
import java.text.DecimalFormat
import java.util.*
import java.util.regex.Pattern

object Utils {
    fun dp(value: Float): Int {
        return if (value == 0f) {
            0
        } else DpToPix(value)
    }

    fun getRandomColor(id: String?): Int {
        val colorList = ArrayList<String>()
        colorList.add("#E56555")
        colorList.add("#F28C48")
        colorList.add("#5094D2")
        colorList.add("#8E85EE")
        colorList.add("#F2749A")
        colorList.add("#E58544")
        colorList.add("#76C74D")
        colorList.add("#5FBED5")
        val i = id?.hashCode() ?: 0
        return if (i > 0 && i < colorList.size) {
            Color.parseColor(colorList[i])
        } else Color.parseColor(colorList[Math.abs(i % colorList.size)])
    }

//    fun hideKeyboard(activity: Activity, view: View?) {
//        if (view != null) {
//            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            imm?.hideSoftInputFromWindow(view.windowToken, 0)
//        }
//    }

    fun DpToPix(dp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics).toInt()
    }

    fun SpToPix(sp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().displayMetrics).toInt()
    }

    fun DpToSp(dp: Float): Int {
        return (DpToPix(dp) / SpToPix(dp).toFloat()).toInt()
    }

    fun stringCheck(string: String?): String? {
        return if (string != null && string != "null" && string != "") string else null
    }

    fun isListEquals(l1: List<*>, l2: List<*>): Boolean {
        return l1.size == l2.size && l1.containsAll(l2) && l2.containsAll(l1)
    }

    //    public static String toPersianNumber(String string) {
//        return net.car.app.utils.FormatHelper.toPersianNumber(string);
//    }
//
//    public static String toEnglishNumber(String string) {
//        return net.car.app.utils.FormatHelper.toEnglishNumber(string);
//    }
    fun logMap(tag: String?, params: Map<String?, String?>?) {
        if (params == null) {
            return
        }
        for (key in params.keys) {
            val value = params[key]
            // G.log(tag, key + "=" + value);
        }
    }

    fun logMap(tag: String?, params: ContentValues?) {
        if (params == null) {
            return
        }
        for (key in params.keySet()) {
            val value = params[key].toString() + ""
            // G.log(tag, key + "=" + value);
        }
    }

    fun raghamAshar(tedadAshar: Int, percet: Double): Double {
        val darsadTakhfif = String.format(Locale.ENGLISH, "%." + tedadAshar + "f", percet).toDouble()
        return if (!java.lang.Double.isNaN(darsadTakhfif)) darsadTakhfif else 0.00
    }

    fun getFileSize(size: Long): String {
        if (size <= 0) return "0"
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()
        return DecimalFormat("#,##0.#").format(size / Math.pow(1024.0, digitGroups.toDouble())) + " " + units[digitGroups]
    }

    fun setFillWindowAndTransparetStatusBar(activity: Activity) { /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }*/
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            activity.window.statusBarColor = ContextCompat.getColor(activity, R.color.colorPrimary)
            //activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    fun changeLanuge(activity: Activity) {
        val languageCode = Preference.language
        if (languageCode == "ar" || languageCode == "ku") activity.window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL else activity.window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR
    }

    fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
        val win = activity.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    val randomColor: Int
        get() {
            val rnd = Random()
            return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        }

    fun getPercent(downlodedFile: Long, totalBytes: Long): Int {
        return (java.lang.Double.longBitsToDouble(downlodedFile) / java.lang.Double.longBitsToDouble(totalBytes) * 100).toInt()
    }

    @JvmStatic
    fun main(args: Array<String>) {
    }

    fun getColor(color: Int): Int {
        return ContextCompat.getColor(G.instance, color)
    }

    fun getString(id: Int): String {
        return G.instance.applicationContext.resources.getString(id)
    }

    fun getDimPix(dim: Int): Float {
        return G.instance.applicationContext.resources.getDimensionPixelSize(dim).toFloat()
    }

    fun getDim(dim: Int): Float {
        return G.instance.applicationContext.resources.getDimension(dim)
    }

    @JvmOverloads
    fun runOnUIThread(runnable: Runnable?, delay: Long = 0) { //        if (delay == 0) {
//            G.HANDLER.post(runnable);
//        } else {
//            G.HANDLER.postDelayed(runnable, delay);
//        }
    }

    val isTablet: Boolean
        get() {
            var screenLayout = Resources.getSystem().configuration.screenLayout
            screenLayout = screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
            return when (screenLayout) {
                Configuration.SCREENLAYOUT_SIZE_SMALL -> false
                Configuration.SCREENLAYOUT_SIZE_NORMAL -> false
                Configuration.SCREENLAYOUT_SIZE_LARGE -> true
                Configuration.SCREENLAYOUT_SIZE_XLARGE -> true
                else -> false
            }
        }

    val displayWidthPx: Int
        get() {
            val displaymetrics = Resources.getSystem().displayMetrics
            return displaymetrics.widthPixels
        }

    val density: Int
        get() = Math.max(dp(displayWidthPx.toFloat()) / displayWidthPx, 1)

    val displayHeightPx: Int
        get() {
            val displaymetrics = Resources.getSystem().displayMetrics
            return displaymetrics.heightPixels
        }

    fun installApk(context: Context?, fileUri: Uri?) { //        File toInstall = new File(fileUri.getPath());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            Uri apkUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", toInstall);
//            Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
//            intent.setData(apkUri);
//            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
//        } else {
//            Uri apkUri = Uri.fromFile(toInstall);
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
//        }
    }

    fun formatToMilitary(i: Long): String {
        return if (i <= 9) "0$i" else i.toString()
    }

    fun isValidMobile(phone: String?): Boolean {
        val mobilePattern = Pattern.compile("(\\+98|0)?9\\d{9}")
        return mobilePattern.matcher(phone).matches()
    }

    fun isValidMail(email: String?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    //    public static void openForView(Context context, File file) {
//
//
////        try {
////            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
////                Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
////                Intent intent = new Intent(Intent.ACTION_VIEW);
////                String mimType = getMimeType(file.getAbsolutePath());
////                intent.setDataAndType(uri, mimType);
////                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
////                Intent j = Intent.createChooser(intent, "Choose an application to open with:");
////                context.startActivity(j);
////            } else {
////                Uri uri = Uri.fromFile(file);
////                Intent intent = new Intent(Intent.ACTION_VIEW);
////                String mimType = getMimeType(file.getAbsolutePath());
////                intent.setDataAndType(uri, mimType);
////                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                Intent j = Intent.createChooser(intent, "Choose an application to open with:");
////                context.startActivity(j);
////            }
//
//        } catch (Exception e) {
//           // G.toast("نرم افزاری برای مشاهده فایل پیدا نشد");
//        }
//    }
    /**
     * alwas check if return value is null
     *
     * @param url
     * @return
     */
    fun getMimeType(url: String?): String? {
        if (url == null) {
            return null
        }
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        type = if (extension != null && !extension.isEmpty()) {
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        } else {
            val cR = G.instance.contentResolver
            cR.getType(Uri.parse(url))
        }
        if (type == null || type.isEmpty()) {
            val mim = url.substring(url.lastIndexOf(".") + 1, url.length)
            val ss = mim.toLowerCase()
            when (ss) {
                "mp3" -> type = "audio/mpeg"
                "zip" -> type = "application/zip"
                "jpg" -> type = "image/jpeg"
                "png" -> type = "image/png"
                "tiff" -> type = "image/tif"
                "tif" -> type = "image/tif"
                "mp4" -> type = "video/mp4"
                "doc" -> type = "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                "ppt" -> type = "application/vnd.ms-powerpoint"
                "xlsx" -> type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                "pdf" -> type = "application/pdf"
                "txt" -> type = "text/plain"
            }
        }
        return type
    }

    fun fullNameFirstLetters(fullName: String?): String {
        if (fullName == null) return ""
        var firstLetters = ""
        val fullnameArray = fullName.split(" ").toTypedArray()
        val fullnameArrayLength = fullnameArray.size
        if (fullnameArrayLength == 1) {
            firstLetters = fullnameArray[0].substring(0, 1)
        } else if (fullnameArrayLength >= 2) {
            val s1 = if (fullnameArray[0].isEmpty()) "" else fullnameArray[0].substring(0, 1)
            val s2: String
            s2 = if (fullnameArray[1].isEmpty() && fullnameArrayLength >= 3) {
                if (fullnameArray[2].isEmpty()) "" else fullnameArray[2].substring(0, 1)
            } else {
                if (fullnameArray[1].isEmpty()) "" else fullnameArray[1].substring(0, 1)
            }
            firstLetters = "$s1 $s2"
        } else {
            firstLetters = " "
        }
        return firstLetters
    }

    fun getImagePicasso(imagePath: String?): String {
        return if (imagePath == null || imagePath.isEmpty()) "noImage" else imagePath
    }

    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    fun getDataColumn(context: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(
                column
        )
        try {
            cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(column)
                val value = cursor.getString(column_index)
                return if (value.startsWith("content://") || !value.startsWith("/") && !value.startsWith("file://")) {
                    null
                } else value
            }
        } catch (e: Exception) {
        } finally {
            cursor?.close()
        }
        return null
    }

    @SuppressLint("NewApi")
    fun getPath(uri: Uri): String? {
        try {
            val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
            if (isKitKat && DocumentsContract.isDocumentUri(G.instance, uri)) {
                if (isExternalStorageDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":").toTypedArray()
                    val type = split[0]
                    if ("primary".equals(type, ignoreCase = true)) {
                        return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                    }
                } else if (isDownloadsDocument(uri)) {
                    val id = DocumentsContract.getDocumentId(uri)
                    val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id))
                    return getDataColumn(G.instance, contentUri, null, null)
                } else if (isMediaDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":").toTypedArray()
                    val type = split[0]
                    var contentUri: Uri? = null
                    when (type) {
                        "image" -> contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        "video" -> contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                        "audio" -> contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                    val selection = "_id=?"
                    val selectionArgs = arrayOf(
                            split[1]
                    )
                    return getDataColumn(G.instance, contentUri, selection, selectionArgs)
                }
            } else if ("content".equals(uri.scheme, ignoreCase = true)) {
                return getDataColumn(G.instance, uri, null, null)
            } else if ("file".equals(uri.scheme, ignoreCase = true)) {
                return uri.path
            }
        } catch (e: Exception) {
        }
        return null
    }

    fun getCurrentLocale(context: Context): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales[0]
        } else {
            context.resources.configuration.locale
        }
    }
}