package com.alaan.outn.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alaan.outn.R
import com.alaan.outn.adapter.AdapterShowActivityPlatform
import com.alaan.outn.api.Repository
import com.alaan.outn.api.interfaces.CallBack
import com.alaan.outn.interfac.InterFaceShowActivityRealState
import com.alaan.outn.model.Api
import com.alaan.outn.model.ModelPartners
import com.alaan.outn.model.ModelPlatform
import com.alaan.outn.model.ShowActivityViewModel
import com.alaan.outn.utils.Preference
import com.alaan.outn.utils.Utils
import com.bumptech.glide.Glide
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.show_activity_realstate.*
import kotlinx.android.synthetic.main.show_matn_outnplatform.view.*

class ShowActivityPlatform : AppCompatActivity(), InterFaceShowActivityRealState {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: AdapterShowActivityPlatform
    private lateinit var gridLayoutManager: GridLayoutManager
    var listHome = mutableListOf<ModelPlatform>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.setFillWindowAndTransparetStatusBar(this)
        setContentView(R.layout.show_activity_realstate)

        readView()
        getData()
        MobileAds.initialize(this)

        val viewModel = ViewModelProvider(this).get(ShowActivityViewModel::class.java)
        viewModel._result.observe(this, Observer {result ->
            result?.let {
//                toast(getString(R.string.success_request))
                getData()
                viewModel.donePhotoPatch()
            }
        })
    }

    fun readView() {
//        dialog = Dialog(this)
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        adapter = AdapterShowActivityPlatform(this, listHome, this)
        recyclerView.adapter = adapter
        gridLayoutManager = GridLayoutManager(this, 2)

        btnBackRealState.setOnClickListener {
            finish()
        }

        txt_add_news.setOnClickListener {
            addNews()
        }

    }


    fun getData() {

        showLoading()
        Repository().getInstance()?.getPlatform(Preference.language, object : CallBack<Api<List<ModelPlatform>>>() {

            override fun onSuccess(t: Api<List<ModelPlatform>>) {
                super.onSuccess(t)
                hideLoading()
                listHome.removeAll { true }
                t.data?.let { listHome.addAll(it) }
                adapter.notifyDataSetChanged()
            }

            override fun onFail(e: Exception, code: Int) {
                super.onFail(e, code)
                hideLoading()
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

    fun addNews() {
        AddNewsFragment.newInstance(ShowActivityViewModel::class.java).show(supportFragmentManager, AddNewsFragment.TAG)
    }


    override fun more(model: ModelPlatform) {
        dialogMore(model)
    }

    override fun share(modelNews: ModelPlatform) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, "${modelNews.title} \n ${modelNews.cover} \n ${modelNews.description}");
        startActivity(Intent.createChooser(shareIntent, getString(R.string.send_to)))
    }

    override fun detail(model: ModelPlatform) {

        if (model.type.equals("home")) {
            model.id
            val modelPartners: ModelPartners? = ModelPartners(
                    model.id,
                    model.user_id,
                    model.user, "",
                    model.business_name,
                    "",
                    "",
                    "",
                    "",
                    model.image,
                    null,
                    "",
                    model.real_state_description,
                    "",
                    "")

            intent = Intent(applicationContext, ActivityPartnersDetails::class.java)
            intent.putExtra("data", modelPartners)
            startActivity(intent)

        } else {
            dialogMore(model)
        }

    }

    override fun comment(model: ModelPlatform) {
        val intent = Intent(this, FragmentComment::class.java)
        intent.putExtra("homeId", model.id)
        intent.putExtra("model_type", model.type)
        intent.putExtra("post", model)
        startActivity(intent)
    }

    fun dialogMore(model: ModelPlatform) {

        val dialog = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.show_matn_outnplatform, null)
        dialog.setCancelable(false)
        dialog.setView(dialogView)
        dialogView.txt_title.text = model.title
        dialogView.txt_desc.text = model.description
        dialogView.txt_desc.movementMethod = ScrollingMovementMethod()
        Glide.with(this)
                .load(model.cover)
                .placeholder(R.mipmap.ic_launcher) //placeholder
                .error(R.mipmap.ic_launcher) //error
                .into(dialogView.img);
        val alertDialog = dialog.create()
        alertDialog.show()
        dialogView.img_close.setOnClickListener {
            alertDialog.dismiss()
        }

    }
}