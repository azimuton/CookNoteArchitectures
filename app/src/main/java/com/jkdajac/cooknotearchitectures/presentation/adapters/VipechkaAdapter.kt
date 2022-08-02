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
import com.jkdajac.cooknotearchitectures.presentation.editactivities.EditVipechkaActivity
import com.jkdajac.cooknotearchitectures.presentation.fragments.VipechkaFragment
import com.jkdajac.data.roomstorage.entity.Vipechka
import kotlinx.android.synthetic.main.item_vipechka.view.*

class VipechkaAdapter (val contextA: Context,
                       val vipechkaList: List<Vipechka>,
                       val callback: VipechkaFragment
) : RecyclerView.Adapter<VipechkaAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(contextA).inflate(R.layout.item_vipechka, parent, false), contextA)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(vipechkaList[position])
        holder.title?.text = vipechkaList[position].dbvipechkatitle
        holder.image?.setImageURI(vipechkaList[position].dbvipechkaimage.toUri())
        holder.deleteItem?.setOnClickListener {
            callback.deleteItem(position)
        }

    }

    override fun getItemCount(): Int {
        return vipechkaList.size
    }

    class ViewHolder(itemView : View, contextV: Context)  : RecyclerView.ViewHolder(itemView){

        val context = contextV
        var title: TextView? = null
        var image : ImageView? = null
        var deleteItem : ImageView? = null

        init{
            title = itemView.tvItemVipechka
            image  = itemView.ivItemVipechka
            deleteItem = itemView.ivItemDeleteVipechka
        }

        fun setData(item : Vipechka){
            itemView.setOnClickListener {
                val intent = Intent(context, EditVipechkaActivity :: class.java).apply {
                    putExtra(MyIntentConstance.I_NAME_KEY, item.dbvipechkatitle)
                    putExtra(MyIntentConstance.I_CONTENT_KEY, item.dbvipechkacontent)
                    putExtra(MyIntentConstance.I_IMAGE_KEY, item.dbvipechkaimage)
                }
                context.startActivity(intent)
            }
        }
        interface ItemCallback {
            fun deleteItem(index: Int)
        }

    }
}