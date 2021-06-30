package com.alaan.outn.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Currency(

	@field:SerializedName("data")
	val data: Data? = null
):Serializable