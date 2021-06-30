package com.alaan.outn.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Comment(

        @field :SerializedName("created_at")
        val createdAt: String,

        @field :SerializedName("id")
        val id: Int,

        @field :SerializedName("text")
        val text: String,

        @field :SerializedName("reply_to")
        val reply_to: String,

        @field :SerializedName("has_reply")
        val hasReply: Int,

        @field :SerializedName("home_id")
        val home_id: String,

        @field:SerializedName("user")
        val user: User

) : Serializable {

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val comment: Comment = o as Comment

        return id == comment.id
    }

    override fun hashCode(): Int {
        return id
    }

}