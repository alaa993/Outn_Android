package com.alaan.outn.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Location(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("state")
	val city: String? = null
): Serializable