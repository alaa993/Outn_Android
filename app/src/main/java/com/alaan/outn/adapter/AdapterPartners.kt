package com.alaan.outn.adapter


import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alaan.outn.R
import com.alaan.outn.interfac.InterFacePartnes
import com.alaan.outn.interfac.InterFaceShowActivityRealState
import com.alaan.outn.model.ModelPartners
import com.alaan.outn.model.ModelSearchHome
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import org.jetbrains.anko.backgroundColor
import java.util.*

class AdapterPartners (context: Context,list: List<ModelPartners>,val listner: InterFacePartnes):RecyclerView.Adapter<AdapterPartners.Partners>(){

    val context:Context
    var list: List<ModelPartners>

    init {
        this.list = list
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Partners {
        val  inflater  = LayoutInflater.from(context)
        return Partners(inflater.inflate(R.layout.row_activity_partners,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder:Partners, position: Int) {

        val list = list.get(position)
        holder.txt_fullName.text = if (list?.fname != null) list.fname else "" + " "+ if (list?.lname != null) list?.lname else ""
        holder.txt_newRealState.text = if (list.business_name != null ) list.business_name else "" + "new Real State"
        Glide.with(context)
                .load(list.avatar)
                .error(R.drawable.user)
                .into(holder.profile_partnes)


            holder.itemView.setOnClickListener {
                listner.more(list)
            }


    }


    class Partners(v:View):RecyclerView.ViewHolder(v){

        val txt_fullName:TextView
        val txt_newRealState:TextView
        val profile_partnes:ImageView


        init {

            txt_fullName = v.findViewById(R.id.txt_fullName)
            txt_newRealState = v.findViewById(R.id.txt_newRealState)
            profile_partnes = v.findViewById(R.id.profile_partnes)


        }
    }

}