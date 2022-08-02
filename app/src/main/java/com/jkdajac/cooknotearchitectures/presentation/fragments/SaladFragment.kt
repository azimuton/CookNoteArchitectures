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
import com.jkdajac.cooknotearchitectures.presentation.adapters.SaladAdapter
import com.jkdajac.cooknotearchitectures.presentation.editactivities.EditSaladActivity
import com.jkdajac.data.roomstorage.AppDatabase
import com.jkdajac.data.roomstorage.entity.Salad
import kotlinx.android.synthetic.main.fragment_salad.*

lateinit var saladDatabase : AppDatabase
lateinit var saladList: ArrayList<Salad>
lateinit var adaptersalad : SaladAdapter
class SaladFragment : Fragment(), SaladAdapter.ViewHolder.ItemCallback {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_salad, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saladList = ArrayList<Salad>()
        saladDatabase = context?.let { AppDatabase.getDatabase(it) }!!
        getData()
        adaptersalad = SaladAdapter(requireContext(), saladList, this)
        rvSalad.layoutManager = LinearLayoutManager(context)
        rvSalad.adapter = adaptersalad

        saladDatabase = AppDatabase.getDatabase(requireContext())

        fabAddSalad.setOnClickListener {
            val intent = Intent(requireContext(), EditSaladActivity::class.java)
            startActivity(intent)
        }

        initAdMob4()
    }
    fun getData() {
        val wordFromDb: List<Salad> = saladDatabase.saladDao().getAll()
        saladList.clear()
        saladList.addAll(wordFromDb)
    }
    override fun onResume() {
        super.onResume()
        adView4?.resume()
    }
    override fun onPause() {
        super.onPause()
        adView4?.pause()
    }
    override fun onDestroy() {
        super.onDestroy()
        adView4?.destroy()
    }
    private fun initAdMob4(){
        MobileAds.initialize(requireContext())
        val adRequest4 = AdRequest.Builder().build()
        adView4?.loadAd(adRequest4)
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
                val salad = saladList[index]
                saladDatabase.saladDao().deleteSalad(salad)
                getData()
                adaptersalad.notifyDataSetChanged()
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
