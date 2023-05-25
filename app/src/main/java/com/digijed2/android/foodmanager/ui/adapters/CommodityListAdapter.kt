package com.digijed2.android.foodmanager.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.digijed2.android.foodmanager.R
import com.digijed2.android.foodmanager.repository.Commodity
import com.digijed2.android.foodmanager.repository.CommodityRepository
import com.digijed2.android.foodmanager.ui.dialog.CommodityDeleteDialog
import com.digijed2.android.foodmanager.ui.main.CommodityListFragment
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CommodityListAdapter(
    private val options: FirebaseRecyclerOptions<Commodity>,
    private val onItemClickListener: CommodityListFragment.OnItemClickListener
) : FirebaseRecyclerAdapter<Commodity, CommodityListAdapter.ViewHolder>(options) {

    class ViewHolder(
        view: View,
    ) : RecyclerView.ViewHolder(view) {
        private var commodity: Commodity? = null

        private val commodityName = view.findViewById<TextView>(R.id.commodityName)
        private val decreaseBtn = view.findViewById<FloatingActionButton>(R.id.decreaseBtn)
        private val increaseBtn = view.findViewById<FloatingActionButton>(R.id.increaseBtn)
        private val amount = view.findViewById<TextView>(R.id.amount)


        init {
            increaseBtn.setOnClickListener {

                this.commodity!!.amount += 1
                this.commodity?.let { commodity ->
                    CommodityRepository().createOrUpdateCommodity(commodity)

                }
            }
            decreaseBtn.setOnClickListener {
                if (this.commodity!!.amount > 0) {
                    this.commodity!!.amount -= 1

                    this.commodity?.let { commodity ->
                        CommodityRepository().createOrUpdateCommodity(commodity)

                    }
                }
            }
        }

        fun bind(commodity: Commodity) {
            this.commodity = commodity
            this.commodityName.text = commodity.name
            this.amount.text = commodity.amount.toString()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.commodity_item, parent, false)
        )

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(
                options.snapshots[holder.absoluteAdapterPosition]
            )
        }

        holder.itemView.setOnLongClickListener {
            onItemClickListener.onItemLongClick(options.snapshots[holder.absoluteAdapterPosition])
            return@setOnLongClickListener true
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Commodity) {
        holder.bind(model)
    }


}