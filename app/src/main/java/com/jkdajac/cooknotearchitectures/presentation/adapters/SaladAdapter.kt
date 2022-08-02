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
import com.jkdajac.cooknotearchitectures.presentation.editactivities.EditSaladActivity
import com.jkdajac.cooknotearchitectures.presentation.fragments.SaladFragment
import com.jkdajac.data.roomstorage.entity.Salad
import kotlinx.android.synthetic.main.item_salad.view.*

class SaladAdapter (val contextA: Context,
                    val saladList: List<Salad>,
                    val callback: SaladFragment
) : RecyclerView.Adapter<SaladAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(contextA).inflate(R.layout.item_salad, parent, false), contextA)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(saladList[position])
        holder.title?.text = saladList[position].dbsaladtitle
        holder.image?.setImageURI(saladList[position].dbsaladimage.toUri())
        holder.deleteItem?.setOnClickListener {
            callback.deleteItem(position)
        }

    }

    override fun getItemCount(): Int {
        return saladList.size
    }

    class ViewHolder(itemView : View, contextV: Context)  : RecyclerView.ViewHolder(itemView){

        val context = contextV
        var title: TextView? = null
        var image : ImageView? = null
        var deleteItem : ImageView? = null

        init{
            title = itemView.tvItemSalad
            image  = itemView.ivItemSalad
            deleteItem = itemView.ivItemDeleteSalad
        }

        fun setData(item : Salad){
            itemView.setOnClickListener {
                val intent = Intent(context, EditSaladActivity :: class.java).apply {
                    putExtra(MyIntentConstance.I_NAME_KEY, item.dbsaladtitle)
                    putExtra(MyIntentConstance.I_CONTENT_KEY, item.dbsaladcontent)
                    putExtra(MyIntentConstance.I_IMAGE_KEY, item.dbsaladimage)
                }
                context.startActivity(intent)
            }
        }
        interface ItemCallback {
            fun deleteItem(index: Int)
        }

    }
}