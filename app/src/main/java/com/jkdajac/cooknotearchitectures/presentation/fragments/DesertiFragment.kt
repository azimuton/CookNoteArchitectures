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
import com.jkdajac.cooknotearchitectures.presentation.adapters.DesertiAdapter
import com.jkdajac.cooknotearchitectures.presentation.editactivities.EditDesertiActivity
import com.jkdajac.data.roomstorage.AppDatabase
import com.jkdajac.data.roomstorage.entity.Deserti
import kotlinx.android.synthetic.main.fragment_deserti.*

lateinit var desertiDatabase : AppDatabase
lateinit var desertiList: ArrayList<Deserti>
lateinit var adapterdeserti : DesertiAdapter
class DesertiFragment : Fragment(), DesertiAdapter.ViewHolder.ItemCallback {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_deserti, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        desertiList = ArrayList<Deserti>()
        desertiDatabase = context?.let { AppDatabase.getDatabase(it) }!!
        getData()
        adapterdeserti = DesertiAdapter(requireContext(), desertiList, this)
        rvDeserti.layoutManager = LinearLayoutManager(context)
        rvDeserti.adapter = adapterdeserti

        desertiDatabase = AppDatabase.getDatabase(requireContext())

        fabAddDeserti.setOnClickListener {
            val intent = Intent(requireContext(), EditDesertiActivity::class.java)
            startActivity(intent)
        }
        initAdMob6()
    }
    fun getData() {
        val wordFromDb: List<Deserti> = desertiDatabase.desertiDao().getAll()
        desertiList.clear()
        desertiList.addAll(wordFromDb)
    }
    override fun onResume() {
        super.onResume()
        adView6?.resume()
    }
    override fun onPause() {
        super.onPause()
        adView6?.pause()
    }
    override fun onDestroy() {
        super.onDestroy()
        adView6?.destroy()
    }
    private fun initAdMob6(){
        MobileAds.initialize(requireContext())
        val adRequest6 = AdRequest.Builder().build()
        adView6?.loadAd(adRequest6)
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
                val deserti = desertiList[index]
                desertiDatabase.desertiDao().deleteDeserti(deserti)
                getData()
                adapterdeserti.notifyDataSetChanged()
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

