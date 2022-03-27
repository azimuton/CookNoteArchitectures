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
import kotlinx.android.synthetic.main.fragment_sous.*


class SousFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sous, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdMob7()
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
        MobileAds.initialize(context)
        val adRequest7 = AdRequest.Builder().build()
        adView7?.loadAd(adRequest7)
    }

    companion object {
        @JvmStatic
        fun newInstance() {
        }
    }
    }
