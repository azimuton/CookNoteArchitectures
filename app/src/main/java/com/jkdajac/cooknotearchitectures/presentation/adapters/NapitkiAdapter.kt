package com.jkdajac.cooknotearchitectures.presentation.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.jkdajac.cooknotearchitectures.MyIntentConstance
import com.jkdajac.cooknotearchitectures.R
import com.jkdajac.cooknotearchitectures.presentation.editactivities.EditNapitkiActivity
import com.jkdajac.cooknotearchitectures.presentation.fragments.NapitkiFragment
import com.jkdajac.data.roomstorage.entity.Napitki
import kotlinx.android.synthetic.main.item_napitki.view.*

class NapitkiAdapter(val contextA: Context,
                     val napitkiList: List<Napitki>,
                     val callback: NapitkiFragment
) : RecyclerView.Adapter<NapitkiAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(contextA).inflate(R.layout.item_napitki, parent, false), contextA)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(napitkiList[position])
        holder.title?.text = napitkiList[position].dbnapitkititle
        holder.image?.setImageURI(napitkiList[position].dbnapitkiimage.toUri())
        holder.deleteItem?.setOnClickListener {
            callback.deleteItem(position)
        }

    }

    override fun getItemCount(): Int {
        return napitkiList.size
    }

    class ViewHolder(itemView : View, contextV: Context)  : RecyclerView.ViewHolder(itemView){

        val context = contextV
        var title: TextView? = null
        var image : ImageView? = null
        var deleteItem : ImageView? = null

        init{
            title = itemView.tvItemNapitki
            image  = itemView.ivItemNapitki
            deleteItem = itemView.ivItemDeleteNapitki
        }

        fun setData(item : Napitki){
            itemView.setOnClickListener {
                val intent = Intent(context, EditNapitkiActivity :: class.java).apply {
                    putExtra(MyIntentConstance.I_NAME_KEY, item.dbnapitkititle)
                    putExtra(MyIntentConstance.I_CONTENT_KEY, item.dbnapitkicontent)
                    putExtra(MyIntentConstance.I_IMAGE_KEY, item.dbnapitkiimage)
                }
                context.startActivity(intent)
            }
        }
        interface ItemCallback {
            fun deleteItem(index: Int)
        }

    }
}