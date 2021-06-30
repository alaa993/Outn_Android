package com.alaan.outn.api.remote

import ModelProfile
import com.alaan.outn.api.interfaces.CallBack
import com.alaan.outn.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.Path

interface RemoteSource {

    fun getLogin(mobile: String?,callBack: CallBack<Api<Token>>)

    fun register(fname: String?,
                 lname:String?,
                 email: String?,
                 mobile: String?,
                 address:String?,
                 location_id:String,
                 business_name:String,
                 imagePath: String?,
                 real_state_image:String?,
                 real_state_description:String?,
                 callBack: CallBack<Api<Token>>)

    fun getProfile(callBack: CallBack<Api<ModelProfile>>)

    fun updateProfile(fname: String?,
                      lname:String?,
                      email: String?,
                      mobile: String?,
                      address: String?,
                      business_name:String,
                      imagePath: String?,
                      real_state_image:String?,
                      real_state_description:String?,
                      callBack: CallBack<Api<List<Void>>>)

    fun logout(callBack: CallBack<Api<List<Void>>>)

    fun addHome(home_type_id:Int,
                ad_type_id:Int,
                location_id:String,
                lat:Double,
                lng:Double,
                rooms:String,
                area:String,
                price:String,
                down_payment:String,
                monthly_payment:String,
                description:String,
                phone:String,
                currency:String,
                neighborhood:String,
                imagePath1: String?,
                imagePath2: String?,
                imagePath3: String?,
                imagePath4: String?,
                imagePath9: String?,
                imagePath10: String?,
                imagePath11: String?,
                imagePath12: String?,
                imagePath5: String?,
                imagePath6: String?,
                imagePath7: String?,
                imagePath8: String?,
                callBack: CallBack<Api<ModelAddHome>>)


    fun updateHome(home_id: Int, home_type_id: Int,
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
                   imagePath9: String?,
                   imagePath10: String?,
                   imagePath11: String?,
                   imagePath12: String?,
                   imagePath5: String?,
                   imagePath6: String?,
                   imagePath7: String?,
                   imagePath8: String?,
                   callBack: CallBack<Api<List<Void>>>)


    fun getHomeType(lang: String,callBack: CallBack<Api<List<ModelHomeType>>>)

    fun getAdversType( callBack: CallBack<Api<List<ModelAdversType>>>)

    fun getCountries(callBack: CallBack<Api<List<ModelCountry>>>)

    fun getListHome(page:Int, per_page:Int,lang: String, callBack: CallBack<Api<List<ModelListHome>>>)

    fun deletHome(home_id:Int,callBack: CallBack<Api<List<Void>>>)

    fun searchHome(page:Int, per_page:Int,location_name:String?,ad_type_id:String?,lang: String,location_id:String?,user_id:String?,id: Int?,callBack:CallBack<Api<List<ModelSearchHome>>>)

    fun getCurrencies(callBack:CallBack<Api<List<ModelCurrency>>>)

   fun getAdv(callBack: CallBack<Api<List<String>>>)

   fun getCity(id:Int,callBack: CallBack<Api<ModelCountry>>)

   fun pushToken(token:String,device_id:String,device_type:String,callBack: CallBack<Api<List<Void>>>)

    fun  getComments(home_id : String?,per_page: Int,page_number: Int,status:String?,callBack: CallBack<Api<List<Comment>>>)

    fun getCommentReply(comment_id: String?, per_page: Int, page_number: Int,status:String?,callBack: CallBack<Api<List<Comment>>>)

    fun addComment(home_id: String?, text: String?, reply_to: String?,status:String?,callBack: CallBack<Api<Comment>>)

   fun deleteComments(comment_id: String?,callBack:CallBack<Api<List<Void>>>)
   fun readComment(comment_id: String?,status:String?,callBack:CallBack<Api<List<Void>>>)

    fun getPartnes(callBack:CallBack<Api<List<ModelPartners>>>)

    fun getPlatform(lang: String?,callBack:CallBack<Api<List<ModelPlatform>>>)

    fun addNews(title:String,body:String, image: String,callBack:CallBack<Api<List<Void>>>)

    fun sndItemVisibil(item:String,callBack:CallBack<Api<List<Void>>>)

    fun getNewsUser(id: String,callBack:CallBack<Api<List<ModelNews>>>)


    fun editeNews(id:String,title:String, body:String, image: String, callBack:CallBack<Api<List<Void>>> )

    fun deleteNews(id:String,callBack:CallBack<Api<List<Void>>>)

    fun getSetting(lang: String?,callBack:CallBack<Api<ModelSetting>>)

    fun favorites( id:Int,callBack:CallBack<Api<List<Void>>>)
    fun favoritesDelete(id:Int,callBack:CallBack<Api<List<Void>>>)

}

