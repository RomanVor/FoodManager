package com.digijed2.android.foodmanager.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.digijed2.android.foodmanager.R
import com.digijed2.android.foodmanager.repository.Commodity
import com.digijed2.android.foodmanager.repository.CommodityRepository
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CommodityShopListAdapter(
    options: FirebaseRecyclerOptions<Commodity>
) : FirebaseRecyclerAdapter<Commodity, CommodityShopListAdapter.ViewHolder>(options) {

    class ViewHolder(
        view: View,
    ) : RecyclerView.ViewHolder(view) {
        private var commodity: Commodity? = null

        private val commodityName = view.findViewById<TextView>(R.id.commodityName)
        private val submitBtn = view.findViewById<FloatingActionButton>(R.id.submitBtn)
        private val amount = view.findViewById<TextView>(R.id.amount)


        init {
            submitBtn.setOnClickListener {
                this.commodity!!.amount += (commodity!!.minAmount- commodity!!.amount)
                this.commodity?.let { commodity ->
                    CommodityRepository().createOrUpdateCommodity(commodity)

                }
            }

        }

        fun bind(commodity: Commodity) {
            this.commodity = commodity
            this.commodityName.text = commodity.name
            this.amount.text =(commodity.minAmount- commodity.amount).toString()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.shop_commodity_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Commodity) {
        holder.bind(model)

    }





}