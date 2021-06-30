package com.alaan.outn.model

import com.google.gson.annotations.SerializedName

data class ModelNews(
        @field:SerializedName("id")
        val id:Int?,
        @field:SerializedName("title")
        val title:String,
        @field:SerializedName("body")
        val body:String,
        @field:SerializedName("created_at")
        val created_at:String,
        @field:SerializedName("cover")
        val cover:String
)