package com.alaan.outn.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alaan.outn.R
import com.alaan.outn.adapter.AdapterListHome
import com.alaan.outn.api.Repository
import com.alaan.outn.api.interfaces.CallBack
import com.alaan.outn.interfac.InterFaceListHome
import com.alaan.outn.model.ModelListHome
import com.alaan.outn.model.Api
import com.alaan.outn.model.ModelSearchHome
import com.alaan.outn.model.Status
import com.alaan.outn.utils.Preference
import com.alaan.outn.utils.Utils
import kotlinx.android.synthetic.main.activity_list_home.*
import org.jetbrains.anko.toast

class ListHomeActivity : AppCompatActivity() ,InterFaceListHome{

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: AdapterListHome
    private lateinit var gridLayoutManager: GridLayoutManager
    var listHome = mutableListOf<ModelListHome>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.setFillWindowAndTransparetStatusBar(this)
        setContentView(R.layout.activity_list_home)
        Utils.changeLanuge(this)
        readView()

    }


    fun readView() {

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        adapter = AdapterListHome(this, listHome,this)
        recyclerView.adapter = adapter
        gridLayoutManager = GridLayoutManager(this, 2)
        btnBack.setOnClickListener{
            finish()
        }

    }

    fun getData() {

        showLoading()
        Repository().getInstance()?.getListHome(1, 100, Preference.language!! ,object : CallBack<Api<List<ModelListHome>>>() {

            override fun onSuccess(t: Api<List<ModelListHome>>) {
                super.onSuccess(t)
                hideLoading()

                listHome.clear()
                t.data?.let { listHome.addAll(it) }
                adapter.notifyDataSetChanged()

            }

            override fun onFail(e: Exception, code: Int) {
                super.onFail(e, code)
                hideLoading()
            }


        })

    }

    override fun edit(edit: ModelListHome) {
        if (edit.adTypeId == 1){
            intent = Intent(applicationContext,RentActivity::class.java);
            intent.putExtra("model",edit)
            startActivity(intent)
        }else {
            intent = Intent(applicationContext,SaleActivity::class.java);
            intent.putExtra("model",edit)
            startActivity(intent)
        }


    }

    override fun delet(delet: ModelListHome) {

        showLoading()
        val id = delet.id

        Repository().getInstance()?.deletHome(id!!,object :CallBack<Api<List<Void>>>(){

            override fun onSuccess(t: Api<List<Void>>) {
                super.onSuccess(t)
                hideLoading()
                if (t.status?.code == 200){
                    t.status?.message?.let { toast(it) }
                    getData()

                }
            }

            override fun onFail(e: Exception, code: Int) {
                super.onFail(e, code)
                hideLoading()
            }

        })

    }

    override fun onResume() {
        super.onResume()
        getData()
    }


    override fun share(comment: ModelListHome) {

        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "ssess")
           val shareMessage =  "http://www.outn.net/homes?id="+Preference.idUser
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "choose one"))
        } catch (e: java.lang.Exception) { //e.toString();
        }


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