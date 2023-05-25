package com.digijed2.android.foodmanager.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.digijed2.android.foodmanager.R
import com.digijed2.android.foodmanager.repository.Commodity
import com.digijed2.android.foodmanager.repository.CommodityRepository
import com.digijed2.android.foodmanager.repository.User
import com.digijed2.android.foodmanager.repository.UserRepository
import com.digijed2.android.foodmanager.ui.main.CommodityListFragment
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class MembersListAdapter(
    private val options: FirebaseRecyclerOptions<String>,
    private val onItemClickListener: CommodityListFragment.OnItemClickListener
) : FirebaseRecyclerAdapter<String, MembersListAdapter.ViewHolder>(options) {

    class ViewHolder(
        view: View,
    ) : RecyclerView.ViewHolder(view) {

        private val userName = view.findViewById<TextView>(R.id.userName)
        private val userPhoto = view.findViewById<ImageView>(R.id.userImage)

        fun bind(uid: String) {
            UserRepository.getUserById(uid){
                user->
                    userName.text=user?.displayName ?: "Unknown user"
                    if(user?.photo?.isNotEmpty() == true)
                    {
                        Picasso.get().load(user.photo)
                            .placeholder(R.drawable.ic_anon_user)
                            .into(userPhoto)
                    }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder= ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.group_member_item, parent, false)
        )
        holder.itemView.setOnLongClickListener {
            onItemClickListener.onItemLongClick(options.snapshots[holder.absoluteAdapterPosition])
            return@setOnLongClickListener true
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: String) {
        holder.bind(model)
    }
}