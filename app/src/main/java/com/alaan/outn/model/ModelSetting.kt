package com.alaan.outn.model

import com.google.gson.annotations.SerializedName

data class ModelSetting(

        @field:SerializedName("vision")
        val vision: ModelVision? = null
//        @field:SerializedName("key")
//        val key: String? = null,
//        @field:SerializedName("value")
//        val value: String? = null,
//        @field:SerializedName("created_at")
//        val created_at: String? = null,
//        @field:SerializedName("updated_at")
//        val updated_at: String? = null

)


data class ModelVision (
        @field:SerializedName("en")
        val en:String? = null,
        @field:SerializedName("ar")
        val ar:String? = null,
        @field:SerializedName("ku")
        val ku:String? = null
)