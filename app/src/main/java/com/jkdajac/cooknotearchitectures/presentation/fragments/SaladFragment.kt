package com.jkdajac.cooknotearchitectures.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.jkdajac.cooknotearchitectures.R
import kotlinx.android.synthetic.main.fragment_salad.*
import kotlinx.android.synthetic.main.fragment_soup.*


class SaladFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_salad, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdMob4()
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
        MobileAds.initialize(context)
        val adRequest4 = AdRequest.Builder().build()
        adView4?.loadAd(adRequest4)
    }

    companion object {

        @JvmStatic
        fun newInstance() {
        }
    }
    }
