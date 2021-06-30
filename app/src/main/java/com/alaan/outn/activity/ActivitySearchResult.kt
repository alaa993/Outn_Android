package com.alaan.outn.activity

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.AbsListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alaan.outn.R
import com.alaan.outn.adapter.AdapterSearchResult
import com.alaan.outn.api.Repository
import com.alaan.outn.api.interfaces.CallBack
import com.alaan.outn.interfac.interFaceFavorite
import com.alaan.outn.model.Api
import com.alaan.outn.model.ModelSearchHome
import com.alaan.outn.utils.Preference
import com.alaan.outn.utils.Utils
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_list_home.*
import kotlinx.android.synthetic.main.activity_list_home.btnBack
import kotlinx.android.synthetic.main.activity_list_home.recyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search_result.*
import org.jetbrains.anko.db.NULL
import java.lang.reflect.Array.get

class ActivitySearchResult : AppCompatActivity(), interFaceFavorite {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: AdapterSearchResult
    private lateinit var gridLayoutManager: GridLayoutManager
    var listHome = mutableListOf<ModelSearchHome>()
    var search: String? = null
    var idLocation: String? = null
    var ad_type_id = 0
    var user_id: String? = null
    var id: Int? = null
    var locationName: String? = null
    val itemVisibl: MutableList<Int> = ArrayList<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.setFillWindowAndTransparetStatusBar(this)

        setContentView(R.layout.activity_search_result)
        Utils.changeLanuge(this)
        readView()

        val action: String? = intent?.action
        val data: Uri? = intent?.data
        user_id = data?.getQueryParameter("id")

        val extras: Bundle? = intent.extras;
        if (extras != null) {
            search = extras.getString("search");
            ad_type_id = extras.getInt("ad_type_id")
            idLocation = extras.getString("location")
            id = extras.getInt("id")
        }


        val spannable = SpannableString("outn")
        spannable.setSpan(
                ForegroundColorSpan(Color.rgb(244, 0, 12)),
                3,
                4,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        txt_brand.text = spannable

        getData()
    }

    fun readView() {

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        adapter = AdapterSearchResult(this, listHome, this)
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val value = listHome[linearLayoutManager.findLastVisibleItemPosition()].id
                if (itemVisibl.contains(value)) {
                    return
                } else {
                    itemVisibl.add(value!!)
                    sndItemVisibil(value)
                }
            }
        })
        gridLayoutManager = GridLayoutManager(this, 2)

        btnBack.setOnClickListener {
            finish()
        }
    }

    fun getData() {

        showLoading()
        var ad: String? = null
        if (ad_type_id == 0) {
            ad = null
        } else {
            ad = ad_type_id.toString()
        }

        Repository().getInstance()?.searchHome(1, 200, locationName, ad, Preference.language!!, idLocation, user_id, id, object : CallBack<Api<List<ModelSearchHome>>>() {

            override fun onSuccess(t: Api<List<ModelSearchHome>>) {
                super.onSuccess(t)
                hideLoading()
                t.data?.let { listHome.addAll(it) }
                if (listHome.size > 0) {
                    listHome.get(0).id?.let { itemVisibl.add(it) }
                    sndItemVisibil(itemVisibl.get(0))
                }
                adapter.notifyDataSetChanged()
            }

            override fun onFail(e: Exception, code: Int) {
                super.onFail(e, code)
                hideLoading()
            }
        })
    }

    override fun favorites(id: Int) {

        showLoading()
        Repository().getInstance()?.favorites(id, object : CallBack<Api<List<Void>>>() {

            override fun onSuccess(t: Api<List<Void>>) {
                super.onSuccess(t)
                hideLoading()
            }

            override fun onFail(e: Exception, code: Int) {
                super.onFail(e, code)
                hideLoading()
            }
        })

    }

    override fun favoritesDelete(id: Int) {

        showLoading()
        Repository().getInstance()?.favoritesDelete(id, object : CallBack<Api<List<Void>>>() {
            override fun onSuccess(t: Api<List<Void>>) {
                super.onSuccess(t)
                hideLoading()
            }

            override fun onFail(e: Exception, code: Int) {
                super.onFail(e, code)
                hideLoading()
            }
        })


    }


    fun sndItemVisibil(value: Int) {

        Repository().getInstance()?.sndItemVisibil(value.toString(), object : CallBack<Api<List<Void>>>() {

            override fun onSuccess(t: Api<List<Void>>) {
                super.onSuccess(t)
            }

            override fun onFail(e: Exception, code: Int) {
                super.onFail(e, code)
                Log.d("sndItemVisibil", "onFail")
            }

        })


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
