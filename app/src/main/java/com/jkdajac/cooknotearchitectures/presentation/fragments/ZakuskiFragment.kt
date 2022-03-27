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
import kotlinx.android.synthetic.main.fragment_zakuski.*


class ZakuskiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_zakuski, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdMob3()
    }
    override fun onResume() {
        super.onResume()
        adView3?.resume()
    }
    override fun onPause() {
        super.onPause()
        adView3?.pause()
    }
    override fun onDestroy() {
        super.onDestroy()
        adView3?.destroy()
    }
    private fun initAdMob3(){
        MobileAds.initialize(context)
        val adRequest3 = AdRequest.Builder().build()
        adView3?.loadAd(adRequest3)
    }

    companion object {

        @JvmStatic
        fun newInstance() {
        }

    }
    }
