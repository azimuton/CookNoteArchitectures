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
import com.jkdajac.cooknotearchitectures.presentation.adapters.SoupAdapter
import com.jkdajac.cooknotearchitectures.presentation.adapters.ZakuskiAdapter
import com.jkdajac.cooknotearchitectures.presentation.editactivities.EditSoupActivity
import com.jkdajac.cooknotearchitectures.presentation.editactivities.EditZakuskiActivity
import com.jkdajac.data.storage.AppDatabase
import com.jkdajac.data.storage.entity.Soup
import com.jkdajac.data.storage.entity.Zakuski
import kotlinx.android.synthetic.main.fragment_soup.*
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
        MobileAds.initialize(context)
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
            .setMessage("???? ?????????????????????????? ???????????? ?????????????? ?????????????")
            .setPositiveButton("Ok") { dialog, _ ->
                val zakuski = zakuskiList[index]
                zakuskiDatabase.zakuskiDao().deleteZakuski(zakuski)
                getData()
                adapterzakuski.notifyDataSetChanged()
                Toast.makeText(requireContext(), "???????????? ??????????????!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton("????????????") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}
