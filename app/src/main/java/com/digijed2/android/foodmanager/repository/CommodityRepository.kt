package com.digijed2.android.foodmanager.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CommodityRepository {
    fun createOrUpdateCommodity(commodity: Commodity) {
        Thread {
            if (commodity.id == "") {
                commodity.id = Firebase.database
                    .getReference("groups")
                    .child(groupId)
                    .child("Commodities")
                    .push().key.toString()
            }
            commodity!!.needed = commodity!!.amount < commodity!!.minAmount
            Firebase.database
                .getReference("groups")
                .child(groupId)
                .child("Commodities")
                .child(commodity.id)
                .setValue(commodity)


        }.start()
    }

    fun deleteCommodity(commodity: Commodity) {
        Thread {

                    Firebase.database
                        .getReference("groups")
                        .child(groupId)
                        .child("Commodities")
                        .child(commodity.id)
                        .setValue(null)
        }.start()
    }

    fun getCommodities(): DatabaseReference =
        FirebaseDatabase.getInstance().getReference("groups").child(groupId).child("Commodities")

    companion object{
         var groupId:String=""
    }

}

