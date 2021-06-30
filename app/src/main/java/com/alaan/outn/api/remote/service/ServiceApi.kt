package com.alaan.outn.api.remote.service

import ModelProfile
import com.alaan.outn.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ServiceApi {

    @FormUrlEncoded
    @POST("login")
    fun login(@Field("mobile") mobile: String?): Call<Api<Token>>

    @Multipart
    @POST("register")
    fun register(@Part("fname") fname: RequestBody?,
                 @Part("lname") lname: RequestBody?,
                 @Part("email") email: RequestBody?,
                 @Part("mobile") mobile: RequestBody?,
                 @Part("address") address: RequestBody?,
                 @Part("location_id") location_id: RequestBody?,
                 @Part("business_name") business_name: RequestBody?,
                 @Part("real_state_description") real_state_description: RequestBody?,
                 @Part avatar: MultipartBody.Part?,
                 @Part real_state_image: MultipartBody.Part?): Call<Api<Token>>


    @GET("user/profile")
    fun getProfile(): Call<Api<ModelProfile>>


    @Multipart
    @POST("user/update")
    fun updateProfile(@Part("fname") fname: RequestBody?,
                      @Part("lname") lname: RequestBody?,
                      @Part("email") email: RequestBody?,
                      @Part("mobile") mobile: RequestBody?,
                      @Part("address") address: RequestBody?,
                      @Part("business_name") business_name: RequestBody?,
                      @Part("real_state_description") real_state_description: RequestBody?,
                      @Part avatar: MultipartBody.Part?,
                      @Part real_state_image: MultipartBody.Part?): Call<Api<List<Void>>>


    @GET("user/logout")
    fun logout(): Call<Api<List<Void>>>


    @Multipart
    @POST("user/homes/create")
    fun addHome(@Part("home_type_id") home_type_id: RequestBody?,
                @Part("ad_type_id") ad_type_id: RequestBody?,
                @Part("location_id") location_id: RequestBody?,
                @Part("lat") lat: RequestBody?,
                @Part("lng") lng: RequestBody?,
                @Part("rooms") rooms: RequestBody?,
                @Part("area") area: RequestBody?,
                @Part("price") price: RequestBody?,
                @Part("down_payment") down_payment: RequestBody?,
                @Part("monthly_payment") monthly_payment: RequestBody?,
                @Part("description") description: RequestBody?,
                @Part("phone") phone: RequestBody?,
                @Part("currency_id") currency: RequestBody?,
                @Part("neighborhood") neighborhood: RequestBody?,
                @Part images1: MultipartBody.Part?,
                @Part images2: MultipartBody.Part?,
                @Part images3: MultipartBody.Part?,
                @Part images4: MultipartBody.Part?,
                @Part images9: MultipartBody.Part?,
                @Part images10: MultipartBody.Part?,
                @Part images11: MultipartBody.Part?,
                @Part images12: MultipartBody.Part?,
                @Part images5: MultipartBody.Part?,
                @Part images6: MultipartBody.Part?,
                @Part images7: MultipartBody.Part?,
                @Part images8: MultipartBody.Part?
    ): Call<Api<ModelAddHome>>

    @Multipart
    @POST("user/home/update")
    fun updateHome(@Part("home_id") home_id: RequestBody?,
                   @Part("home_type_id") home_type_id: RequestBody?,
                   @Part("ad_type_id") ad_type_id: RequestBody?,
                   @Part("location_id") location_id: RequestBody?,
                   @Part("lat") lat: RequestBody?,
                   @Part("lng") lng: RequestBody?,
                   @Part("rooms") rooms: RequestBody?,
                   @Part("area") area: RequestBody?,
                   @Part("price") price: RequestBody?,
                   @Part("down_payment") down_payment: RequestBody?,
                   @Part("monthly_payment") monthly_payment: RequestBody?,
                   @Part("description") description: RequestBody?,
                   @Part("phone") phone: RequestBody?,
                   @Part("currency_id") currency: RequestBody?,
                   @Part("neighborhood") neighborhood: RequestBody?,
                   @Part images1: MultipartBody.Part?,
                   @Part images2: MultipartBody.Part?,
                   @Part images3: MultipartBody.Part?,
                   @Part images4: MultipartBody.Part?,
                   @Part images9: MultipartBody.Part?,
                   @Part images10: MultipartBody.Part?,
                   @Part images11: MultipartBody.Part?,
                   @Part images12: MultipartBody.Part?,
                   @Part images5: MultipartBody.Part?,
                   @Part images6: MultipartBody.Part?,
                   @Part images7: MultipartBody.Part?,
                   @Part images8: MultipartBody.Part?
    ): Call<Api<List<Void>>>


    @GET("home_types")
    fun getHomeType(@Query("lang") lang: String?): Call<Api<List<ModelHomeType>>>

    @GET("ad_types")
    fun getAdversType(): Call<Api<List<ModelAdversType>>>

    @POST("countries")
    fun getCountries(): Call<Api<List<ModelCountry>>>

    @POST("countries/{id}/cities")
    fun getCity(@Path("id") id: Int): Call<Api<ModelCountry>>

    @FormUrlEncoded
    @POST("user/homes")
    fun getListHome(@Field("page") page: Int?,
                    @Field("per_page") per_page: Int?,
                    @Field("lang") lang: String): Call<Api<List<ModelListHome>>>


    @FormUrlEncoded
    @POST("homes")
    fun searchHome(@Field("page") page: Int,
                   @Field("per_page") per_page: Int,
                   @Field("location_name") location_name: String?,
                   @Field("ad_type_id") ad_type_id: String?,
                   @Field("lang") lang: String,
                   @Field("location_id") location_id: String?,
                   @Field("user_id") user_id: String?,
                   @Field("id") id: Int?): Call<Api<List<ModelSearchHome>>>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "homes", hasBody = true)
    fun deletHome(@Field("home_id") home_id: Int?): Call<Api<List<Void>>>

    @GET("currencies")
    fun getCurrency(): Call<Api<List<ModelCurrency>>>

    @GET("settings/adv")
    fun getAdv(): Call<Api<List<String>>>


    @FormUrlEncoded
    @POST("users/device_token")
    fun pushToken(@Field("token") token: String,
                  @Field("device_id") device_id: String,
                  @Field("device_type") device_type: String): Call<Api<List<Void>>>


    @FormUrlEncoded
    @POST("/api/user/comments/create")
    fun addComment(@Field("model_id") home_id: String?,
                   @Field("text") text: String?,
                   @Field("reply_to") reply_to: String?,
                   @Field("model_type") status: String?
    ): Call<Api<Comment>>

    @FormUrlEncoded
    @POST("/api/home/comments")
    fun getComments(
            @Field("model_id") home_id: String?,
            @Field("per_page") per_page: Int,
            @Field("page") page_number: Int,
            @Field("model_type") status: String?
    ): Call<Api<List<Comment>>>

    @FormUrlEncoded
    @POST("/api/user/comments/replies")
    fun getCommentReply(
            @Field("comment_id") comment_id: String?,
            @Field("per_page") per_page: Int,
            @Field("page") page_number: Int
    ): Call<Api<List<Comment>>>

    @FormUrlEncoded
    @DELETE("/api/comments")
    fun deleteComments(@Field("comment_id") comment_id: String?): Call<Api<List<Void>>>

    @FormUrlEncoded
    @DELETE("/api/comments/read")
    fun readComment(@Field("comment_id") comment_id: String?): Call<Api<List<Void>>>

    @GET("/api/partners")
    fun getPartnes(): Call<Api<List<ModelPartners>>>

    @GET("/api/activities")
    fun getPlatform(@Query("lang") lang: String?): Call<Api<List<ModelPlatform>>>


    @Multipart
    @POST("/api/news")
    fun addNews(
            @Part("title") title: RequestBody,
            @Part("body") body: RequestBody,
            @Part image: MultipartBody.Part?
    ): Call<Api<List<Void>>>

    @FormUrlEncoded
    @POST("/api/homes/visits")
    fun sndItemVisibil(@Field("home_ids") item: String): Call<Api<List<Void>>>


    @GET("/api/users/{id}/news")
    fun getNewsUser(@Path("id") id: String): Call<Api<List<ModelNews>>>


    @Multipart
    @POST("news/{id}")
    fun editeNews(
            @Path("id") id: String,
            @Part("title") title: RequestBody,
            @Part("body") body: RequestBody,
            @Part cover: MultipartBody.Part?): Call<Api<List<Void>>>

    @DELETE("/api/news/{id}")
    fun deleteNews(@Path("id") id: String): Call<Api<List<Void>>>

    @GET("/api/settings/all")
    fun getSetting(@Query("lang") lang: String?): Call<Api<ModelSetting>>


    @POST("/api/homes/{idHome}/favorites")
    fun favorites(@Path("idHome") id: Int): Call<Api<List<Void>>>


    @DELETE("/api/homes/{idHome}/favorites")
    fun favoritesDelete(@Path("idHome") id: Int): Call<Api<List<Void>>>

}