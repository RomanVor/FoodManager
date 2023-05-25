package com.digijed2.android.foodmanager

import android.content.ClipboardManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.digijed2.android.foodmanager.landing.LandingFragment
import com.digijed2.android.foodmanager.repository.CommodityRepository
import com.digijed2.android.foodmanager.repository.UserRepository
import com.digijed2.android.foodmanager.ui.login.LoginFragment
import com.digijed2.android.foodmanager.ui.login.GroupSelectionFragment
import com.digijed2.android.foodmanager.ui.login.onAuthStateListener
import com.digijed2.android.foodmanager.utils.replaceFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), onAuthStateListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showFragment()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        } else {
            super.onBackPressed()
        }
    }


    private fun showFragment() {
        val user = FirebaseAuth.getInstance().currentUser

        if (user == null) {
            replaceFragment(R.id.mainfragmentContiner, LoginFragment())
        } else {
            UserRepository.getUserByFirebaseUser(user)
            { user ->
                 if (user!!.groupId=="")
                     replaceFragment(R.id.mainfragmentContiner, GroupSelectionFragment())
                 else {
                     CommodityRepository.groupId= user!!.groupId.toString()
                     replaceFragment(R.id.mainfragmentContiner, LandingFragment())
                 }

            }
        }
    }

    override fun onAuthStateChanged() {
        showFragment()
    }
}