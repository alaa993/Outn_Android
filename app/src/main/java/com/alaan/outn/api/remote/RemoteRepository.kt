package com.alaan.outn.api.remote

import ModelProfile
import android.util.Log
import com.alaan.outn.api.interfaces.CallBack
import com.alaan.outn.api.interfaces.CallBacks
import com.alaan.outn.api.remote.service.ApiConnection
import com.alaan.outn.model.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RemoteRepository : RemoteSource {

    private var INSTANCE: RemoteRepository? = null
    private var apiConnection: ApiConnection = ApiConnection.instance!!

    fun getInstance(): RemoteRepository? {
        if (INSTANCE == null) {
            synchronized(RemoteRepository::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = RemoteRepository()
                }
            }
        }
        return INSTANCE
    }

    protected fun <T> makeCallback(cb: CallBacks<T>): Callback<T>? {
        return object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    cb.onSuccess(response.body()!!)
                } else {

                    val message: String?
                    message = try {
                        response.errorBody()!!.string()
                    } catch (ex: java.lang.Exception) {
                        response.message()
                    }
                    cb.onFail(java.lang.Exception(message), response.code())
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {

                val exception = Exception(t.message, t)
                cb.onFail(exception, 100)
            }
        }
    }

    override
    fun getLogin(mobile: String?, callBack: CallBack<Api<Token>>) {

        apiConnection.service!!.login(mobile).enqueue(makeCallback(callBack))
    }



    override fun register(fname: String?, lname: String?, email: String?, mobile: String?, address: String?, location_id: String, business_name: String, imagePath: String?, real_state_image: String?,real_state_description:String?, callBack: CallBack<Api<Token>>) {

        var body: MultipartBody.Part? = null
        if (imagePath != null && imagePath != null) {
            val file = File(imagePath)
            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            body = MultipartBody.Part.createFormData("avatar", file.name, requestFile)
        }

        var real_state: MultipartBody.Part? = null
        if (real_state_image != null && real_state_image != "") {
            val file = File(real_state_image)
            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            real_state = MultipartBody.Part.createFormData("real_state_image", file.name, requestFile)
        }


        val fname: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), fname.toString())
        val lname: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), lname.toString())
        val email: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), email.toString())
        val mobile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), mobile.toString())
        val address: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), mobile.toString())
        val business_name: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), business_name.toString())
        val real_state_description: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), real_state_description.toString())
        val location_id: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), mobile.toString())

        apiConnection.service!!.register(fname, lname, email, mobile, address, location_id,business_name,real_state_description, body,real_state)
            .enqueue(makeCallback(callBack))
    }

    override fun getProfile(callBack: CallBack<Api<ModelProfile>>) {

        apiConnection.service!!.getProfile().enqueue(makeCallback(callBack))
    }

    override fun updateProfile(
        fname: String?,
        lname: String?,
        email: String?,
        mobile: String?,
        address: String?,
        business_name:String,
        imagePath: String?,
        real_state_image:String?,
        real_state_description:String?,
        callBack: CallBack<Api<List<Void>>>
    ) {

        var body: MultipartBody.Part? = null
        if (imagePath != null && imagePath != "") {
            val file = File(imagePath)
            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            body = MultipartBody.Part.createFormData("avatar", file.name, requestFile)
        }

        var real_state: MultipartBody.Part? = null
        if (real_state_image != null && real_state_image != "") {
            val file = File(real_state_image)
            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            real_state = MultipartBody.Part.createFormData("real_state_image", file.name, requestFile)
        }

        val fname: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), fname.toString())
        val lname: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), lname.toString())
        val email: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), email.toString())
        val mobile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), mobile.toString())
        val address: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), address.toString())
        val business_name: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), business_name.toString())
        val real_state_description: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), real_state_description.toString())

        apiConnection.service!!.updateProfile(fname, lname, email, mobile, address,business_name,real_state_description, body,real_state).enqueue(makeCallback(callBack))
    }

    override fun logout(callBack: CallBack<Api<List<Void>>>) {

        apiConnection.service!!.logout().enqueue(makeCallback(callBack))
    }

    override fun addHome(
        home_type_id: Int, ad_type_id: Int, location_id: String,
        lat: Double, lng: Double, rooms: String,
        area: String, price: String, down_payment: String,
        monthly_payment: String, description: String,
        phone: String, currency: String, neighborhood:String,
        imagePath1: String?,
        imagePath2: String?,
        imagePath3: String?,
        imagePath4: String?,
        imagePath5: String?,
        imagePath6: String?,
        imagePath7: String?,
        imagePath8: String?,
        imagePath9: String?,
        imagePath10: String?,
        imagePath11: String?,
        imagePath12: String?,
        callBack: CallBack<Api<ModelAddHome>>
    ) {
        var body1: MultipartBody.Part? = null
        var body2: MultipartBody.Part? = null
        var body3: MultipartBody.Part? = null
        var body4: MultipartBody.Part? = null
        var body5: MultipartBody.Part? = null
        var body6: MultipartBody.Part? = null
        var body7: MultipartBody.Part? = null
        var body8: MultipartBody.Part? = null

        var body9: MultipartBody.Part? = null
        var body10: MultipartBody.Part? = null
        var body11: MultipartBody.Part? = null
        var body12: MultipartBody.Part? = null

        if (imagePath1 != "") {
            val file = File(imagePath1)
            val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            body1 = MultipartBody.Part.createFormData("image1", file.name, requestFile)
        }

        if (imagePath2 != "") {
            val file = File(imagePath2)
            val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            body2 = MultipartBody.Part.createFormData("image2", file.name, requestFile)
        }

        if (imagePath3 != "") {
            val file = File(imagePath3)
            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            body3 = MultipartBody.Part.createFormData("image3", file.name, requestFile)
        }

        if (imagePath4 != "") {
            val file = File(imagePath4)
            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            body4 = MultipartBody.Part.createFormData("image4", file.name, requestFile)
        }

        if (imagePath5 != "") {
            val file = File(imagePath5)
            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            body5 = MultipartBody.Part.createFormData("image5", file.name, requestFile)
        }

        if (imagePath6 != "") {
            val file = File(imagePath6)
            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            body6 = MultipartBody.Part.createFormData("image6", file.name, requestFile)
        }

        if (imagePath7 != "") {
            val file = File(imagePath7)
            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            body7 = MultipartBody.Part.createFormData("image7", file.name, requestFile)
        }

        if (imagePath8 != "") {
            val file = File(imagePath8)
            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            body8 = MultipartBody.Part.createFormData("image8", file.name, requestFile)
        }

        if (imagePath9 != "") {
            val file = File(imagePath9)
            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            body9 = MultipartBody.Part.createFormData("image9", file.name, requestFile)
        }

        if (imagePath10 != "") {
            val file = File(imagePath10)
            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            body10 = MultipartBody.Part.createFormData("image10", file.name, requestFile)
        }

        if (imagePath11 != "") {
            val file = File(imagePath11)
            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            body11 = MultipartBody.Part.createFormData("image11", file.name, requestFile)
        }

        if (imagePath12 != "") {
            val file = File(imagePath12)
            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            body12 = MultipartBody.Part.createFormData("image12", file.name, requestFile)
        }



        val home_type_id: RequestBody =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), home_type_id.toString())
        val ad_type_id: RequestBody =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), ad_type_id.toString())
        val location_id: RequestBody =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), location_id.toString())
        val lat: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), lat.toString())
        val lng: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), lng.toString())
        val rooms: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), rooms.toString())
        val area: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), area.toString())
        val price: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), price.toString())
        val down_payment: RequestBody =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), down_payment.toString())
        val monthly_payment: RequestBody =
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), monthly_payment.toString())
        val description: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), description)
        val phone: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), phone)
        val currency: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), currency)
        val neighborhood: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), neighborhood)


        apiConnection.service!!.addHome(
            home_type_id, ad_type_id, location_id,
            lat, lng, rooms, area, price, down_payment,
            monthly_payment, description, phone, currency,neighborhood, body1, body2, body3, body4,
        body9,body10,body11,body12,body5,body6,body7,body8).enqueue(makeCallback(callBack))
    }

    override fun updateHome(
        home_id: Int,
        home_type_id: Int,
        ad_type_id: Int,
        location_id: String,
        lat: Double,
        lng: Double,
        rooms: String,
        area: String,
        price: String,
        down_payment: String,
        monthly_payment: String,
        description: String,
        phone: String,
        currency: String,
        neighborhood:String,
        imagePath1: String?,
        imagePath2: String?,
        imagePath3: String?,
        imagePath4: String?,
        imagePath5: String?,
        imagePath6: String?,
        imagePath7: String?,
        imagePath8: String?,
        imagePath9: String?,
        imagePath10: String?,
        imagePath11: String?,
        imagePath12: String?,
        callBack: CallBack<Api<List<Void>>>
    ) {

        var body1: MultipartBody.Part? = null
        var body2: MultipartBody.Part? = null
        var body3: MultipartBody.Part? = null
        var body4: MultipartBody.Part? = null
        var body5: MultipartBody.Part? = null
        var body6: MultipartBody.Part? = null
        var body7: MultipartBody.Part? = null
        var body8: MultipartBody.Part? = null

        var body9: MultipartBody.Part? = null
        var body10: MultipartBody.Part? = null
        var body11: MultipartBody.Part? = null
        var body12: MultipartBody.Part? = null



        if (imagePath1 != "") {
            val file = File(imagePath1)
            val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            body1 = MultipartBody.Part.createFormData("image1", file.name, requestFile)
        }

        if (imagePath2 != "") {
            val file = File(imagePath2)
            val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            body2 = MultipartBody.Part.createFormData("image2", file.name, requestFile)
        }

        if (imagePath3 != "") {
            val file = File(imagePath3)
            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            body3 = MultipartBody.Part.createFormData("image3", file.name, requestFile)
        }

        if (imagePath4 != "") {
            val file = File(imagePath4)
            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            body4 = MultipartBody.Part.createFormData("image4", file.name, requestFile)
        }

        if (imagePath5 != "") {
            val file = File(imagePath5)
            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            body5 = MultipartBody.Part.createFormData("image5", file.name, requestFile)
        }

        if (imagePath6 != "") {
            val file = File(imagePath6)
            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            body6 = MultipartBody.Part.createFormData("image6", file.name, requestFile)
        }

        if (imagePath7 != "") {
            val file = File(imagePath7)
            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            body7 = MultipartBody.Part.createFormData("image7", file.name, requestFile)
        }

        if (imagePath8 != "") {
            val file = File(imagePath8)
            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            body8 = MultipartBody.Part.createFormData("image8", file.name, requestFile)
        }

        if (imagePath9 != "") {
            val file = File(imagePath9)
            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            body9 = MultipartBody.Part.createFormData("image9", file.name, requestFile)
        }

        if (imagePath10 != "") {
            val file = File(imagePath10)
            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            body10 = MultipartBody.Part.createFormData("image10", file.name, requestFile)
        }

        if (imagePath11 != "") {
            val file = File(imagePath11)
            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            body11 = MultipartBody.Part.createFormData("image11", file.name, requestFile)
        }

        if (imagePath12 != "") {
            val file = File(imagePath12)
            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            body12 = MultipartBody.Part.createFormData("image12", file.name, requestFile)
        }



        val home_id: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), home_id.toString())
        val home_type_id: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), home_type_id.toString())
        val ad_type_id: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), ad_type_id.toString())
        val location_id: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), location_id.toString())
        val lat: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), lat.toString())
        val lng: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), lng.toString())
        val rooms: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), rooms.toString())
        val area: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), area.toString())
        val price: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), price.toString())
        val down_payment: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), down_payment.toString())
        val monthly_payment: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), monthly_payment.toString())
        val description: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), description)
        val phone: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), phone)
        val currency: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), currency)
        val neighborhood: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), neighborhood)




        apiConnection.service!!.updateHome(
            home_id, home_type_id, ad_type_id, location_id,
            lat, lng, rooms, area, price, down_payment,
            monthly_payment, description, phone, currency,neighborhood,body1, body2, body3, body4
                ,body9,body10,body11,body12,body5,body6,body7,body8).enqueue(makeCallback(callBack))
    }

    override fun getHomeType(lang:String,callBack: CallBack<Api<List<ModelHomeType>>>) {

        apiConnection.service!!.getHomeType(lang).enqueue(makeCallback(callBack))
    }

    override fun getAdversType(callBack: CallBack<Api<List<ModelAdversType>>>) {
        apiConnection.service!!.getAdversType().enqueue(makeCallback(callBack))
    }

    override fun getCountries(callBack: CallBack<Api<List<ModelCountry>>>) {

        apiConnection.service!!.getCountries().enqueue(makeCallback(callBack))
    }

    override fun getListHome(page: Int, per_page: Int,lang: String, callBack: CallBack<Api<List<ModelListHome>>>) {
        apiConnection.service!!.getListHome(page, per_page,lang).enqueue(makeCallback(callBack))
    }

    override fun deletHome(home_id: Int, callBack: CallBack<Api<List<Void>>>) {

        apiConnection.service!!.deletHome(home_id).enqueue(makeCallback(callBack))
    }

    override fun searchHome(
        page: Int,
        per_page: Int,
        location_name: String?,
        ad_type_id: String?,
        lang: String,location_id:String?,user_id:String?,id: Int?,
        callBack: CallBack<Api<List<ModelSearchHome>>>
    ) {

        apiConnection.service!!.searchHome(page, per_page, location_name, ad_type_id,lang,location_id,user_id,id).enqueue(makeCallback(callBack))
    }

    override fun getCurrencies(callBack: CallBack<Api<List<ModelCurrency>>>) {
        apiConnection.service!!.getCurrency().enqueue(makeCallback(callBack))
    }

    override fun getAdv(callBack: CallBack<Api<List<String>>>) {
        apiConnection.service!!.getAdv().enqueue(makeCallback(callBack))
    }

    override fun getCity(id: Int, callBack: CallBack<Api<ModelCountry>>) {
        apiConnection.service!!.getCity(id).enqueue(makeCallback(callBack))
    }


    override fun pushToken(token: String, device_id: String, device_type: String, callBack: CallBack<Api<List<Void>>>) {
        apiConnection.service!!.pushToken(token,device_id,device_type).enqueue(makeCallback(callBack))
    }

    override fun getComments(home_id: String?, per_page: Int, page_number: Int,status:String?, callBack: CallBack<Api<List<Comment>>>) {
        apiConnection.service!!.getComments(home_id, per_page, page_number,status).enqueue(makeCallback(callBack))

    }

    override fun getCommentReply(comment_id: String?, per_page: Int, page_number: Int,status:String?, callBack: CallBack<Api<List<Comment>>>) {
        apiConnection.service!!.getCommentReply(comment_id, per_page, page_number).enqueue(makeCallback(callBack))
    }

    override fun addComment(home_id: String?, text: String?, reply_to: String?,status:String?, callBack: CallBack<Api<Comment>>) {
        apiConnection.service!!.addComment(home_id, text, reply_to,status).enqueue(makeCallback(callBack))
    }

    override fun readComment(comment_id: String?,status:String?, callBack: CallBack<Api<List<Void>>>) {
            apiConnection.service!!.readComment(comment_id).enqueue(makeCallback(callBack))

    }

    override fun deleteComments(comment_id: String?, callBack: CallBack<Api<List<Void>>>) {
        apiConnection.service!!.deleteComments(comment_id).enqueue(makeCallback(callBack))
    }

    override fun getPartnes(callBack: CallBack<Api<List<ModelPartners>>>) {
        apiConnection.service!!.getPartnes().enqueue(makeCallback(callBack))
    }

    override fun getPlatform(lang: String?,callBack: CallBack<Api<List<ModelPlatform>>>) {
        apiConnection.service!!.getPlatform(lang).enqueue(makeCallback(callBack))
    }

    override fun addNews(title: String, body: String, image: String, callBack: CallBack<Api<List<Void>>>) {
        var innerBody: MultipartBody.Part? = null
        if (image != "") {
            val file = File(image)
            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            innerBody = MultipartBody.Part.createFormData("cover", file.name, requestFile)
        }

        val myTitle: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), title.toString())
        val desc: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), body.toString())
        apiConnection.service!!.addNews(myTitle, desc, innerBody).enqueue(makeCallback(callBack));
    }

    override fun sndItemVisibil(item:String, callBack: CallBack<Api<List<Void>>>) {
        apiConnection.service!!.sndItemVisibil(item).enqueue(makeCallback(callBack))
    }

    override fun getNewsUser(id: String,callBack:CallBack<Api<List<ModelNews>>>) {
        apiConnection.service!!.getNewsUser(id).enqueue(makeCallback(callBack))
    }

    override fun deleteNews(id: String, callBack: CallBack<Api<List<Void>>>) {
       apiConnection.service!!.deleteNews(id).enqueue(makeCallback(callBack))
    }

    override fun editeNews(id: String, title: String, body: String, image: String, callBack: CallBack<Api<List<Void>>>) {
        var innerBody: MultipartBody.Part? = null
        if (image != "") {
            val file = File(image)
            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            innerBody = MultipartBody.Part.createFormData("cover", file.name, requestFile)
        }
        val myId: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), id.toString())
        val myTitle: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), title.toString())
        val desc: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), body.toString())

        apiConnection.service!!.editeNews(id, myTitle, desc, innerBody).enqueue(makeCallback(callBack))
    }

    override fun getSetting(lang: String?,callBack: CallBack<Api<ModelSetting>>) {
        apiConnection.service!!.getSetting(lang).enqueue(makeCallback(callBack))
    }

    override fun favorites(id: Int, callBack: CallBack<Api<List<Void>>>) {
        apiConnection.service!!.favorites(id).enqueue(makeCallback(callBack))
    }

    override fun favoritesDelete(id: Int, callBack: CallBack<Api<List<Void>>>) {
       apiConnection.service!!.favoritesDelete(id).enqueue(makeCallback(callBack))
    }
}


