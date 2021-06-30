package com.alaan.outn.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ModelListHome(
		@field:SerializedName("area")
		val area: Int? = null,

		@field:SerializedName("ad_type_id")
		val adTypeId: Int? = null,

		@field:SerializedName("rooms")
		val rooms: String? = null,

		@field:SerializedName("images")
		val images: List<ImagesItem>? = null,

		@field:SerializedName("neighborhood_images")
		val neighborhood_images: List<ImagesItem>? = null,

		@field:SerializedName("new_comments")
		val newComments: Int? = null,

		@field:SerializedName("ad_type")
		val adType: String? = null,

		@field:SerializedName("description")
		val description: String? = null,

		@field:SerializedName("neighborhood")
		val neighborhood: String? = null,


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

		@field:SerializedName("country_id")
		val country_id: Int? = null,

		@field:SerializedName("monthly_payment")
		val monthlyPayment: String? = null,

		@field:SerializedName("currency")
         val currency: Currency? = null,

		@field:SerializedName("phone")
		val phone: String? = null,

		@field:SerializedName("location_id")
		val location_id: String? = null,

		@field:SerializedName("favorites_count")
		val favorites_count: Int? = null,

		@field:SerializedName("is_favorited")
		val is_favorited: Boolean? = null,

		@field:SerializedName("comments_count")
		val comments_count: Int? = null,

		@field:SerializedName("view_count")
		val view_count: Int? = null,

		@field:SerializedName("md")
		val md: String? = null,

		@field:SerializedName("xs")
		val xs: String? = null



):Serializable
