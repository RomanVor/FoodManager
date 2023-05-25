package com.digijed2.android.foodmanager.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import com.digijed2.android.foodmanager.R
import com.digijed2.android.foodmanager.databinding.FragmentAddCommodityBinding
import com.digijed2.android.foodmanager.repository.Commodity
import com.digijed2.android.foodmanager.repository.CommodityRepository

class AddEditCommodityFragment(commodity: Commodity? = null) : DialogFragment() {
//Цей фрагмент створює новий або редагує існуючий продукт
    private val oldCommodity = MutableLiveData(commodity)
    private lateinit var binding: FragmentAddCommodityBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCommodityBinding.inflate(layoutInflater)


        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.FullScreen
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (oldCommodity.value == null) {
            oldCommodity.value = Commodity()
        }
        oldCommodity.observe(this) {
            it.let {
                binding.title.editText?.setText(it?.name ?: "")
                binding.minAmount.setText(it?.minAmount.toString())
                binding.currAmount.setText(it?.amount.toString())
            }

        }

        binding.clearBtn.setOnClickListener {
            binding.title.editText?.setText("")
            binding.minAmount.setText("0")
            binding.currAmount.setText("0")
        }
        binding.saveBtn.setOnClickListener {
            oldCommodity.value?.name = binding.title.editText?.text.toString()
            oldCommodity.value?.minAmount = binding.minAmount.text.toString().toInt()
            oldCommodity.value?.amount = binding.currAmount.text.toString().toInt()
            oldCommodity.value?.let { it1 ->
                CommodityRepository().createOrUpdateCommodity(
                    it1
                )
            }
            dismiss()
        }
    }

}