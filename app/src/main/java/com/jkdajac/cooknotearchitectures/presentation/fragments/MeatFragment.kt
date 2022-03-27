package com.jkdajac.cooknotearchitectures.presentation.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.jkdajac.cooknotearchitectures.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_meat.*


class MeatFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdMob()
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
        MobileAds.initialize(context)
        val adRequest = AdRequest.Builder().build()
        adView?.loadAd(adRequest)
    }

    companion object {
        @JvmStatic
        fun newInstance() {
        }
    }
}