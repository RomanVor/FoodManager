package com.digijed2.android.foodmanager.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.digijed2.android.foodmanager.repository.CommodityRepository
import com.digijed2.android.foodmanager.repository.UserRepository
import com.digijed2.android.foodmanager.ui.login.onAuthStateListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MemberDeleteDialog(

    private val uid: String,
    private var authStateListener: onAuthStateListener? = null): DialogFragment()  {
// Цей діалог підтверджує бажання видалити користувача із групи

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
            .setTitle("Видалити?")
            .setPositiveButton("Так") { _, _ ->
               UserRepository.deleteUserFromGroup(uid)
                authStateListener?.onAuthStateChanged()
            }
            .setNegativeButton("Ні", null)
            .setMessage("Ви впевнені що хочете видалити цього користувача?")

        return builder.create()
    }
}