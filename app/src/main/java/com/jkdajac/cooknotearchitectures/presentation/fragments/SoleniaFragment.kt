package com.jkdajac.cooknotearchitectures.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.jkdajac.cooknotearchitectures.R
import kotlinx.android.synthetic.main.fragment_solenia.*
import kotlinx.android.synthetic.main.fragment_soup.*


class SoleniaFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_solenia, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdMob8()
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
        MobileAds.initialize(context)
        val adRequest8 = AdRequest.Builder().build()
        adView8?.loadAd(adRequest8)
    }

    companion object {
        @JvmStatic
        fun newInstance() {
        }
    }
    }
