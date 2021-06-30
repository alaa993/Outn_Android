package com.alaan.outn.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.net.Inet4Address

data class ModelPartners(

        @field:SerializedName("id")
        var id:Int,
        @field:SerializedName("user_id")
        var user_id:Int,
        @field:SerializedName("fname")
        var fname:String,
        @field:SerializedName("lname")
        var lname:String,
        @field:SerializedName("business_name")
        var business_name:String,
        @field:SerializedName("address")
        val address: String,
        @field:SerializedName("email")
        val email:String,
        @field:SerializedName("mobile")
        val mobile:String,
        @field:SerializedName("avatar")
        val avatar:String,
        @field:SerializedName("image")
        var image:String,
        @field:SerializedName("location")
        val location: Location? = null,
        @field:SerializedName("created_at")
        val createdAt: String? = null,
        @field:SerializedName("real_state_description")
        var real_state_description: String? = null,
        @field:SerializedName("vision")
        val vision: String? = null,
        @field:SerializedName("description")
        val description: String? = null

):Serializable