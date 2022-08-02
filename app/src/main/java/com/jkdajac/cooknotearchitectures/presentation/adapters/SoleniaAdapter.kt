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
import com.jkdajac.cooknotearchitectures.presentation.editactivities.EditSoleniaActivity
import com.jkdajac.cooknotearchitectures.presentation.fragments.SoleniaFragment
import com.jkdajac.data.roomstorage.entity.Solenia
import kotlinx.android.synthetic.main.item_solenia.view.*

class SoleniaAdapter (val contextA: Context,
                      val soleniaList: List<Solenia>,
                      val callback: SoleniaFragment
) : RecyclerView.Adapter<SoleniaAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(contextA).inflate(R.layout.item_solenia, parent, false), contextA)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(soleniaList[position])
        holder.title?.text = soleniaList[position].dbsoleniatitle
        holder.image?.setImageURI(soleniaList[position].dbsoleniaimage.toUri())
        holder.deleteItem?.setOnClickListener {
            callback.deleteItem(position)
        }

    }

    override fun getItemCount(): Int {
        return soleniaList.size
    }

    class ViewHolder(itemView : View, contextV: Context)  : RecyclerView.ViewHolder(itemView){

        val context = contextV
        var title: TextView? = null
        var image : ImageView? = null
        var deleteItem : ImageView? = null

        init{
            title = itemView.tvItemSolenia
            image  = itemView.ivItemSolenia
            deleteItem = itemView.ivItemDeleteSolenia
        }

        fun setData(item : Solenia){
            itemView.setOnClickListener {
                val intent = Intent(context, EditSoleniaActivity :: class.java).apply {
                    putExtra(MyIntentConstance.I_NAME_KEY, item.dbsoleniatitle)
                    putExtra(MyIntentConstance.I_CONTENT_KEY, item.dbsoleniacontent)
                    putExtra(MyIntentConstance.I_IMAGE_KEY, item.dbsoleniaimage)
                }
                context.startActivity(intent)
            }
        }
        interface ItemCallback {
            fun deleteItem(index: Int)
        }

    }
}