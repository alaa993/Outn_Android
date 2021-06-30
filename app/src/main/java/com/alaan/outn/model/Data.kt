package com.alaan.outn.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Data(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
): Serializable