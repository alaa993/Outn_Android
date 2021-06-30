package com.alaan.outn.adapter

import android.content.Context
import android.text.Html
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alaan.outn.R
import com.alaan.outn.application.G.Companion.context
import com.alaan.outn.interfac.InterFaceShowActivityRealState
import com.alaan.outn.model.ModelPlatform
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_neighborhood.*
import kotlinx.android.synthetic.main.partners_detail_ad_item_list.view.*
import org.jetbrains.anko.internals.AnkoInternals.addView


class AdapterShowActivityPlatform (context: Context, list: List<ModelPlatform>, val listner:InterFaceShowActivityRealState):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val context:Context
    var list: List<ModelPlatform>
    var status = true

    init {
        this.list = list
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       if(viewType == VIEW_TYPE_ONE){
           return ShowActivityPlatform(LayoutInflater.from(context).inflate(R.layout.row_activity_show_realstate,parent,false))
       }
        return AdViewHolder(LayoutInflater.from(context).inflate(R.layout.partners_detail_ad_item_list,parent,false))
    }

    override fun getItemCount(): Int {
      return list.size
    }

    override fun onBindViewHolder(holder:RecyclerView.ViewHolder, position: Int) {
        val list = list[position]
        when (holder.itemViewType) {
            VIEW_TYPE_ONE -> (holder as ShowActivityPlatform).bind(list, listner)

            VIEW_TYPE_TWO -> (holder as AdViewHolder).bind2(list)

        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position % 7 == 6){
            return 2
        }else{
            return  1
        }
    }

    class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind2(model: ModelPlatform) {

            val adContainer: AdView = itemView.findViewById(R.id.adView)
            val mAdView = AdView(context)
            mAdView.adSize = AdSize.BANNER
            mAdView.adUnitId = "ca-app-pub-6606021354718512/5502891970"
            adContainer.addView(mAdView)
            val adRequest = AdRequest.Builder().build()
            mAdView.loadAd(adRequest)

        }

    }

    class ShowActivityPlatform(itemView:View):RecyclerView.ViewHolder(itemView){
        val txt_fullName:TextView
        val txt_date_time:TextView
        val txt_des:TextView
        val img_comment:ImageView
        val img_share:ImageView
        val img_more:ImageView
        var txt_count_comment:TextView
        val title:TextView
        val txt_more:TextView
        val img: ImageView

        init {

            txt_fullName = itemView.findViewById(R.id.txt_fullName_platform)
            txt_date_time = itemView.findViewById(R.id.txt_date_time)
            txt_count_comment = itemView.findViewById(R.id.txt_comment_count)
            txt_more = itemView.findViewById(R.id.txt_more)
            img_share = itemView.findViewById(R.id.img_share)
            //   txt_date_and_time = v.findViewById(R.id.txt_date_and_time)

            //   txt_type = v.findViewById(R.id.txt_type)
            img_comment = itemView.findViewById(R.id.img_comment)
            img_more = itemView.findViewById(R.id.img_more)
            txt_des = itemView.findViewById(R.id.txt_des)
            //  parent = v.findViewById(R.id.parent)
            title = itemView.findViewById(R.id.title_news)
            img = itemView.findViewById(R.id.img)

        }

        fun bind(model: ModelPlatform, listner:InterFaceShowActivityRealState) {

            txt_fullName.text = model.user
            txt_count_comment.text = "" + model.comments_count
            txt_date_time.text = model.elapsed_time
            title.text = model.title
            txt_des.movementMethod = LinkMovementMethod.getInstance()
            val sourceString = "<b>" + model.description + "</b>"
            txt_des.text = Html.fromHtml(sourceString)
            Linkify.addLinks(txt_des, Linkify.WEB_URLS);

            Glide.with(itemView.context)
                    .load(model.cover)
                    .into(img)
            if (model.description.count() > 150) {
                txt_more.visibility = View.VISIBLE
            } else {
                txt_more.visibility = View.GONE
            }

            img_share.setOnClickListener {
                listner.share(model)
            }

            img_comment.setOnClickListener {
                listner.comment(model)
            }
            txt_fullName.setOnClickListener {
                listner.detail(model)
            }
            img_more.setOnClickListener {
                listner.detail(model)
            }
            txt_more.setOnClickListener {
                listner.more(model)
            }
        }
    }

    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
    }
}