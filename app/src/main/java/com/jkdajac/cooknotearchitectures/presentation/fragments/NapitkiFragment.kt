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
import com.jkdajac.cooknotearchitectures.presentation.adapters.NapitkiAdapter
import com.jkdajac.cooknotearchitectures.presentation.editactivities.EditNapitkiActivity
import com.jkdajac.data.roomstorage.AppDatabase
import com.jkdajac.data.roomstorage.entity.Napitki
import kotlinx.android.synthetic.main.fragment_napitki.*

lateinit var napitkiDatabase : AppDatabase
lateinit var napitkiList: ArrayList<Napitki>
lateinit var adapternapitki : NapitkiAdapter

class NapitkiFragment : Fragment(), NapitkiAdapter.ViewHolder.ItemCallback {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_napitki, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        napitkiList = ArrayList<Napitki>()
        napitkiDatabase = context?.let { AppDatabase.getDatabase(it) }!!
        getData()
        adapternapitki = NapitkiAdapter(requireContext(), napitkiList, this)
        rvNapitki.layoutManager = LinearLayoutManager(context)
        rvNapitki.adapter = adapternapitki

        napitkiDatabase = AppDatabase.getDatabase(requireContext())

        fabAddNapitki.setOnClickListener {
            val intent = Intent(requireContext(), EditNapitkiActivity::class.java)
            startActivity(intent)
        }
        initAdMob10()
    }
    fun getData() {
        val wordFromDb: List<Napitki> = napitkiDatabase.napitkiDao().getAll()
        napitkiList.clear()
        napitkiList.addAll(wordFromDb)
    }

    override fun onResume() {
        super.onResume()
        adView10?.resume()
    }
    override fun onPause() {
        super.onPause()
        adView10?.pause()
    }
    override fun onDestroy() {
        super.onDestroy()
        adView10?.destroy()
    }
    private fun initAdMob10(){
        MobileAds.initialize(requireContext())
        val adRequest10 = AdRequest.Builder().build()
        adView10?.loadAd(adRequest10)
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
                val napitki = napitkiList[index]
                napitkiDatabase.napitkiDao().deleteNapitki(napitki)
                getData()
                adapternapitki.notifyDataSetChanged()
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