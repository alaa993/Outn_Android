package com.alaan.outn.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Status(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
): Serializable