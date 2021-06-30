package com.alaan.outn.activity

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alaan.outn.R
import com.alaan.outn.adapter.AdapterPartners
import com.alaan.outn.api.Repository
import com.alaan.outn.api.interfaces.CallBack
import com.alaan.outn.interfac.InterFacePartnes
import com.alaan.outn.model.Api
import com.alaan.outn.model.ModelPartners
import com.alaan.outn.utils.Preference
import kotlinx.android.synthetic.main.activity_partners.*
import kotlinx.android.synthetic.main.show_activity_realstate.*
import kotlinx.android.synthetic.main.show_activity_realstate.btnBackRealState
import kotlinx.android.synthetic.main.show_activity_realstate.recyclerView

class ActivityPartners : AppCompatActivity(), InterFacePartnes {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: AdapterPartners
    private lateinit var gridLayoutManager: GridLayoutManager
    var list = mutableListOf<ModelPartners>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partners)
        readView()
        getData()

    }

    fun readView() {

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        adapter = AdapterPartners(this, list, this)
        recyclerView.adapter = adapter
        gridLayoutManager = GridLayoutManager(this, 2)

        btnBackRealState.setOnClickListener {
            finish()
        }

        val spannable = SpannableString("outn")
        spannable.setSpan(
                ForegroundColorSpan(Color.rgb(244, 0, 12)),
                3,
                4,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        txt_brand_partner.text = spannable

    }


    fun getData() {

        showLoading()
        Repository().getInstance()?.getPartnes(object : CallBack<Api<List<ModelPartners>>>() {

            override fun onSuccess(t: Api<List<ModelPartners>>) {
                super.onSuccess(t)
                hideLoading()
                t.data?.let { list.addAll(it) }
                adapter.notifyDataSetChanged()
            }

            override fun onFail(e: Exception, code: Int) {
                super.onFail(e, code)
                hideLoading()
            }
        })
    }

    override fun more(model: ModelPartners) {
        intent = Intent(applicationContext, ActivityPartnersDetails::class.java)
        intent.putExtra("data", model)
        startActivity(intent)
    }


    var progressDialog: ProgressDialog? = null

    private fun showLoading() {
        progressDialog = ProgressDialog(this)
        progressDialog!!.setCancelable(false)
        progressDialog!!.setMessage(getString(R.string.pls_wait))
        progressDialog!!.show()
    }

    private fun hideLoading() {
        if (progressDialog != null) {
            if (progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
            }
        }
    }
}