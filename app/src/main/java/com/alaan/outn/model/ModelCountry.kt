package com.alaan.outn.model


import com.alaan.outn.interfac.ListItem
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ModelCountry(

        @field:SerializedName("alpha2Code")
        val alpha2Code: String? = null,

        @field:SerializedName("level")
        val level: Int? = null,

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("states")
        val states: List<StatesItem?>? = null
): Serializable


