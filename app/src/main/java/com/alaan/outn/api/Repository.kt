package com.alaan.outn.api

import ModelProfile
import com.alaan.outn.api.interfaces.CallBack
import com.alaan.outn.api.remote.RemoteRepository
import com.alaan.outn.model.*
import retrofit2.Call

class Repository : RepositoryMethod {

    private var INSTANCE: Repository? = null
    var remoteRepository: RemoteRepository? = RemoteRepository().getInstance()

  public  fun getInstance(): Repository? {
        if (INSTANCE == null) {
            synchronized(Repository::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = Repository()
                }
            }
        }
        return INSTANCE
    }


    override fun getLogin(mobile: String?, callBack: CallBack<Api<Token>>) {
          remoteRepository?.getLogin(mobile,callBack)
    }

    override fun register(fname: String?, lname: String?, email: String?, mobile: String?, address: String?, location_id: String, business_name: String, imagePath: String?, real_state_image: String?, real_state_description: String?, callBack: CallBack<Api<Token>>) {
      remoteRepository?.register(fname, lname, email, mobile, address, location_id, business_name, imagePath, real_state_image, real_state_description, callBack)
    }

    override fun getProfile(callBack: CallBack<Api<ModelProfile>>) {

          remoteRepository?.getProfile(callBack)
    }

    override fun updateProfile(fname: String?, lname: String?, email: String?, mobile: String?, address: String?, business_name: String, imagePath: String?, real_state_image: String?, real_state_description: String?, callBack: CallBack<Api<List<Void>>>) {
        remoteRepository?.updateProfile(fname, lname, email, mobile, address, business_name, imagePath, real_state_image, real_state_description, callBack)
    }


    override fun logout(callBack: CallBack<Api<List<Void>>>) {

      remoteRepository?.logout(callBack)
    }

    override fun addHome(home_type_id: Int, ad_type_id: Int, location_id: String, lat: Double, lng: Double, rooms: String, area: String, price: String, down_payment: String, monthly_payment: String, description: String, phone: String, currency: String, neighborhood: String, imagePath1: String?, imagePath2: String?, imagePath3: String?, imagePath4: String?, imagePath5: String?, imagePath6: String?, imagePath7: String?, imagePath8: String?, imagePath9: String?, imagePath10: String?, imagePath11: String?, imagePath12: String?, callBack: CallBack<Api<ModelAddHome>>) {
        remoteRepository?.addHome(home_type_id, ad_type_id, location_id, lat, lng, rooms, area, price, down_payment, monthly_payment, description, phone, currency, neighborhood, imagePath1, imagePath2, imagePath3, imagePath4, imagePath9, imagePath10, imagePath11, imagePath12, imagePath5, imagePath6, imagePath7, imagePath8, callBack)
    }

    override fun updateHome(home_id: Int, home_type_id: Int, ad_type_id: Int, location_id: String, lat: Double, lng: Double, rooms: String, area: String, price: String, down_payment: String, monthly_payment: String, description: String, phone: String, currency: String, neighborhood: String, imagePath1: String?, imagePath2: String?, imagePath3: String?, imagePath4: String?, imagePath5: String?, imagePath6: String?, imagePath7: String?, imagePath8: String?, imagePath9: String?, imagePath10: String?, imagePath11: String?, imagePath12: String?, callBack: CallBack<Api<List<Void>>>) {
        remoteRepository?.updateHome(home_id, home_type_id, ad_type_id, location_id, lat, lng, rooms, area, price, down_payment, monthly_payment, description, phone, currency, neighborhood, imagePath1, imagePath2, imagePath3, imagePath4, imagePath9, imagePath10, imagePath11, imagePath12, imagePath5, imagePath6, imagePath7, imagePath8, callBack)
    }

    override fun getHomeType(lang: String,callBack: CallBack<Api<List<ModelHomeType>>>) {
        remoteRepository?.getHomeType(lang,callBack)
    }

    override fun getAdversType(callBack: CallBack<Api<List<ModelAdversType>>>) {
        remoteRepository?.getAdversType(callBack)
    }

    override fun getCountries(callBack: CallBack<Api<List<ModelCountry>>>) {
        remoteRepository?.getCountries(callBack)
    }

    override fun getListHome(page: Int, per_page: Int,lang: String,callBack: CallBack<Api<List<ModelListHome>>>) {
        remoteRepository?.getListHome(page,per_page,lang,callBack)
    }

    override fun deletHome(home_id: Int, callBack: CallBack<Api<List<Void>>>) {
     remoteRepository?.deletHome(home_id,callBack)
    }

    override fun searchHome(page: Int, per_page: Int, location_name: String?, ad_type_id: String?, lang: String, location_id: String?, user_id: String?,id: Int?, callBack: CallBack<Api<List<ModelSearchHome>>>) {
        remoteRepository?.searchHome(page, per_page, location_name, ad_type_id,lang,location_id,user_id,id, callBack)    }

    override fun getPlatform(lang: String?,callBack: CallBack<Api<List<ModelPlatform>>>) {
        remoteRepository?.getPlatform(lang,callBack)
    }

    override fun getCurrencies(callBack: CallBack<Api<List<ModelCurrency>>>) {
        remoteRepository?.getCurrencies(callBack)
    }

    override fun getAdv(callBack: CallBack<Api<List<String>>>) {
        remoteRepository?.getAdv(callBack)
    }

    override fun getCity(id: Int, callBack: CallBack<Api<ModelCountry>>) {
        remoteRepository?.getCity(id,callBack);
    }


    override fun pushToken(token: String, device_id: String, device_type: String, callBack: CallBack<Api<List<Void>>>) {
        remoteRepository?.pushToken(token,device_id,device_type, callBack)
    }


    override fun getComments(home_id: String?, per_page: Int, page_number: Int,status:String?, callBack: CallBack<Api<List<Comment>>>) {
        remoteRepository?.getComments(home_id, per_page, page_number,status, callBack)

    }

    override fun getCommentReply(comment_id: String?, per_page: Int, page_number: Int,status:String?, callBack: CallBack<Api<List<Comment>>>) {
        remoteRepository?.getCommentReply(comment_id, per_page, page_number,status, callBack)
    }

    override fun addComment(home_id: String?, text: String?, reply_to: String?,status:String?, callBack: CallBack<Api<Comment>>) {
        remoteRepository?.addComment(home_id, text, reply_to,status, callBack)
    }

    override fun deleteComments(comment_id: String?, callBack: CallBack<Api<List<Void>>>) {
        remoteRepository?.deleteComments(comment_id, callBack)
    }

    override fun readComment(comment_id: String?,status:String?, callBack: CallBack<Api<List<Void>>>) {
       remoteRepository?.readComment(comment_id,status, callBack)
    }

    override fun getPartnes(callBack: CallBack<Api<List<ModelPartners>>>) {
        remoteRepository?.getPartnes(callBack)
    }

    override fun addNews(title: String, body: String, image: String, callBack: CallBack<Api<List<Void>>>) {
        remoteRepository?.addNews(title, body, image, callBack)
    }

    override fun sndItemVisibil(item: String, callBack: CallBack<Api<List<Void>>>) {
        remoteRepository?.sndItemVisibil(item,callBack)
    }

    override fun getNewsUser(id: String, callBack: CallBack<Api<List<ModelNews>>>) {
        remoteRepository?.getNewsUser(id, callBack)
    }

    override fun deleteNews(id: String, callBack: CallBack<Api<List<Void>>>) {
        remoteRepository?.deleteNews(id, callBack)
    }

    override fun editeNews(id: String, title: String, body: String, image: String, callBack: CallBack<Api<List<Void>>>) {
        remoteRepository?.editeNews(id, title, body, image, callBack)
    }

    override fun getSetting(lang: String?,callBack: CallBack<Api<ModelSetting>>) {
        remoteRepository?.getSetting(lang,callBack)
    }

    override fun favorites(id: Int, callBack: CallBack<Api<List<Void>>>) {
       remoteRepository?.favorites(id, callBack)
    }

    override fun favoritesDelete(id: Int, callBack: CallBack<Api<List<Void>>>) {
       remoteRepository?.favoritesDelete(id, callBack)
    }

}