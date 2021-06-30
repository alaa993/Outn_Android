package com.alaan.outn.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ModelAdversType(

        @field:SerializedName("id")
        val id: Int = 0,
        @field:SerializedName("title")
        val title: String?
): Serializable