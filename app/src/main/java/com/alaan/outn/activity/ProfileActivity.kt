package com.alaan.outn.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alaan.outn.R
import com.alaan.outn.adapter.AdapterProfile
import com.alaan.outn.api.Repository
import com.alaan.outn.api.interfaces.CallBack
import com.alaan.outn.interfac.interface_profile
import com.alaan.outn.model.Api
import com.alaan.outn.utils.EqualSpacingItemDecoration
import com.alaan.outn.utils.Preference
import com.alaan.outn.utils.Utils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity(), interface_profile, View.OnClickListener {

    val item = arrayListOf<String>()
    var adapter: AdapterProfile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.setFillWindowAndTransparetStatusBar(this)
        setContentView(R.layout.activity_profile)
        Utils.changeLanuge(this)
        initData()
        readView()
        functionView()


    }


    fun initData(){

        item.add(resources.getString(R.string.add_sale))
        item.add(resources.getString(R.string.add_rent))
        item.add(resources.getString(R.string.list_home))
        item.add(resources.getString(R.string.change_language))
        item.add(resources.getString(R.string.share_app))
        item.add(resources.getString(R.string.news))
        item.add(resources.getString(R.string.help))
        item.add(resources.getString(R.string.about))
        item.add(resources.getString(R.string.exit))


    }

    private fun readView() {

        adapter = AdapterProfile(this, item, this)
        val manager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
        rc_profile.addItemDecoration(EqualSpacingItemDecoration(12));
        rc_profile.layoutManager = manager
        rc_profile.adapter = adapter

        btnBack.setOnClickListener(this)

    }

    private fun functionView() {

        img_edit_profile.setOnClickListener(this)


    }

    override fun onResume() {
        super.onResume()

        val fullName = Preference.firstName + " " + Preference.lastName
        val moblie = Preference.phone
        val im = Preference.real_state_image
        Glide.with(this)
                .load(im)
                .placeholder(R.drawable.profile)
                .fitCenter()
                .into(profile_image)

        txt_mobile.text = moblie
        txt_name.text = fullName

    }


    override fun onClick(view: View?) {

        when (view!!.id) {

            R.id.img_edit_profile -> {

                intent = Intent(this, RejesterActivity::class.java);
                intent.putExtra("mobile", Preference.phone)
                startActivity(intent)

            }

            R.id.btnBack -> {
                finish()
            }


        }

    }

    override fun eventProfile(pos: Int) {

        when (pos) {
            0 -> {
                intentActivity(SaleActivity())
            }
            1 -> {
                intentActivity(RentActivity())
            }
            2 -> {
                intentActivity(ListHomeActivity())
            }
            3->{
                changeLanguage()
            }

            4 -> {
                shareApp()
            }

            5->{
                intentActivity(ActivityNews())
            }
            6 -> {
                intentActivity(HelpActivity())
            }
            7 -> {
                intentActivity(AboutActivity())
            }

            8 -> {
                logout()
            }
        }
    }


    fun intentActivity(activity: AppCompatActivity) {
        intent = Intent(this, activity::class.java);
        startActivity(intent)
    }

    fun logout() {

        showLoading()
        Repository().getInstance()?.logout(object : CallBack<Api<List<Void>>>() {

            override fun onSuccess(t: Api<List<Void>>) {
                super.onSuccess(t)
                hideLoading()
                if (t.status?.code == 200) {
                    Preference.logOut()
                    Toast.makeText(this@ProfileActivity, t.status?.message, Toast.LENGTH_LONG).show()
                    finish()
                }
            }

            override fun onFail(e: Exception, code: Int) {
                super.onFail(e, code)
                hideLoading()
                Toast.makeText(this@ProfileActivity, e.message, Toast.LENGTH_LONG).show()

            }

        })

    }


    fun changeLanguage(){

        intent = Intent(this, ActivitySettings::class.java);
        startActivity(intent)
    }

    private fun shareApp() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "outn")
            shareIntent.putExtra(Intent.EXTRA_TEXT, "http://alaan.co.uk/outn_app.html")
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
