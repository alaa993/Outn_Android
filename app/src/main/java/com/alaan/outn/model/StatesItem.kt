package com.alaan.outn.model
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StatesItem (

	@field:SerializedName("cities")
	val cities: List<CitiesItem?>? = null,

	@field:SerializedName("level")
	val level: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
):Serializable