package com.alaan.outn.api.remote.utils

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Timer
import java.util.TimerTask

abstract class RetryableCallback<T> : Callback<T> {
    private var maxRetry = 0
    private var maxTimerRetry = 4
    private val timerLoopTime = 3000
    private var retryCount = 0
    private var timerTryCount = 0
    private var timer: Timer? = null
    private var call: Call<T>? = null

    constructor() {}
    constructor(maxRetry: Int) {
        this.maxRetry = maxRetry
    }

    constructor(maxRetry: Int, maxTimerRetry: Int) {
        this.maxRetry = maxRetry
        this.maxTimerRetry = maxTimerRetry
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        timeOutFun(null)
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        timeOutFun(t)
        this.call = call
        if (retryCount++ < maxRetry) {
            Log.v(
                TAG,
                "Retrying... ($retryCount out of $maxRetry)"
            )
            retry()
        } else {
            if (maxRetry > 0 && timer == null) {
                timer = Timer()
                timer!!.schedule(object : TimerTask() {
                    override fun run() {
                        timerTryCount++
                        if (timerTryCount < maxTimerRetry) {
                            Log.v(
                                TAG,
                                "Retrying Timer ... ($timerTryCount)"
                            )
                            retry()
                        } else {
                            if (timer != null) {
                                timer!!.cancel()
                                timer = null
                            }
                        }
                    }
                }, timerLoopTime.toLong(), timerLoopTime.toLong())
            }
        }
    }

    private fun retry() {
        call!!.clone().enqueue(this)
    }

    private fun timeOutFun(t: Throwable?) { /*Utils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (t == null) {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.DisconnectNet, false);
                    return;
                }
                if (t instanceof java.net.SocketException || t instanceof java.net.SocketTimeoutException || t instanceof UnknownHostException) {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.DisconnectNet, true);
                }
            }
        });*/
    }

    companion object {
        private val TAG = RetryableCallback::class.java.simpleName
        const val DEFAULT = 2
    }
}