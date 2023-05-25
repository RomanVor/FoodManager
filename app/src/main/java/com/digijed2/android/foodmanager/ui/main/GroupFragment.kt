package com.digijed2.android.foodmanager.ui.main

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import com.digijed2.android.foodmanager.databinding.FragmentGroupBinding
import com.digijed2.android.foodmanager.repository.Commodity
import com.digijed2.android.foodmanager.repository.CommodityRepository
import com.digijed2.android.foodmanager.ui.adapters.MembersListAdapter
import com.digijed2.android.foodmanager.ui.dialog.MemberDeleteDialog
import com.digijed2.android.foodmanager.ui.layoutManager.CommodityLayoutManager
import com.digijed2.android.foodmanager.ui.login.onAuthStateListener
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class GroupFragment : Fragment() {
    // Цей фрагмент відображає користувачів у группі
    private lateinit var binding: FragmentGroupBinding
    private lateinit var adapter: MembersListAdapter



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
    ): View {

        binding = FragmentGroupBinding.inflate(layoutInflater)
        binding.fab.setOnClickListener {
            val clipboardManager = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            clipboardManager.setPrimaryClip(ClipData.newPlainText("text", CommodityRepository.groupId))
            Toast.makeText(
                requireContext(),
                "Id групи скопійовано",
                Toast.LENGTH_SHORT
            ).show()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val options = FirebaseRecyclerOptions.Builder<String>()
            .setLifecycleOwner(this)
            .setQuery(
                FirebaseDatabase.getInstance().getReference("groups")
                    .child(CommodityRepository.groupId).child("members"), String::class.java
            )
            .build()

        adapter = MembersListAdapter(options, object :
            CommodityListFragment.OnItemClickListener {
            override fun onItemClick(commodity: Commodity) {
                TODO("Not yet implemented")

            }

            override fun onItemLongClick(commodity: Commodity) {
                TODO("Not yet implemented")
            }

            override fun onItemLongClick(uid: String) {

                if (FirebaseAuth.getInstance().currentUser?.uid != uid)
                    MemberDeleteDialog(uid)
                        .show(childFragmentManager, "Delete dialog")
                else
                {
                    AuthUI.getInstance().signOut(requireContext())
                    MemberDeleteDialog(uid, authStateListener)
                        .show(childFragmentManager, "Delete dialog")
                }

            }
        })

        val manager = CommodityLayoutManager(requireContext())
        binding.list.layoutManager = manager
        binding.list.adapter = adapter
    }

}