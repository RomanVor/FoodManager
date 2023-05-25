package com.digijed2.android.foodmanager.landing

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.digijed2.android.foodmanager.databinding.FragmentLandingBinding
import com.digijed2.android.foodmanager.ui.login.onAuthStateListener
import com.google.android.material.tabs.TabLayoutMediator

class LandingFragment: Fragment(), onAuthStateListener {

    private lateinit var binding:FragmentLandingBinding
    private var authStateListener: onAuthStateListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is onAuthStateListener)
            authStateListener = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLandingBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = LandingTabViewPagerAdapter(this)

        TabLayoutMediator(binding.tabLayout,binding.viewPager){tab, position ->
            tab.text= Tabs[position]
        }.attach()
    }

    override fun onAuthStateChanged() {
        authStateListener?.onAuthStateChanged()
    }


}