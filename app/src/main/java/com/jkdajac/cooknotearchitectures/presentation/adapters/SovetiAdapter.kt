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
import com.jkdajac.cooknotearchitectures.presentation.editactivities.EditSovetiActivity
import com.jkdajac.cooknotearchitectures.presentation.fragments.SovetiFragment
import com.jkdajac.data.roomstorage.entity.Soveti
import kotlinx.android.synthetic.main.item_soveti.view.*

class SovetiAdapter (val contextA: Context,
                     val sovetiList: List<Soveti>,
                     val callback: SovetiFragment
) : RecyclerView.Adapter<SovetiAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(contextA).inflate(R.layout.item_soveti, parent, false), contextA)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(sovetiList[position])
        holder.title?.text = sovetiList[position].dbsovetititle
        holder.image?.setImageURI(sovetiList[position].dbsovetiimage.toUri())
        holder.deleteItem?.setOnClickListener {
            callback.deleteItem(position)
        }

    }

    override fun getItemCount(): Int {
        return sovetiList.size
    }

    class ViewHolder(itemView : View, contextV: Context)  : RecyclerView.ViewHolder(itemView){

        val context = contextV
        var title: TextView? = null
        var image : ImageView? = null
        var deleteItem : ImageView? = null

        init{
            title = itemView.tvItemSoveti
            image  = itemView.ivItemSoveti
            deleteItem = itemView.ivItemDeleteSoveti
        }

        fun setData(item : Soveti){
            itemView.setOnClickListener {
                val intent = Intent(context, EditSovetiActivity :: class.java).apply {
                    putExtra(MyIntentConstance.I_NAME_KEY, item.dbsovetititle)
                    putExtra(MyIntentConstance.I_CONTENT_KEY, item.dbsoveticontent)
                    putExtra(MyIntentConstance.I_IMAGE_KEY, item.dbsovetiimage)
                }
                context.startActivity(intent)
            }
        }
        interface ItemCallback {
            fun deleteItem(index: Int)
        }

    }
}