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
import com.jkdajac.cookingnote.MyIntentConstance
import com.jkdajac.cooknotearchitectures.R
import com.jkdajac.cooknotearchitectures.presentation.editactivities.EditSoupActivity
import com.jkdajac.cooknotearchitectures.presentation.editactivities.EditZakuskiActivity
import com.jkdajac.cooknotearchitectures.presentation.fragments.SoupFragment
import com.jkdajac.cooknotearchitectures.presentation.fragments.ZakuskiFragment
import com.jkdajac.data.storage.entity.Soup
import com.jkdajac.data.storage.entity.Zakuski
import kotlinx.android.synthetic.main.item_soup.view.*
import kotlinx.android.synthetic.main.item_zakuski.view.*

class ZakuskiAdapter (val contextA: Context,
                      val zakuskiList: List<Zakuski>,
                      val callback: ZakuskiFragment
) : RecyclerView.Adapter<ZakuskiAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(contextA).inflate(R.layout.item_zakuski, parent, false), contextA)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(zakuskiList[position])
        holder.title?.text = zakuskiList[position].dbzakuskititle
        holder.image?.setImageURI(zakuskiList[position].dbzakuskiimage.toUri())
        holder.deleteItem?.setOnClickListener {
            callback.deleteItem(position)
        }

    }

    override fun getItemCount(): Int {
        return zakuskiList.size
    }

    class ViewHolder(itemView : View, contextV: Context)  : RecyclerView.ViewHolder(itemView){

        val context = contextV
        var title: TextView? = null
        var image : ImageView? = null
        var deleteItem : ImageView? = null

        init{
            title = itemView.tvItemZakuski
            image  = itemView.ivItemZakuski
            deleteItem = itemView.ivItemDeleteZakuski
        }

        fun setData(item : Zakuski){
            itemView.setOnClickListener {
                val intent = Intent(context, EditZakuskiActivity :: class.java).apply {
                    putExtra(MyIntentConstance.I_NAME_KEY, item.dbzakuskititle)
                    putExtra(MyIntentConstance.I_CONTENT_KEY, item.dbzakuskicontent)
                    putExtra(MyIntentConstance.I_IMAGE_KEY, item.dbzakuskiimage)
                }
                context.startActivity(intent)
            }
        }
        interface ItemCallback {
            fun deleteItem(index: Int)
        }

    }
}