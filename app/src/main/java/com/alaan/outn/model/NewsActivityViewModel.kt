package com.alaan.outn.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewsActivityViewModel : ViewModel() {

    val _result = MutableLiveData<String>()

    fun doneResult() {
        _result.value = null
    }

}