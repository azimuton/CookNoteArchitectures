package com.jkdajac.cooknotearchitectures.presentation.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.jkdajac.cooknotearchitectures.R
import com.jkdajac.cooknotearchitectures.presentation.adapters.VipechkaAdapter
import com.jkdajac.cooknotearchitectures.presentation.editactivities.EditVipechkaActivity
import com.jkdajac.data.roomstorage.AppDatabase
import com.jkdajac.data.roomstorage.entity.Vipechka
import kotlinx.android.synthetic.main.fragment_vipechka.*

lateinit var vipechkaDatabase : AppDatabase
lateinit var vipechkaList: ArrayList<Vipechka>
lateinit var adaptervipechka : VipechkaAdapter
class VipechkaFragment : Fragment(), VipechkaAdapter.ViewHolder.ItemCallback {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vipechka, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vipechkaList = ArrayList<Vipechka>()
        vipechkaDatabase = context?.let { AppDatabase.getDatabase(it) }!!
        getData()
        adaptervipechka = VipechkaAdapter(requireContext(), vipechkaList, this)
        rvVipechka.layoutManager = LinearLayoutManager(context)
        rvVipechka.adapter = adaptervipechka

        vipechkaDatabase = AppDatabase.getDatabase(requireContext())

        fabAddVipechka.setOnClickListener {
            val intent = Intent(requireContext(), EditVipechkaActivity::class.java)
            startActivity(intent)
        }
        initAdMob5()
    }
    fun getData() {
        val wordFromDb: List<Vipechka> = vipechkaDatabase.vipechkaDao().getAll()
        vipechkaList.clear()
        vipechkaList.addAll(wordFromDb)
    }
    override fun onResume() {
        super.onResume()
        adView5?.resume()
    }
    override fun onPause() {
        super.onPause()
        adView5?.pause()
    }
    override fun onDestroy() {
        super.onDestroy()
        adView5?.destroy()
    }
    private fun initAdMob5(){
        MobileAds.initialize(requireContext())
        val adRequest5 = AdRequest.Builder().build()
        adView5?.loadAd(adRequest5)
    }

    companion object {
        @JvmStatic
        fun newInstance() {
        }
    }

    override fun deleteItem(index: Int) {
        val addDialog = AlertDialog.Builder(requireContext())
        addDialog
            .setMessage("Вы действительно хотите удалить запись?")
            .setPositiveButton("Ok") { dialog, _ ->
                val vipechka = vipechkaList[index]
                vipechkaDatabase.vipechkaDao().deleteVipechka(vipechka)
                getData()
                adaptervipechka.notifyDataSetChanged()
                Toast.makeText(requireContext(), "Запись удалена!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}
