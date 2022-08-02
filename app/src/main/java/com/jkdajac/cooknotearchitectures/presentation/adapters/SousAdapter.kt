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
import com.jkdajac.cooknotearchitectures.presentation.editactivities.EditSousActivity
import com.jkdajac.cooknotearchitectures.presentation.fragments.SousFragment
import com.jkdajac.data.roomstorage.entity.Sous
import kotlinx.android.synthetic.main.item_sous.view.*

class SousAdapter (val contextA: Context,
                   val sousList: List<Sous>,
                   val callback: SousFragment
) : RecyclerView.Adapter<SousAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(contextA).inflate(R.layout.item_sous, parent, false), contextA)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(sousList[position])
        holder.title?.text = sousList[position].dbsoustitle
        holder.image?.setImageURI(sousList[position].dbsousimage.toUri())
        holder.deleteItem?.setOnClickListener {
            callback.deleteItem(position)
        }

    }

    override fun getItemCount(): Int {
        return sousList.size
    }

    class ViewHolder(itemView : View, contextV: Context)  : RecyclerView.ViewHolder(itemView){

        val context = contextV
        var title: TextView? = null
        var image : ImageView? = null
        var deleteItem : ImageView? = null

        init{
            title = itemView.tvItemSous
            image  = itemView.ivItemSous
            deleteItem = itemView.ivItemDeleteSous
        }

        fun setData(item : Sous){
            itemView.setOnClickListener {
                val intent = Intent(context, EditSousActivity :: class.java).apply {
                    putExtra(MyIntentConstance.I_NAME_KEY, item.dbsoustitle)
                    putExtra(MyIntentConstance.I_CONTENT_KEY, item.dbsouscontent)
                    putExtra(MyIntentConstance.I_IMAGE_KEY, item.dbsousimage)
                }
                context.startActivity(intent)
            }
        }
        interface ItemCallback {
            fun deleteItem(index: Int)
        }

    }
}