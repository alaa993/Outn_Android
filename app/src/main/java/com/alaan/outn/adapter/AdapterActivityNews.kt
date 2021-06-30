package com.alaan.outn.adapter


import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alaan.outn.R
import com.alaan.outn.interfac.InterFaceActivityNews
import com.alaan.outn.interfac.InterFaceShowActivityRealState
import com.alaan.outn.model.ModelNews
import com.alaan.outn.model.ModelPlatform
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.show_matn_outnplatform.view.*

class AdapterActivityNews (context: Context, list: List<ModelNews>, val listner: InterFaceActivityNews):RecyclerView.Adapter<AdapterActivityNews.ActivityNews>(){

    val context:Context
    var list: List<ModelNews>

    init {
        this.list = list
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityNews {
        val  inflater  = LayoutInflater.from(context)
        return ActivityNews(inflater.inflate(R.layout.row_activity_new,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder:ActivityNews, position: Int) {

        val list = list[position]
        holder.txt_date_time.text = list.created_at
        holder.txt_body.text = list.body
        holder.txt_title.text = list.title
        holder.txt_body.movementMethod = ScrollingMovementMethod()
        Glide.with(context)
                .load(list.cover)
                .into(holder.img_cover);

        holder.txt_comment.setOnClickListener{
            listner.comment(list)
        }

        holder.txt_delet.setOnClickListener{

            listner.delet(list)
        }

        holder.txt_edit.setOnClickListener{
          listner.edit(list)
        }
    }


    class ActivityNews(v:View):RecyclerView.ViewHolder(v){

        val txt_date_time:TextView
        val txt_body:TextView
        val txt_comment:TextView
        val txt_title:TextView
        val txt_edit:TextView
        val txt_delet:TextView
        val img_cover: ImageView

        init {

            txt_date_time = v.findViewById(R.id.txt_date_time)
            txt_comment = v.findViewById(R.id.txt_comment)
            txt_body = v.findViewById(R.id.txt_body)
            txt_title = v.findViewById(R.id.txt_title)
            txt_edit = v.findViewById(R.id.txt_edit)
            txt_delet = v.findViewById(R.id.txt_delet)
            img_cover = v.findViewById(R.id.img_news)

        }
    }

}