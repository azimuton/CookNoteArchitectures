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
import com.jkdajac.cooknotearchitectures.presentation.adapters.SousAdapter
import com.jkdajac.cooknotearchitectures.presentation.editactivities.EditSousActivity
import com.jkdajac.data.roomstorage.AppDatabase
import com.jkdajac.data.roomstorage.entity.Sous
import kotlinx.android.synthetic.main.fragment_sous.*

lateinit var sousDatabase : AppDatabase
lateinit var sousList: ArrayList<Sous>
lateinit var adaptersous : SousAdapter
class SousFragment : Fragment(), SousAdapter.ViewHolder.ItemCallback {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sous, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sousList = ArrayList<Sous>()
        sousDatabase = context?.let { AppDatabase.getDatabase(it) }!!
        getData()
        adaptersous = SousAdapter(requireContext(), sousList, this)
        rvSous.layoutManager = LinearLayoutManager(context)
        rvSous.adapter = adaptersous

        sousDatabase = AppDatabase.getDatabase(requireContext())

        fabAddSous.setOnClickListener {
            val intent = Intent(requireContext(), EditSousActivity::class.java)
            startActivity(intent)
        }
        initAdMob7()
    }
    fun getData() {
        val wordFromDb: List<Sous> = sousDatabase.sousDao().getAll()
        sousList.clear()
        sousList.addAll(wordFromDb)
    }
    override fun onResume() {
        super.onResume()
        adView7?.resume()
    }
    override fun onPause() {
        super.onPause()
        adView7?.pause()
    }
    override fun onDestroy() {
        super.onDestroy()
        adView7?.destroy()
    }
    private fun initAdMob7(){
        MobileAds.initialize(requireContext())
        val adRequest7 = AdRequest.Builder().build()
        adView7?.loadAd(adRequest7)
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
                val sous = sousList[index]
                sousDatabase.sousDao().deleteSous(sous)
                getData()
                adaptersous.notifyDataSetChanged()
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
