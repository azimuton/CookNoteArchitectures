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
import com.jkdajac.cooknotearchitectures.presentation.adapters.ZakuskiAdapter
import com.jkdajac.cooknotearchitectures.presentation.editactivities.EditZakuskiActivity
import com.jkdajac.data.roomstorage.AppDatabase
import com.jkdajac.data.roomstorage.entity.Zakuski
import kotlinx.android.synthetic.main.fragment_zakuski.*

lateinit var zakuskiDatabase : AppDatabase
lateinit var zakuskiList: ArrayList<Zakuski>
lateinit var adapterzakuski : ZakuskiAdapter
class ZakuskiFragment : Fragment(), ZakuskiAdapter.ViewHolder.ItemCallback {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_zakuski, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        zakuskiList = ArrayList<Zakuski>()
        zakuskiDatabase = context?.let { AppDatabase.getDatabase(it) }!!
        getData()
        adapterzakuski = ZakuskiAdapter(requireContext(), zakuskiList, this)
        rvZakuski.layoutManager = LinearLayoutManager(context)
        rvZakuski.adapter = adapterzakuski

        zakuskiDatabase = AppDatabase.getDatabase(requireContext())

        fabAddZakuski.setOnClickListener {
            val intent = Intent(requireContext(), EditZakuskiActivity::class.java)
            startActivity(intent)
        }
        initAdMob3()
    }
    fun getData() {
        val wordFromDb: List<Zakuski> = zakuskiDatabase.zakuskiDao().getAll()
        zakuskiList.clear()
        zakuskiList.addAll(wordFromDb)
    }

    override fun onResume() {
        super.onResume()
        adView3?.resume()
    }
    override fun onPause() {
        super.onPause()
        adView3?.pause()
    }
    override fun onDestroy() {
        super.onDestroy()
        adView3?.destroy()
    }
    private fun initAdMob3(){
        MobileAds.initialize(requireContext())
        val adRequest3 = AdRequest.Builder().build()
        adView3?.loadAd(adRequest3)
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
                val zakuski = zakuskiList[index]
                zakuskiDatabase.zakuskiDao().deleteZakuski(zakuski)
                getData()
                adapterzakuski.notifyDataSetChanged()
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
