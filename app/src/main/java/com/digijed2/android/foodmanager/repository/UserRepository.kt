package com.digijed2.android.foodmanager.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserRepository {


    fun createOrUpdateUser(user: User) {
        Thread {
            Firebase.database
                .getReference("users")
                .child(user.uid)
                .setValue(user)
        }.start()
    }

    companion object {
        fun deleteUserFromGroup(uid: String) {
            Thread {
                Firebase.database
                    .getReference("groups")
                    .child(CommodityRepository.groupId)
                    .child("members")
                    .child(uid)
                    .setValue(null)

                Firebase.database
                    .getReference("users")
                    .child(uid)
                    .child("groupId")
                    .setValue("")
            }.start()
        }

        fun mapFromFirebaseUser(user: FirebaseUser) = User(
            user.uid,
            user.displayName ?: "unknown user",
            user.photoUrl.toString(),
            ""
        )

        fun getUserByFirebaseUser(userFb: FirebaseUser, callback: (User) -> Unit) {
            val usersRef = Firebase.database.reference.child("users")
            val userRef = usersRef.child(userFb.uid)
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)

                    if (user != null) {
                        callback(user)
                    } else
                        callback(mapFromFirebaseUser(userFb))

                }

                override fun onCancelled(error: DatabaseError) {
                    callback(mapFromFirebaseUser(userFb))
                }
            })
        }

        fun getUserById(uid: String, callback: (User?) -> Unit) {
            val usersRef = Firebase.database.reference.child("users")
            val userRef = usersRef.child(uid)
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var user = snapshot.getValue(User::class.java)

                    if (user != null) {
                        callback(user)
                    } else
                        callback(null)

                }

                override fun onCancelled(error: DatabaseError) {
                    callback(null)
                }
            })
        }

    }
}

