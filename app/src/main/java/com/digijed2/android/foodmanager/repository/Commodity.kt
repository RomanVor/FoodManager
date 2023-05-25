package com.digijed2.android.foodmanager.repository

import kotlin.math.min

data class Commodity(
    var id:String="",
    var name: String="",
    var amount:Int=0,
    var minAmount:Int =1,
    var needed:Boolean = amount< minAmount
)