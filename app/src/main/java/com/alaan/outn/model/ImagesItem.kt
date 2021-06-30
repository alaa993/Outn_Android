package com.alaan.outn.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ImagesItem(

	@field:SerializedName("path")
	val path: String? = null,

	@field:SerializedName("home_id")
	val homeId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("md")
	val md: String? = null,

	@field:SerializedName("xs")
	val xs: String? = null

):Serializable