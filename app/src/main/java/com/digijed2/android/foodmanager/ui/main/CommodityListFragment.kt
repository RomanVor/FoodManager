package com.digijed2.android.foodmanager.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.digijed2.android.foodmanager.databinding.FragmentCommodityListBinding
import com.digijed2.android.foodmanager.repository.Commodity
import com.digijed2.android.foodmanager.repository.CommodityRepository
import com.digijed2.android.foodmanager.ui.adapters.CommodityListAdapter
import com.digijed2.android.foodmanager.ui.dialog.CommodityDeleteDialog
import com.digijed2.android.foodmanager.ui.layoutManager.CommodityLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions

class CommodityListFragment : Fragment() {
    private lateinit var binding: FragmentCommodityListBinding
    private lateinit var adapter: CommodityListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCommodityListBinding.inflate(layoutInflater)

        binding.fab.setOnClickListener {
            AddCommodityFragment()
                .show(childFragmentManager, "Edit Commodity")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        // AuthUI.getInstance().signOut(requireContext())
    }

    private fun initRecyclerView() {
        val options = FirebaseRecyclerOptions.Builder<Commodity>()
            .setLifecycleOwner(this)
            .setQuery(CommodityRepository().getCommodities(), Commodity::class.java)
            .build()

        adapter = CommodityListAdapter(options, object : OnItemClickListener {
            override fun onItemClick(commodity: Commodity) {
                AddCommodityFragment(commodity)
                    .show(childFragmentManager, "Edit Commodity")
            }

            override fun onItemLongClick(commodity: Commodity) {
                CommodityDeleteDialog(commodity)
                    .show(childFragmentManager, "Delete dialog")

            }

            override fun onItemLongClick(uid: String) {
                TODO("Not yet implemented")
            }
        })

        val manager = CommodityLayoutManager(requireContext())
        binding.list.layoutManager = manager
        binding.list.adapter = adapter


    }

    interface OnItemClickListener {
        fun onItemClick(commodity: Commodity)
        fun onItemLongClick(commodity: Commodity)
        fun onItemLongClick(uid: String)
    }

}