package com.alaan.outn.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.alaan.outn.R
import com.alaan.outn.activity.ActivityNeighborhood
import com.alaan.outn.activity.ActivityShowImag
import com.alaan.outn.activity.FragmentComment
import com.alaan.outn.interfac.InterFaceListHome
import com.alaan.outn.interfac.InterfaceShowListImage
import com.alaan.outn.model.ModelListHome
import com.alaan.outn.model.ModelSearchHome
import com.custom.sliderimage.logic.SliderImage
import me.relex.circleindicator.CircleIndicator
import java.util.*
import kotlin.collections.ArrayList


class AdapterListHome(context: Context, list: List<ModelListHome>,val itemClik:InterFaceListHome) : RecyclerView.Adapter<AdapterListHome.ListHomeHolder>(),InterfaceShowListImage {

    var list: List<ModelListHome>
    var context: Context


    private var myPager: MyPager? = null
    val xs = arrayListOf<String>()

    init {
        this.list = list
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListHome.ListHomeHolder {
        val inflater = LayoutInflater.from(context)
        return ListHomeHolder(inflater.inflate(R.layout.row_list_home, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n", "CheckResult")
    override fun onBindViewHolder(holder: AdapterListHome.ListHomeHolder, position: Int) {

        val list = list.get(position)
        holder.txt_type.text = list.homeType + " - " + list.adType
        holder.txt_country_city.text = list.location?.country + " - " + (if (list.location?.city == null) "" else list.location.city)
        holder.txt_date.text = list.createdAt

        holder.txt_comment_count.text = list.comments_count.toString()
        holder.txt_count_favorites.text = list.favorites_count.toString()
        holder.txt_count_view_value.text = list.view_count.toString()

        if (list.adTypeId == 2) {

            holder.txt_price.text = ": "+String.format(Locale.ENGLISH, "%,d", if (list.price != "")
                list.price?.toLong() else 0) +
                    " " + if (list.currency?.data?.name != null)
                list.currency?.data?.name else ""

            holder.ln_monthly.visibility = View.GONE
            holder.ln_down.visibility = View.GONE
            holder.ln_price.visibility = View.VISIBLE
            holder.text_type_sale_rent.background = getDrawable(context, R.drawable.background_rent)


        } else {

            holder.ln_monthly.visibility = View.VISIBLE
            holder.ln_down.visibility = View.VISIBLE
            holder.ln_price.visibility = View.GONE

            holder.txt_monthly_payment.text = ": "+String.format(Locale.ENGLISH, "%,d", if (list.monthlyPayment != "")
                list.monthlyPayment?.toLong() else 0) +
                    " " + if (list.currency?.data?.name != null)
                list.currency?.data?.name else ""


            holder.txt_down_payment.text = ": "+ String.format(Locale.ENGLISH, "%,d", if (list.downPayment != "")
                list.downPayment?.toLong() else 0) + " " +
                    " " + if (list.currency?.data?.name != null)
                list.currency?.data?.name else ""

            holder.text_type_sale_rent.background = getDrawable(context, R.drawable.background_rent)
        }

        holder.txt_arae.text = ": "+list.area.toString()
        holder.txt_roomsa.text = ": "+list.rooms.toString()
        holder.txt_desc.text = list.description
        holder.text_type_sale_rent.text = list.adType

        val listImage = arrayListOf<String>()

        list.images?.forEach {
            it

            if (it != null) {
                it.path?.let { it1 -> listImage.add(it1) }
            }
        }

        holder.img_delet.setOnClickListener{
            itemClik.delet(list)
        }

        holder.img_edit.setOnClickListener{
            itemClik.edit(list)
        }
        holder.img_share.setOnClickListener{
            itemClik.share(list)
        }

        holder.img_comment.setOnClickListener {
            val intent = Intent(context, FragmentComment::class.java)
            intent.putExtra("homeId", list.id)
            intent.putExtra("model_type","home")
            context.startActivity(intent)
        }


        myPager = MyPager(context, listImage, position,this)// MyPager(context,listImage,context);
        holder.viewPager?.setAdapter(myPager);
        holder.circleIndicator?.setViewPager(holder.viewPager);

    }

    class ListHomeHolder(v: View) : RecyclerView.ViewHolder(v) {

        val img_edit:ImageView
        val img_delet:ImageView
        val img_share:ImageView
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

        val ln_price: LinearLayout
        val ln_down: LinearLayout
        val ln_monthly: LinearLayout

        var viewPager: ViewPager? = null
        var circleIndicator: CircleIndicator? = null

        val img_comment: ImageView
        val txt_comment_count: TextView
        val txt_count_view_value: TextView
        val txt_count_favorites:TextView

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

            txt_monthly = v.findViewById(R.id.txt_monthly_payment)
            txt_down = v.findViewById(R.id.txt_down_payment)
            txt_p = v.findViewById(R.id.txt_price)


            img_edit = v.findViewById(R.id.img_edit)
            img_delet = v.findViewById(R.id.img_delet)
            img_share = v.findViewById(R.id.img_share)






            ln_price = v.findViewById(R.id.lnPrice)
            ln_down = v.findViewById(R.id.ln_down_payment)
            ln_monthly = v.findViewById(R.id.ln_monthly_payment)
            text_type_sale_rent = v.findViewById(R.id.text_type_sale_rent)
            circleIndicator = v.findViewById(R.id.circle);
            viewPager = v.findViewById(R.id.view_pager);


            img_comment = v.findViewById(R.id.img_comment_home);
            txt_comment_count = v.findViewById(R.id.txt_comment_count_home);
            txt_count_view_value = v.findViewById(R.id.txt_count_view_value_home);
            txt_count_favorites = v.findViewById(R.id.txt_count_favorites_home);

        }


    }

    override fun listImage(get: ArrayList<String>,position: Int) {

        xs.clear()
        list.get(position).images?.forEach {
            it.xs?.let { it1 -> xs.add(it1) }
        }

        val intent = Intent(context, ActivityShowImag::class.java)
        intent.putExtra("items", get)
        intent.putExtra("position", 0)
        context.startActivity(intent)
    }


}

