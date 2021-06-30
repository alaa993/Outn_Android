package com.alaan.outn.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(

	@field:SerializedName("fname")
	val fname: String? = null,

	@field:SerializedName("lname")
	val lname: String? = null,

	@field:SerializedName("mobile")
	val mobile: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("avatar")
	val avatar: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("business_name")
     val business_name: String? = null


): Serializable