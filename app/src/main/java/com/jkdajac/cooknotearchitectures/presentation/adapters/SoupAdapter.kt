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
import com.jkdajac.cooknotearchitectures.presentation.fragments.SoupFragment
import com.jkdajac.data.storage.entity.Soup
import kotlinx.android.synthetic.main.item_meat.view.*
import kotlinx.android.synthetic.main.item_soup.view.*

class SoupAdapter (val contextA: Context,
                   val soupList: List<Soup>,
                   val callback: SoupFragment
) : RecyclerView.Adapter<SoupAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(contextA).inflate(R.layout.item_soup, parent, false), contextA)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(soupList[position])
        holder.title?.text = soupList[position].dbsouptitle
        holder.image?.setImageURI(soupList[position].dbsoupimage.toUri())
        holder.deleteItem?.setOnClickListener {
            callback.deleteItem(position)
        }

    }

    override fun getItemCount(): Int {
        return soupList.size
    }

    class ViewHolder(itemView : View, contextV: Context)  : RecyclerView.ViewHolder(itemView){

        val context = contextV
        var title: TextView? = null
        var image : ImageView? = null
        var deleteItem : ImageView? = null

        init{
            title = itemView.tvItemSoup
            image  = itemView.ivItemSoup
            deleteItem = itemView.ivItemDeleteSoup
        }

        fun setData(item : Soup){
            itemView.setOnClickListener {
                val intent = Intent(context, EditSoupActivity :: class.java).apply {
                    putExtra(MyIntentConstance.I_NAME_KEY, item.dbsouptitle)
                    putExtra(MyIntentConstance.I_CONTENT_KEY, item.dbsoupcontent)
                    putExtra(MyIntentConstance.I_IMAGE_KEY, item.dbsoupimage)
                }
                context.startActivity(intent)
            }
        }
        interface ItemCallback {
            fun deleteItem(index: Int)
        }

    }
}