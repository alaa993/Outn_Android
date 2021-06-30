package com.alaan.outn.model

import com.google.gson.annotations.SerializedName

class Api<T> {

    @SerializedName("status")
    var status: Status? = null

    @SerializedName("data")
    var data:T? = null


}