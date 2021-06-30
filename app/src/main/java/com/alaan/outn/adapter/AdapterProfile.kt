package com.alaan.outn.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alaan.outn.R
import com.alaan.outn.interfac.interface_profile

class AdapterProfile(context:Context,list:List<String>,val itemClik:interface_profile):RecyclerView.Adapter<AdapterProfile.ProfileHolder>() {

    var context: Context
    var list:List<String>

    init {

        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileHolder {
        val inflater = LayoutInflater.from(context)
        return ProfileHolder(inflater.inflate(R.layout.row_profile, parent, false))

    }

    override fun onBindViewHolder(holder: ProfileHolder, position: Int) {

        holder.lbl_name.setText(list.get(position))

        if (position == 0){
            holder.image_Item.setImageResource(R.drawable.home)


        }else if (position == 1){
            holder.image_Item.setImageResource(R.drawable.homerent)

        }else if (position == 2) {
            holder.image_Item.setImageResource(R.drawable.home)

        }else if (position == 3){
            holder.image_Item.setImageResource(R.drawable.language)

        }else if (position == 4) {
            holder.image_Item.setImageResource(R.drawable.share)
        } else if (position == 5){

            holder.image_Item.setImageResource(R.drawable.news)
        } else if (position == 6){

            holder.image_Item.setImageResource(R.drawable.help)
        }else if (position == 7){

            holder.image_Item.setImageResource(R.drawable.info)
        }else if (position == 8){

            holder.image_Item.setImageResource(R.drawable.exit)
        }
        holder.itemView.setOnClickListener {

            itemClik.eventProfile(position)

        }

    }

    override fun getItemCount(): Int {

        return list.size
    }



    class ProfileHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var lbl_name: TextView
        var image_Item:ImageView


        init {
            lbl_name = itemView.findViewById(R.id.lb_name) as TextView
            image_Item = itemView.findViewById(R.id.item) as ImageView

        }
    }

}