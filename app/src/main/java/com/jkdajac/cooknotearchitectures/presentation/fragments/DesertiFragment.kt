package com.jkdajac.cooknotearchitectures.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.jkdajac.cooknotearchitectures.R
import kotlinx.android.synthetic.main.fragment_deserti.*
import kotlinx.android.synthetic.main.fragment_soup.*

class DesertiFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_deserti, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdMob6()
    }
    override fun onResume() {
        super.onResume()
        adView6?.resume()
    }
    override fun onPause() {
        super.onPause()
        adView6?.pause()
    }
    override fun onDestroy() {
        super.onDestroy()
        adView6?.destroy()
    }
    private fun initAdMob6(){
        MobileAds.initialize(context)
        val adRequest6 = AdRequest.Builder().build()
        adView6?.loadAd(adRequest6)
    }

    companion object {

        @JvmStatic
        fun newInstance() {
        }

    }
            }

