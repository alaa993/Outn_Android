package com.alaan.outn.model

import com.google.gson.annotations.SerializedName
import retrofit2.http.Field
import java.io.Serializable

data class ModelPlatform(
        @Field("id")
        val id: Int,
        @Field("user_id")
        val user_id: Int,
        @Field("type")
        val type: String,
        @Field("comments_count")
        val comments_count: Int,
        @Field("user")
        val user: String,
        @Field("description")
        val description: String,
        @Field("created_at")
        val created_at: String,
        @Field("elapsed_time")
        val elapsed_time: String,
        @Field("title")
        val title: String,
        @field:SerializedName("fname")
        val fname: String,
        @field:SerializedName("lname")
        val lname: String,
        @field:SerializedName("business_name")
        val business_name: String,
        @field:SerializedName("real_state_description ")
        val real_state_description: String? = null,
        @field:SerializedName("real_state_image ")
        val image:String,
        @field:SerializedName("cover")
        @Field("cover")
        val cover:String

) : Serializable