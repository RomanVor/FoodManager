package com.digijed2.android.foodmanager.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.digijed2.android.foodmanager.databinding.FragmentCommodityListBinding
import com.digijed2.android.foodmanager.databinding.FragmentShopCommodityBinding
import com.digijed2.android.foodmanager.repository.Commodity
import com.digijed2.android.foodmanager.repository.CommodityRepository
import com.digijed2.android.foodmanager.repository.UserRepository
import com.digijed2.android.foodmanager.ui.adapters.CommodityShopListAdapter
import com.digijed2.android.foodmanager.ui.layoutManager.CommodityLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth

class CommodityShopListFragment : Fragment() {
    private lateinit var binding: FragmentShopCommodityBinding
    private lateinit var adapter: CommodityShopListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentShopCommodityBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val options = FirebaseRecyclerOptions.Builder<Commodity>()
            .setLifecycleOwner(this)
            .setQuery(
                CommodityRepository().getCommodities().orderByChild("needed").equalTo(true),
                Commodity::class.java
            )
            .build()

        adapter = CommodityShopListAdapter(options)

        val manager = CommodityLayoutManager(requireContext())
        binding.list.layoutManager = manager
        binding.list.adapter = adapter


    }

}