package com.digijed2.android.foodmanager.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.digijed2.android.foodmanager.repository.Commodity
import com.digijed2.android.foodmanager.repository.CommodityRepository

class CommodityDeleteDialog(val commodity:Commodity): DialogFragment()  {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
            .setTitle(commodity.name)
            .setPositiveButton("Так") { _, _ ->
                CommodityRepository().deleteCommodity(commodity)
            }
            .setNegativeButton("Ні", null)
            .setMessage("Ви впевнені що хочете видалити цей продукт?")

        return builder.create()
    }
}