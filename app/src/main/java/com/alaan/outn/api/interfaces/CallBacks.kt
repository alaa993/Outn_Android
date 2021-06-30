package com.alaan.outn.api.interfaces

import java.util.concurrent.atomic.AtomicInteger

interface CallBacks<T> {
    fun onSuccess(t:T)
    fun onFail(e:Exception,code:Int)
}