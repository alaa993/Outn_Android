package com.alaan.outn.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ModelAddHome (

        @field:SerializedName("home_id")
       val home_id:Int = 0
): Serializable