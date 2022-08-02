package com.jkdajac.cooknotearchitectures.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.jkdajac.cooknotearchitectures.presentation.adapters.MeatAdapter
import com.jkdajac.cooknotearchitectures.presentation.editactivities.EditMeatActivity
import com.jkdajac.cooknotearchitectures.R
import com.jkdajac.data.roomstorage.AppDatabase
import com.jkdajac.data.roomstorage.entity.Meat
import kotlinx.android.synthetic.main.fragment_meat.*

lateinit var adapter: MeatAdapter
lateinit var meatDatabase : AppDatabase
lateinit var meatList: ArrayList<Meat>

class MeatFragment : Fragment(), MeatAdapter.ViewHolder.ItemCallback {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdMob()

        meatList = ArrayList<Meat>()
        meatDatabase = context?.let { AppDatabase.getDatabase(it) }!!
        getData()
        adapter = MeatAdapter(requireContext(), meatList, this)
        rvMeat.layoutManager = LinearLayoutManager(context)
        rvMeat.adapter = adapter

        meatDatabase = AppDatabase.getDatabase(requireContext())

        fabAddMeat.setOnClickListener {
            val intent = Intent(requireContext(), EditMeatActivity::class.java)
            startActivity(intent)
        }
    }

    fun getData() {
        val wordFromDb: List<Meat> = meatDatabase.meatDao().getAll()
        meatList.clear()
        meatList.addAll(wordFromDb)
    }
    override fun deleteItem(index: Int) {


        val addDialog = AlertDialog.Builder(requireContext())
        addDialog
            .setMessage("Вы действительно хотите удалить запись?")
            .setPositiveButton("Ok") { dialog, _ ->
                val meat = meatList[index]
                meatDatabase.meatDao().deleteMeat(meat)
                getData()
                adapter.notifyDataSetChanged()
                Toast.makeText(requireContext(), "Запись удалена!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
    override fun onResume() {
        super.onResume()
        adView?.resume()
    }
    override fun onPause() {
        super.onPause()
        adView?.pause()
    }
    override fun onDestroy() {
        super.onDestroy()
        adView?.destroy()
    }
    private fun initAdMob(){
        MobileAds.initialize(requireContext())
        val adRequest = AdRequest.Builder().build()
        adView?.loadAd(adRequest)
    }

    companion object {
        @JvmStatic
        fun newInstance() {
        }
    }
}