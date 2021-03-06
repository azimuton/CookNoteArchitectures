package com.jkdajac.cooknotearchitectures.presentation.fragments

import android.annotation.SuppressLint
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
import com.jkdajac.cookingnote.adapters.MeatAdapter
import com.jkdajac.cooknotearchitectures.R
import com.jkdajac.cooknotearchitectures.presentation.adapters.SoupAdapter
import com.jkdajac.cooknotearchitectures.presentation.editactivities.EditSoupActivity
import com.jkdajac.data.storage.AppDatabase
import com.jkdajac.data.storage.entity.Meat
import com.jkdajac.data.storage.entity.Soup
import kotlinx.android.synthetic.main.fragment_meat.*
import kotlinx.android.synthetic.main.fragment_soup.*

@SuppressLint("StaticFieldLeak")
lateinit var soupDatabase : AppDatabase
lateinit var soupList: ArrayList<Soup>
lateinit var adaptersoup : SoupAdapter

class SoupFragment : Fragment(), SoupAdapter.ViewHolder.ItemCallback  {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_soup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        soupList = ArrayList<Soup>()
        soupDatabase = context?.let { AppDatabase.getDatabase(it) }!!
        getData()
        adaptersoup = SoupAdapter(requireContext(), soupList, this)
        rvSoup.layoutManager = LinearLayoutManager(context)
        rvSoup.adapter = adaptersoup

        soupDatabase = AppDatabase.getDatabase(requireContext())

        fabAddSoup.setOnClickListener {
            val intent = Intent(requireContext(), EditSoupActivity::class.java)
            startActivity(intent)
        }

        initAdMob2()
    }

    fun getData() {
        val wordFromDb: List<Soup> = soupDatabase.soupDao().getAll()
        soupList.clear()
        soupList.addAll(wordFromDb)
    }

    override fun deleteItem(index: Int) {
        val addDialog = AlertDialog.Builder(requireContext())
        addDialog
            .setMessage("???? ?????????????????????????? ???????????? ?????????????? ?????????????")
            .setPositiveButton("Ok") { dialog, _ ->
                val soup = soupList[index]
                soupDatabase.soupDao().deleteSoup(soup)
                getData()
                adaptersoup.notifyDataSetChanged()
                Toast.makeText(requireContext(), "???????????? ??????????????!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton("????????????") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onResume() {
        super.onResume()
        adView2?.resume()
    }
    override fun onPause() {
        super.onPause()
        adView2?.pause()
    }
    override fun onDestroy() {
        super.onDestroy()
        adView2?.destroy()
    }
    private fun initAdMob2(){
        MobileAds.initialize(context)
        val adRequest2 = AdRequest.Builder().build()
        adView2?.loadAd(adRequest2)
    }

    companion object {
        @JvmStatic
        fun newInstance() {
        }
    }
    }
