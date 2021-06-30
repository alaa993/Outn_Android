package com.alaan.outn.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.getDrawable
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.alaan.outn.R
import com.alaan.outn.activity.ActivityNeighborhood
import com.alaan.outn.activity.ActivityShowImag
import com.alaan.outn.activity.FragmentComment
import com.alaan.outn.activity.LoginActivity
import com.alaan.outn.application.G
import com.alaan.outn.application.G.Companion.context
import com.alaan.outn.interfac.InterfaceShowListImage
import com.alaan.outn.interfac.interFaceFavorite
import com.alaan.outn.model.ModelSearchHome
import com.alaan.outn.utils.Preference
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.initialization.InitializationStatus
import me.relex.circleindicator.CircleIndicator
import java.io.Serializable
import java.util.*

class AdapterSearchResult(context: Context, list: List<ModelSearchHome>, val faceFavorite: interFaceFavorite) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), InterfaceShowListImage {

    var list: List<ModelSearchHome>
    var context: Context
    val xs = arrayListOf<String>()


    init {
        this.list = list
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ONE) {
            return ListHomeHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_activity_search_result, parent, false))
        }
        return AdViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.partners_detail_ad_item_list, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n", "CheckResult")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val list = list[position]
        when (holder.itemViewType) {
            VIEW_TYPE_ONE -> (holder as ListHomeHolder).bind1(context, list, faceFavorite, this)

            VIEW_TYPE_TWO -> (holder as AdViewHolder).bind2(list)

        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position % 5 == 4){
            return 2
        }else{
            return  1
        }
    }

    override fun listImage(get: ArrayList<String>, position: Int) {

        xs.clear()
        list[position].images?.forEach {
            it.md?.let { it1 -> xs.add(it1) }
        }
        val intent = Intent(context, ActivityShowImag::class.java)
        intent.putExtra("items", xs)
        intent.putExtra("position", 0)
        context.startActivity(intent)
    }


    class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind2(model: ModelSearchHome) {

            val adContainer: AdView = itemView.findViewById(R.id.adView)
            val mAdView = AdView(G.context)
            mAdView.adSize = AdSize.BANNER
            mAdView.adUnitId = "ca-app-pub-6606021354718512/4005799869"
            adContainer.addView(mAdView)
            val adRequest = AdRequest.Builder().build()
            mAdView.loadAd(adRequest)

        }

    }


    class ListHomeHolder(v: View) : RecyclerView.ViewHolder(v) {

        val txt_type: TextView
        val txt_roomsa: TextView
        val txt_down_payment: TextView
        val txt_monthly_payment: TextView
        val txt_price: TextView
        val txt_desc: TextView
        val txt_arae: TextView
        val text_type_sale_rent: TextView

        val txt_monthly: TextView
        val txt_down: TextView
        val txt_p: TextView
        val txt_country_city: TextView

        val trade_user_name: TextView
        val txt_date: TextView

        //  val title_app: TextView
        val text_neighborhood: TextView
        val img_comment: ImageView
        val txt_comment_count: TextView
        val txt_count_view_value: TextView
        val txt_count_view: TextView
        val txt_count_favorites: TextView
        val image_favorites: ImageView

        // val imageSlider: SliderImage
        val imagePhone: ImageView
        val ln_price: LinearLayout
        val ln_down: LinearLayout
        val ln_monthly: LinearLayout

        var viewPager: ViewPager? = null
        var circleIndicator: CircleIndicator? = null

        init {


            txt_type = v.findViewById(R.id.txt_type)
            trade_user_name = v.findViewById(R.id.trade_user_name)
            txt_roomsa = v.findViewById(R.id.txt_roomsa_value)
            txt_monthly_payment = v.findViewById(R.id.txt_monthly_payment_value)
            txt_down_payment = v.findViewById(R.id.txt_down_payment_value)
            txt_price = v.findViewById(R.id.txt_price_value)
            txt_desc = v.findViewById(R.id.txt_desc)
            txt_arae = v.findViewById(R.id.txt_arae_value)
            txt_date = v.findViewById(R.id.txt_date)
            txt_country_city = v.findViewById(R.id.txt_country_city)

            text_neighborhood = v.findViewById(R.id.text_neighborhood)
            img_comment = v.findViewById(R.id.img_comment)
            txt_comment_count = v.findViewById(R.id.txt_comment_count)
            txt_count_view = v.findViewById(R.id.txt_count_view_value)
            txt_count_view_value = v.findViewById(R.id.txt_count_view_value)


            txt_count_favorites = v.findViewById(R.id.txt_count_favorites)
            image_favorites = v.findViewById(R.id.image_favorites)


            txt_monthly = v.findViewById(R.id.txt_monthly_payment)
            txt_down = v.findViewById(R.id.txt_down_payment)
            txt_p = v.findViewById(R.id.txt_price)
            // imageSlider = v.findViewById(R.id.slider_ListHome)
            imagePhone = v.findViewById(R.id.imagePhone)

            ln_price = v.findViewById(R.id.lnPrice)
            ln_down = v.findViewById(R.id.ln_down_payment)
            ln_monthly = v.findViewById(R.id.ln_monthly_payment)
            text_type_sale_rent = v.findViewById(R.id.text_type_sale_rent)
            circleIndicator = v.findViewById(R.id.circle);
            viewPager = v.findViewById(R.id.view_pager);

        }

        fun bind1(ctx: Context, model: ModelSearchHome, faceFavorite: interFaceFavorite, loadImageListener: InterfaceShowListImage) {

            var myPager: MyPager? = null

            txt_type.text = model.homeType + " - " + model.adType
            txt_country_city.text = model.location?.country + " - " + (if (model.location?.city == null) "" else model.location.city)
            txt_date.text = model.createdAt

            txt_comment_count.text = model.comments_count.toString()
            txt_count_view_value.text = model.view_count.toString()
            txt_count_favorites.text = model.favorites_count.toString()

            if (if (model.is_favorited != null) model.is_favorited!! else false) {
                image_favorites.setImageResource(R.drawable.favorited)

            } else {
                image_favorites.setImageResource(R.drawable.heart)
            }

            if (model.adTypeId == 2) {

                txt_price.text = ": " + String.format(Locale.ENGLISH, "%,d", if (model.price != "")
                    model.price?.toLong() else 0) +
                        " " + if (model.currency?.data?.name != null)
                    model.currency?.data?.name else ""

                ln_monthly.visibility = View.GONE
                ln_down.visibility = View.GONE
                ln_price.visibility = View.VISIBLE
                text_type_sale_rent.background = getDrawable(context!!, R.drawable.background_rent)


            } else {

                ln_monthly.visibility = View.VISIBLE
                ln_down.visibility = View.VISIBLE
                ln_price.visibility = View.GONE

                txt_monthly_payment.text = ": " + String.format(Locale.ENGLISH, "%,d", if (model.monthlyPayment != "")
                    model.monthlyPayment?.toLong() else 0) +
                        " " + if (model.currency?.data?.name != null)
                    model.currency?.data?.name else ""


                txt_down_payment.text = ": " + String.format(Locale.ENGLISH, "%,d", if (model.downPayment != "")
                    model.downPayment?.toLong() else 0) + " " +
                        " " + if (model.currency?.data?.name != null)
                    model.currency?.data?.name else ""

                text_type_sale_rent.background = getDrawable(context!!, R.drawable.background_rent)
            }

            txt_arae.text = ": " + model.area.toString()
            txt_roomsa.text = ": " + model.rooms.toString()
            txt_desc.text = model.description
            text_type_sale_rent.text = model.adType

            text_neighborhood.setOnClickListener {

                if (model.neighborhood_images?.count() ?: 0 > 0 || model.neighborhood != null) {

                    val intent = Intent(context, ActivityNeighborhood::class.java)
                    intent.putExtra("listImage", model.neighborhood_images as Serializable)
                    intent.putExtra("des", model.neighborhood)
                    ctx.startActivity(intent)
                }


            }

            if (model.user?.business_name == null) {
                trade_user_name.visibility = GONE
            } else {
                trade_user_name.text = model.user?.business_name
            }


            val listImage = arrayListOf<String>()

            model.images?.forEach {
                it.md?.let { it1 -> listImage.add(it1) }
            }


            myPager = MyPager(context!!, listImage, position, loadImageListener)
            viewPager?.adapter = myPager;
            circleIndicator?.setViewPager(viewPager);


            imagePhone.setOnClickListener {

                var phone = ""
                if (model.phone != "") {
                    phone = model.phone.toString()
                } else {
                    phone = model.user?.mobile.toString()
                }

                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:" + phone)
                ctx.startActivity(intent)
            }

            img_comment.setOnClickListener {
                val intent = Intent(context, FragmentComment::class.java)
                intent.putExtra("homeId", model.id)
                intent.putExtra("model_type", "home")
                ctx.startActivity(intent)
            }

            image_favorites.setOnClickListener {


                if (Preference.isLogin!!) {

                    if (if (model.is_favorited != null) model.is_favorited!! else false) {
                        faceFavorite.favoritesDelete(model.id!!)
                        model.is_favorited = false
                        image_favorites.setImageResource(R.drawable.heart)
                        model.favorites_count = model.favorites_count?.minus(1)
//                        notifyDataSetChanged()
                    } else {
                        faceFavorite.favorites(model.id!!)
                        model.is_favorited = true
                        image_favorites.setImageResource(R.drawable.favorited)
                        model.favorites_count = model.favorites_count?.plus(1)
//                        notifyDataSetChanged()
                    }
                } else {
                    val intent = Intent(context, LoginActivity::class.java)
                    ctx.startActivity(intent)
                }

            }
        }


    }

    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
    }
}
