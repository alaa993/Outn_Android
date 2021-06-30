package com.alaan.outn.model

import com.alaan.outn.interfac.ListItem
import com.google.gson.annotations.SerializedName

class Country : ListItem {
    @SerializedName("alpha2Code")
    override var id: String? = null
    @SerializedName("callingCodes")
    var callingCodes: List<String>? = null
    @SerializedName("name")
    override var name: String? = null
    @SerializedName("states")
    override var states: List<String>? = null
}