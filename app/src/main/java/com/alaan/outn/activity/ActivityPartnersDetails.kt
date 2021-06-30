package com.alaan.outn.activity

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.TextView
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
import com.alaan.outn.model.ModelPartners
import com.alaan.outn.model.ModelSearchHome
import com.alaan.outn.utils.Preference
import com.alaan.outn.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_list_home.*
import kotlinx.android.synthetic.main.activity_list_home.btnBack
import kotlinx.android.synthetic.main.activity_list_home.recyclerView
import kotlinx.android.synthetic.main.activity_partners_details.*
import kotlinx.android.synthetic.main.activity_partners_details.title_app
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.backgroundColorResource

class ActivityPartnersDetails :AppCompatActivity(),interFaceFavorite{

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: AdapterSearchResult
    private lateinit var gridLayoutManager: GridLayoutManager
    var listHome = mutableListOf<ModelSearchHome>()
    var search:String? = null
    var idLocation:String? = null
    var ad_type_id = 0
    var user_id:String? = null
    var id:Int? = null
    var locationName:String? = null
   var  modelPartners:ModelPartners? = null
    val itemVisibl:MutableList<Int> = ArrayList<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partners_details)
        Utils.setFillWindowAndTransparetStatusBar(this)
        Utils.changeLanuge(this)

        val extras: Bundle? = intent.extras;
        if (extras != null) {

            modelPartners = extras.getSerializable("data") as ModelPartners?
            user_id = modelPartners?.user_id.toString()
            id = modelPartners?.id
        }

        readView()
        getData()
        MobileAds.initialize(this)

    }

    fun readView() {

        txt_fullName.text = if (modelPartners?.fname != null) modelPartners?.fname else "" + " "+ if (modelPartners?.lname != null) modelPartners?.lname else ""
        txt_terand.text = if (modelPartners?.business_name != null) modelPartners?.business_name else ""
        txt_desc_real_state.text = modelPartners?.real_state_description
        tvAddress.text = modelPartners?.address ?: ""
        txt_phone.text = modelPartners?.mobile
        txt_desc.text = modelPartners?.real_state_description
        Glide.with(this)
                .load(modelPartners?.image)
                .into(image_ternd)

        if (modelPartners?.image != null) {
            Glide.with(this)
                    .load(modelPartners?.avatar)
                    .placeholder(R.drawable.profile)
                    .error(R.drawable.profile)
                    .into(imageProfile)
        }

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView_partnes.layoutManager = linearLayoutManager
        adapter = AdapterSearchResult(this, listHome,this)
        recyclerView_partnes.adapter = adapter
        gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView_partnes.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                Log.d("addOnScrollListener",""+linearLayoutManager.findLastVisibleItemPosition())
                val value =  listHome.get(linearLayoutManager.findLastVisibleItemPosition()).id
                if (itemVisibl.contains(value)){
                    return
                }else {
                    itemVisibl.add(value!!)
                    sndItemVisibil(value)
                }


            }

        })

        val spannable = SpannableString("outn")
        spannable.setSpan(
                ForegroundColorSpan(Color.rgb(244, 0, 12)),
                3,
                4,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        title_app.setText(spannable)

        btnBack.setOnClickListener {
            finish()
        }
    }

    fun getData() {

        showLoading()
        var ad:String? = null
        if (ad_type_id == 0) {
            ad = null
        }else {
            ad = ad_type_id.toString()
        }
        itemVisibl.removeAll { true }

        Repository().getInstance()?.searchHome(1, 200, locationName, ad, Preference.language!!,idLocation,user_id,id, object : CallBack<Api<List<ModelSearchHome>>>() {

            override fun onSuccess(t: Api<List<ModelSearchHome>>) {
                super.onSuccess(t)
                t.data?.let { listHome.addAll(it) }
                if (listHome.size>0){
                    listHome[0].id?.let { itemVisibl.add(it) }
                    sndItemVisibil(itemVisibl.get(0))
                }
                adapter.notifyDataSetChanged()
                hideLoading()
            }

            override fun onFail(e: Exception, code: Int) {
                super.onFail(e, code)
                hideLoading()
            }
        })
    }

    fun sndItemVisibil(value:Int){

        Repository().getInstance()?.sndItemVisibil(value.toString(),object :CallBack<Api<List<Void>>>(){

            override fun onSuccess(t: Api<List<Void>>) {
                super.onSuccess(t)
            }

        })


    }

    override fun favorites(id: Int) {

        if (Preference.isLogin!!) {

            showLoading()
            Repository().getInstance()?.favorites(id,object:CallBack<Api<List<Void>>>(){

                override fun onSuccess(t: Api<List<Void>>) {
                    super.onSuccess(t)
                    hideLoading()
                }

                override fun onFail(e: Exception, code: Int) {
                    super.onFail(e, code)
                    hideLoading()
                }
            })
        } else {
            intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }



    }

    override fun favoritesDelete(id: Int) {

        if (Preference.isLogin!!) {
            showLoading()
            Repository().getInstance()?.favoritesDelete(id,object:CallBack<Api<List<Void>>>(){
                override fun onSuccess(t: Api<List<Void>>) {
                    super.onSuccess(t)
                    hideLoading()
                }

                override fun onFail(e: Exception, code: Int) {
                    super.onFail(e, code)
                    hideLoading()
                }
            })
        } else {
            intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
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
