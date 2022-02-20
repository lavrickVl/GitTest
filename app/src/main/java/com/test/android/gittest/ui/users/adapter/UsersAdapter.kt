package com.test.android.gittest.ui.users.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.test.android.gittest.R
import com.test.android.gittest.databinding.ItemUserCardBinding
import com.test.android.gittest.domain.model.User
import com.test.android.gittest.util.interfaces.INavigationListener

class UsersAdapter(private val _users: List<User>, private val navigationListener: INavigationListener? = null) :
    RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private var users = _users

    inner class UserViewHolder(val binding: ItemUserCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            ItemUserCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.apply {
            Glide.with(itemView)
                .load(users[position].urlAvatar)
                .placeholder(R.drawable.ic_baseline_image_48)
                .transform(CircleCrop())
                .into(binding.ivAvatar)


            binding.textView.text = users[position].name

            binding.root.setOnClickListener{
                navigationListener?.navigateToDetails(users[position], binding.ivAvatar)
            }


        }

    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun updateUsersList(list: List<User>) {
        users = list
        notifyDataSetChanged()
    }

}