package com.digijed2.android.foodmanager.ui.login

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.digijed2.android.foodmanager.R
import com.digijed2.android.foodmanager.repository.UserRepository
import com.digijed2.android.foodmanager.repository.UserRepository.Companion.mapFromFirebaseUser
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    // Цей фрагмент авторизує користувача
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doLogin()
    }

    private val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build()
    )

    private fun doLogin() {
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setLogo(R.drawable.ic_logo)
            .setIsSmartLockEnabled(false)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private var authStateListener: onAuthStateListener?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is onAuthStateListener)
            authStateListener=context
    }
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    private fun onSignInResult(res: FirebaseAuthUIAuthenticationResult?) {
        if (res?.resultCode == AppCompatActivity.RESULT_OK) {
            FirebaseAuth.getInstance().currentUser?.also {
                UserRepository.getUserByFirebaseUser(it)
                {
                    user->
                    if (user != null) {
                        UserRepository().createOrUpdateUser(user)
                    }
                }

                authStateListener?.onAuthStateChanged()
            } ?: Toast.makeText(requireContext(), "LogIn error", Toast.LENGTH_SHORT).show()
        } else {
            if (res?.idpResponse == null) {
                requireActivity().finish()
            } else {
                Toast.makeText(requireContext(), "LogIn error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}