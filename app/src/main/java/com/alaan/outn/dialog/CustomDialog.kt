package com.alaan.outn.dialog

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.alaan.outn.R

class CustomDialog : Dialog {

    constructor(context: Context?) : super(context!!) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_dialog)
    }


}