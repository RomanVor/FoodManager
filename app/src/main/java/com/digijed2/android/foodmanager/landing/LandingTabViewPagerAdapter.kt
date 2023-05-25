package com.digijed2.android.foodmanager.landing

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.digijed2.android.foodmanager.ui.main.CommodityListFragment
import com.digijed2.android.foodmanager.ui.main.CommodityShopListFragment
import com.digijed2.android.foodmanager.ui.main.GroupFragment
import com.digijed2.android.foodmanager.ui.placehoder.PlaceholderFragment

val Tabs = arrayOf(
    "Продукти",
    "Список покупок",
    "Группа"
)

class LandingTabViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int) = when (position) {
        0 -> {
            CommodityListFragment()
        }
        1 -> {
            CommodityShopListFragment()
        }
        2 -> {
            GroupFragment()
        }
        else -> PlaceholderFragment()
    }

    override fun getItemCount() = Tabs.size
}