package com.alaan.outn.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShowActivityViewModel : ViewModel() {

    val _result = MutableLiveData<String>()

    fun donePhotoPatch() {
        _result.value = null
    }

}