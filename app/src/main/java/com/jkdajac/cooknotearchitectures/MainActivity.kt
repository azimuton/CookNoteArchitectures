package com.jkdajac.cooknotearchitectures

import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.jkdajac.cooknotearchitectures.presentation.adapters.MainViewPagerAdapter
import com.jkdajac.cooknotearchitectures.presentation.fragments.DepthPageTransformer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val  w : Window = window
        w.decorView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // скрываем нижнюю панель навигации
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) //появляется поверх активити и исчезает
        setContentView(R.layout.activity_main)
        initial()
    }

//    fun animate() {
//        val sunRiseAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.alfaperehod)
//        vpMainPager.startAnimation(sunRiseAnimation)
//    }
    private fun initial() {
        vpMainPager.adapter = MainViewPagerAdapter(this)
        tlTabMain.tabIconTint = null
        TabLayoutMediator(tlTabMain, vpMainPager){
                tab, pos ->
            when(pos) {
                0 -> {
                    tab.text = "Мясо и рыба"
                    tab.icon?.setTint(ContextCompat.getColor(this, R.color.tab_icon))
                    vpMainPager.setPageTransformer(DepthPageTransformer())
                }
                1 -> {
                    tab.text = "Первые блюда"
                    tab.icon?.setTint(ContextCompat.getColor(this, R.color.tab_icon))
                    vpMainPager.setPageTransformer(DepthPageTransformer())
                }
                2 -> {
                    tab.text = "Закуски"
                    tab.icon?.setTint(ContextCompat.getColor(this, R.color.tab_icon))
                    vpMainPager.setPageTransformer(DepthPageTransformer())
                }
                3 -> {
                    tab.text = "Салаты"
                    tab.icon?.setTint(ContextCompat.getColor(this, R.color.tab_icon))
                    vpMainPager.setPageTransformer(DepthPageTransformer())
                }
                4 -> {
                    tab.text = "Выпечка"
                    tab.icon?.setTint(ContextCompat.getColor(this, R.color.tab_icon))
                    vpMainPager.setPageTransformer(DepthPageTransformer())
                }
                5 -> {
                    tab.text = "Десерты"
                    tab.icon?.setTint(ContextCompat.getColor(this, R.color.tab_icon))
                    vpMainPager.setPageTransformer(DepthPageTransformer())
                }
                6 -> {
                    tab.text = "Соусы"
                    tab.icon?.setTint(ContextCompat.getColor(this, R.color.tab_icon))
                    vpMainPager.setPageTransformer(DepthPageTransformer())
                }
                7 -> {
                    tab.text = "Соленья"
                    tab.icon?.setTint(ContextCompat.getColor(this, R.color.tab_icon))
                    vpMainPager.setPageTransformer(DepthPageTransformer())
                }
                8 -> {
                    tab.text = "Полезные советы"
                    tab.icon?.setTint(ContextCompat.getColor(this, R.color.tab_icon))
                    vpMainPager.setPageTransformer(DepthPageTransformer())
                }
            }

        }.attach()
    }

    override fun onResume() {
        super.onResume()
        val  w : Window = window
        w.decorView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // скрываем нижнюю панель навигации
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) //появляется поверх активити и исчезает
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}