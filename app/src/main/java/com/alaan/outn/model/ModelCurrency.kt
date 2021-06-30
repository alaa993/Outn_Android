package com.alaan.outn.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ModelCurrency (

    @SerializedName("id")
     var id: Int = 0,
    @SerializedName("name")
     var name: String? = null

): Serializable