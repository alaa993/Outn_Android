package com.alaan.outn.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ModelSearchHome(
        @field:SerializedName("user")
        val user: User? = null,

        @field:SerializedName("area")
        val area: String? = null,

        @field:SerializedName("ad_type_id")
        val adTypeId: Int? = null,

        @field:SerializedName("rooms")
        val rooms: String? = null,

        @field:SerializedName("images")
        val images: List<ImagesItem>? = null,

        @field:SerializedName("neighborhood_images")
        val neighborhood_images: List<ImagesItem>? = null,

        @field:SerializedName("neighborhood")
        val neighborhood: String? = null,

        @field:SerializedName("new_comments")
        val newComments: Int? = null,

        @field:SerializedName("ad_type")
        val adType: String? = null,

        @field:SerializedName("description")
        val description: String? = null,

        @field:SerializedName("created_at")
        val createdAt: String? = null,

        @field:SerializedName("down_payment")
        val downPayment: String? = null,

        @field:SerializedName("home_type_id")
        val homeTypeId: Int? = null,

        @field:SerializedName("price")
        val price: String? = null,

        @field:SerializedName("location")
        val location: Location? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("home_type")
        val homeType: String? = null,

        @field:SerializedName("status")
        val status: String? = null,

        @field:SerializedName("monthly_payment")
        val monthlyPayment: String? = null,

        @field:SerializedName("phone")
        val phone: String? = null,

        @field:SerializedName("comments_count")
        val comments_count: Int? = null,

        @field:SerializedName("view_count")
        val view_count: Int? = null,

        @field:SerializedName("currency")
        val currency: Currency? = null,

        @field:SerializedName("favorites_count")
        var favorites_count: Int? = null,

        @field:SerializedName("is_favorited")
        var is_favorited: Boolean? = false

) : Serializable