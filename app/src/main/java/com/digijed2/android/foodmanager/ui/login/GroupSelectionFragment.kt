package com.digijed2.android.foodmanager.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.digijed2.android.foodmanager.databinding.FragmentGroupSelectionBinding
import com.digijed2.android.foodmanager.repository.CommodityRepository
import com.digijed2.android.foodmanager.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class GroupSelectionFragment : Fragment() {
// Цей фрагмент використовується для заповнення поля groupId у користувачів
// Користувач може або додатись до існуючої групи або створити нову
// При додаванні до існуючої групи перевіряється її існування
    private lateinit var binding: FragmentGroupSelectionBinding
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

        binding = FragmentGroupSelectionBinding.inflate(layoutInflater)

        //AuthUI.getInstance().signOut(requireContext())
        binding.newGroupBtn.setOnClickListener {
            var query: Query
            val groupsHeadRef =
                FirebaseDatabase.getInstance().reference.child("groups")
            query = groupsHeadRef
            val key = query.ref.push().key
            FirebaseAuth.getInstance().currentUser?.also {
                UserRepository.getUserByFirebaseUser(it)
                { user ->
                    if (key != null) {
                    user.groupId = key
                        CommodityRepository.groupId = key
                        groupsHeadRef.child(key).child("members")
                            .child(user.uid)
                            .setValue(user.uid)
                    }
                    UserRepository().createOrUpdateUser(user)
                    authStateListener?.onAuthStateChanged()

                }
            }
        }

        binding.joinGroupBtn.setOnClickListener {
            if (binding.groupIdText.text.isEmpty())
                Toast.makeText(requireContext(), "Введіть номер групи", Toast.LENGTH_SHORT).show()
            else {
                val groupsHeadRef =
                    FirebaseDatabase.getInstance().reference.child("groups")
                groupsHeadRef.child(binding.groupIdText.text.toString())
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                FirebaseAuth.getInstance().currentUser?.also {
                                    UserRepository.getUserByFirebaseUser(it)
                                    { user ->

                                        user.groupId = binding.groupIdText.text.toString()

                                        CommodityRepository.groupId =
                                            binding.groupIdText.text.toString()
                                        groupsHeadRef.child(binding.groupIdText.text.toString())
                                            .child("members").child(user.uid)
                                            .setValue(user.uid)

                                        UserRepository().createOrUpdateUser(user)
                                        authStateListener?.onAuthStateChanged()

                                    }
                                }
                            } else
                                Toast.makeText(
                                    requireContext(),
                                    "Помилка групи не існує",
                                    Toast.LENGTH_SHORT
                                ).show()

                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(
                                requireContext(),
                                "Помилка спробуйте пізніше",
                                Toast.LENGTH_SHORT
                            )
                        }
                    })

            }
        }

        return binding.root
    }


}