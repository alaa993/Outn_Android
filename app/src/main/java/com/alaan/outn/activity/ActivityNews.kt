package com.alaan.outn.activity

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alaan.outn.R
import com.alaan.outn.adapter.AdapterActivityNews
import com.alaan.outn.api.Repository
import com.alaan.outn.api.interfaces.CallBack
import com.alaan.outn.interfac.InterFaceActivityNews
import com.alaan.outn.model.*
import com.alaan.outn.utils.Preference
import com.alaan.outn.utils.Utils
import kotlinx.android.synthetic.main.show_activity_realstate.*

class ActivityNews : AppCompatActivity(), InterFaceActivityNews {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: AdapterActivityNews
    private lateinit var gridLayoutManager: GridLayoutManager
    var listNews = mutableListOf<ModelNews>()
    var imageRealState = false;
    var imagePathRealState = ""
    var mCurrentPhotoPath: String? = null
    var imagePath: String? = null
    var newModel: ModelNews? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.setFillWindowAndTransparetStatusBar(this)
        setContentView(R.layout.activity_news)
        Utils.changeLanuge(this)
        readView()
        getData()
        val viewModel = ViewModelProvider(this).get(NewsActivityViewModel::class.java)
        viewModel._result.observe(this, androidx.lifecycle.Observer {result ->
            result?.let {
//                toast(getString(R.string.success_request))
                getData()
                viewModel.doneResult()
            }
        })
    }

    fun readView() {

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        adapter = AdapterActivityNews(this, listNews, this)
        recyclerView.adapter = adapter
        gridLayoutManager = GridLayoutManager(this, 2)

        btnBackRealState.setOnClickListener {
            finish()
        }

    }

    fun getData() {

        showLoading()
        Preference.idUser?.let {
            Repository().getInstance()?.getNewsUser(it, object : CallBack<Api<List<ModelNews>>>() {
                override fun onSuccess(t: Api<List<ModelNews>>) {
                    super.onSuccess(t)
                    hideLoading()
                    listNews.clear()
                    t.data?.let { it1 -> listNews.addAll(it1) }
                    adapter.notifyDataSetChanged()

                }

                override fun onFail(e: Exception, code: Int) {
                    super.onFail(e, code)
                    hideLoading()
                }

            })
        }
    }

    override fun delet(modelNews: ModelNews) {
        showLoading()
        Repository().getInstance()?.deleteNews("" + modelNews.id, object : CallBack<Api<List<Void>>>() {
            override fun onSuccess(t: Api<List<Void>>) {
                super.onSuccess(t)
                hideLoading()
                getData()
            }

            override fun onFail(e: Exception, code: Int) {
                super.onFail(e, code)
                hideLoading()
                getData()
            }
        })

    }

    override fun edit(modelNews: ModelNews) {
        EditNewsFragment.newInstance(NewsActivityViewModel::class.java, modelNews).show(supportFragmentManager, EditNewsFragment.TAG)
    }

    override fun comment(modelNews: ModelNews) {

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
