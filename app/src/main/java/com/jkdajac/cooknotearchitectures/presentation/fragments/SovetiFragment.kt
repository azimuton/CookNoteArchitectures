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
import com.jkdajac.cooknotearchitectures.presentation.adapters.SovetiAdapter
import com.jkdajac.cooknotearchitectures.presentation.editactivities.EditSovetiActivity
import com.jkdajac.data.roomstorage.AppDatabase
import com.jkdajac.data.roomstorage.entity.Soveti
import kotlinx.android.synthetic.main.fragment_soveti.*
lateinit var sovetiDatabase : AppDatabase
lateinit var sovetiList: ArrayList<Soveti>
lateinit var adaptersoveti : SovetiAdapter
class SovetiFragment : Fragment(), SovetiAdapter.ViewHolder.ItemCallback {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_soveti, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sovetiList = ArrayList<Soveti>()
        sovetiDatabase = context?.let { AppDatabase.getDatabase(it) }!!
        getData()
        adaptersoveti = SovetiAdapter(requireContext(), sovetiList, this)
        rvSoveti.layoutManager = LinearLayoutManager(context)
        rvSoveti.adapter = adaptersoveti

        sovetiDatabase = AppDatabase.getDatabase(requireContext())

        fabAddSoveti.setOnClickListener {
            val intent = Intent(requireContext(), EditSovetiActivity::class.java)
            startActivity(intent)
        }
        initAdMob9()
    }
    fun getData() {
        val wordFromDb: List<Soveti> = sovetiDatabase.sovetiDao().getAll()
        sovetiList.clear()
        sovetiList.addAll(wordFromDb)
    }
    override fun onResume() {
        super.onResume()
        adView9?.resume()
    }
    override fun onPause() {
        super.onPause()
        adView9?.pause()
    }
    override fun onDestroy() {
        super.onDestroy()
        adView9?.destroy()
    }
    private fun initAdMob9(){
        MobileAds.initialize(requireContext())
        val adRequest9 = AdRequest.Builder().build()
        adView9?.loadAd(adRequest9)
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
                val soveti = sovetiList[index]
                sovetiDatabase.sovetiDao().deleteSoveti(soveti)
                getData()
                adaptersoveti.notifyDataSetChanged()
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
