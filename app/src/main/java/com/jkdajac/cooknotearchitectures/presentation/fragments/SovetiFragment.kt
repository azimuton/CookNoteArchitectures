package com.jkdajac.cooknotearchitectures.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.jkdajac.cooknotearchitectures.R
import kotlinx.android.synthetic.main.fragment_soup.*
import kotlinx.android.synthetic.main.fragment_soveti.*

class SovetiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_soveti, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdMob9()
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
        MobileAds.initialize(context)
        val adRequest9 = AdRequest.Builder().build()
        adView9?.loadAd(adRequest9)
    }

    companion object {
        @JvmStatic
        fun newInstance() {
        }

    }
    }
