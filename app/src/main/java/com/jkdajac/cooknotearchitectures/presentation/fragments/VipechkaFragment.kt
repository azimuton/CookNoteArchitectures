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
import kotlinx.android.synthetic.main.fragment_vipechka.*


class VipechkaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vipechka, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdMob5()
    }
    override fun onResume() {
        super.onResume()
        adView5?.resume()
    }
    override fun onPause() {
        super.onPause()
        adView5?.pause()
    }
    override fun onDestroy() {
        super.onDestroy()
        adView5?.destroy()
    }
    private fun initAdMob5(){
        MobileAds.initialize(context)
        val adRequest5 = AdRequest.Builder().build()
        adView5?.loadAd(adRequest5)
    }

    companion object {
        @JvmStatic
        fun newInstance() {
        }
    }
    }
