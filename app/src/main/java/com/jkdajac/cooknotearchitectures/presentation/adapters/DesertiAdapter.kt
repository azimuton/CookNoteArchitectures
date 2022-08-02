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
import com.jkdajac.cooknotearchitectures.presentation.editactivities.EditDesertiActivity
import com.jkdajac.cooknotearchitectures.presentation.fragments.DesertiFragment
import com.jkdajac.data.roomstorage.entity.Deserti
import kotlinx.android.synthetic.main.item_deserti.view.*

class DesertiAdapter (val contextA: Context,
                      val desertiList: List<Deserti>,
                      val callback: DesertiFragment
) : RecyclerView.Adapter<DesertiAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(contextA).inflate(R.layout.item_deserti, parent, false), contextA)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(desertiList[position])
        holder.title?.text = desertiList[position].dbdesertititle
        holder.image?.setImageURI(desertiList[position].dbdesertiimage.toUri())
        holder.deleteItem?.setOnClickListener {
            callback.deleteItem(position)
        }

    }

    override fun getItemCount(): Int {
        return desertiList.size
    }

    class ViewHolder(itemView : View, contextV: Context)  : RecyclerView.ViewHolder(itemView){

        val context = contextV
        var title: TextView? = null
        var image : ImageView? = null
        var deleteItem : ImageView? = null

        init{
            title = itemView.tvItemDeserti
            image  = itemView.ivItemDeserti
            deleteItem = itemView.ivItemDeleteDeserti
        }

        fun setData(item : Deserti){
            itemView.setOnClickListener {
                val intent = Intent(context, EditDesertiActivity :: class.java).apply {
                    putExtra(MyIntentConstance.I_NAME_KEY, item.dbdesertititle)
                    putExtra(MyIntentConstance.I_CONTENT_KEY, item.dbdeserticontent)
                    putExtra(MyIntentConstance.I_IMAGE_KEY, item.dbdesertiimage)
                }
                context.startActivity(intent)
            }
        }
        interface ItemCallback {
            fun deleteItem(index: Int)
        }

    }
}