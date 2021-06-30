package com.alaan.outn.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ModelAdv(

        @field:SerializedName("path")
        val path: List<String>?

): Serializable