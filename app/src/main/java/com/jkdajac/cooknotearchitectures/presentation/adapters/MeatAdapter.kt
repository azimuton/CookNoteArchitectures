package com.jkdajac.cookingnote.adapters

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
import com.jkdajac.cooknotearchitectures.presentation.editactivities.EditMeatActivity
import com.jkdajac.cooknotearchitectures.R
import com.jkdajac.cooknotearchitectures.presentation.fragments.MeatFragment
import com.jkdajac.data.storage.entity.Meat
import kotlinx.android.synthetic.main.activity_edit_meat.view.*
import kotlinx.android.synthetic.main.item_meat.view.*

class MeatAdapter(
    val contextA:Context,
    val meatList: List<Meat>,
    val callback: MeatFragment
) : RecyclerView.Adapter<MeatAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(contextA).inflate(R.layout.item_meat, parent, false), contextA)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(meatList[position])
        holder.title?.text = meatList[position].dbmeattitle
        //holder.content?.text = meatList[position].dbmeatcontent
        holder.image?.setImageURI(meatList[position].dbmeatimage.toUri())
        holder.deleteItem?.setOnClickListener {
            callback.deleteItem(position)
        }

    }

    override fun getItemCount(): Int {
        return meatList.size
    }

    class ViewHolder(itemView : View, contextV: Context)  : RecyclerView.ViewHolder(itemView){

        val context = contextV
        var title: TextView? = null
        var content: TextView? = null
        var deleteItem : ImageView? = null
        var image : ImageView? = null

        init{
            title = itemView.tvItemMeat
            content = itemView.etEditMeatContent
            deleteItem = itemView.ivItemDeleteMeat
            image  = itemView.ivItemMeat
        }

        fun setData(item : Meat){
            itemView.setOnClickListener {
                val intent = Intent(context, EditMeatActivity :: class.java).apply {
                    putExtra(MyIntentConstance.I_NAME_KEY, item.dbmeattitle)
                    putExtra(MyIntentConstance.I_CONTENT_KEY, item.dbmeatcontent)
                    putExtra(MyIntentConstance.I_IMAGE_KEY, item.dbmeatimage)
                }
                context.startActivity(intent)
            }
        }
        interface ItemCallback {
            fun deleteItem(index: Int)
        }

    }
}