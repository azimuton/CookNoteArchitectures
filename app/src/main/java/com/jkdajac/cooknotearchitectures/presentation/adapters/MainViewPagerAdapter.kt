package com.jkdajac.cooknotearchitectures.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jkdajac.cooknotearchitectures.presentation.fragments.*

class MainViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 10
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> MeatFragment()
            1 -> SoupFragment()
            2 -> ZakuskiFragment()
            3 -> SaladFragment()
            4 -> VipechkaFragment()
            5 -> DesertiFragment()
            6 -> SousFragment()
            7 -> SoleniaFragment()
            8 -> NapitkiFragment()
            else -> SovetiFragment()
        }
    }
}