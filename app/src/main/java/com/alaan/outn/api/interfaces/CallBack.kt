package com.alaan.outn.api.interfaces

import com.alaan.outn.model.Api
import com.alaan.outn.model.Token
import retrofit2.Call
import retrofit2.Callback

abstract class CallBack<T>:CallBacks<T> {

    override fun onSuccess(t: T) {

    }

    override fun onFail(e: Exception, code: Int) {

    }


}