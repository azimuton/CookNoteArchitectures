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
import com.jkdajac.cooknotearchitectures.presentation.adapters.SoleniaAdapter
import com.jkdajac.cooknotearchitectures.presentation.editactivities.EditSoleniaActivity
import com.jkdajac.data.roomstorage.AppDatabase
import com.jkdajac.data.roomstorage.entity.Solenia
import kotlinx.android.synthetic.main.fragment_solenia.*

lateinit var soleniaDatabase : AppDatabase
lateinit var soleniaList: ArrayList<Solenia>
lateinit var adaptersolenia : SoleniaAdapter
class SoleniaFragment : Fragment(), SoleniaAdapter.ViewHolder.ItemCallback {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_solenia, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        soleniaList = ArrayList<Solenia>()
        soleniaDatabase = context?.let { AppDatabase.getDatabase(it) }!!
        getData()
        adaptersolenia = SoleniaAdapter(requireContext(), soleniaList, this)
        rvSolenia.layoutManager = LinearLayoutManager(context)
        rvSolenia.adapter = adaptersolenia

        soleniaDatabase = AppDatabase.getDatabase(requireContext())

        fabAddSolenia.setOnClickListener {
            val intent = Intent(requireContext(), EditSoleniaActivity::class.java)
            startActivity(intent)
        }
        initAdMob8()
    }
    fun getData() {
        val wordFromDb: List<Solenia> = soleniaDatabase.soleniaDao().getAll()
        soleniaList.clear()
        soleniaList.addAll(wordFromDb)
    }
    override fun onResume() {
        super.onResume()
        adView8?.resume()
    }
    override fun onPause() {
        super.onPause()
        adView8?.pause()
    }
    override fun onDestroy() {
        super.onDestroy()
        adView8?.destroy()
    }
    private fun initAdMob8(){
        MobileAds.initialize(requireContext())
        val adRequest8 = AdRequest.Builder().build()
        adView8?.loadAd(adRequest8)
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
                val solenia = soleniaList[index]
                soleniaDatabase.soleniaDao().deleteSolenia(solenia)
                getData()
                adaptersolenia.notifyDataSetChanged()
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
