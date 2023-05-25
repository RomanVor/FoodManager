package com.digijed2.android.foodmanager.repository

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val uid: String="",
    val displayName:String="",
    var photo:String? ="",
    var groupId:String?
): Parcelable
{
    constructor() : this("", "", "","")
}