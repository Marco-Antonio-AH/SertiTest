package com.example.serti.ui.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.serti.R
import com.example.serti.databinding.ItemUserBinding
import com.example.serti.model.UserModel

class UsersAdapter(
    private val onItemClick: (Int) -> Unit
) : ListAdapter<UserModel, UsersAdapter.UserViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserModel>() {
            override fun areItemsTheSame(old: UserModel, new: UserModel) =
                old.id == new.id

            override fun areContentsTheSame(old: UserModel, new: UserModel) =
                old == new
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UserViewHolder(
        private val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private var currentUser: UserModel? = null

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(user: UserModel) {
            currentUser = user

            binding.tvName.text  = "${user.firstName} ${user.lastName}"
            binding.tvEmail.text = user.email
            binding.tvId.text    = "#${user.id}"


            Glide.with(binding.imgAvatar.context)
                .load(user.avatar)
                .circleCrop()
                .placeholder(R.drawable.ic_user_placeholder)
                .into(binding.imgAvatar)
        }

        override fun onClick(v: View?) {
            currentUser?.let { onItemClick(it.id) }
        }
    }
}
