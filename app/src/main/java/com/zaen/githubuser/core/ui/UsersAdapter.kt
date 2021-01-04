package com.zaen.githubuser.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zaen.githubuser.R
import com.zaen.githubuser.core.domain.model.UserInfo
import com.zaen.githubuser.databinding.ItemUserInfoPreviewBinding

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.UsersInfoViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<UserInfo>() {
        override fun areItemsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean {
            return oldItem.userGithubUrl == newItem.userGithubUrl
        }

        override fun areContentsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersInfoViewHolder {
        return UsersInfoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_user_info_preview,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: UsersInfoViewHolder, position: Int) {
        val userInfo = differ.currentList[position]
        holder.bind(userInfo)
    }

    inner class UsersInfoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemUserInfoPreviewBinding.bind(itemView)
        fun bind(data: UserInfo) {
            binding.apply {
                Glide.with(itemView.context).load(data.profileImageUrl).into(ivProfileImage)
                tvUsername.text = data.username
                tvGithubLink.text = data.userGithubUrl
                itemView.setOnClickListener {
                    onItemClickListener?.let {
                        it(data)
                    }
                }
            }
        }
    }


    private var onItemClickListener: ((UserInfo) -> Unit)? = null

    fun setOnItemClickListener(listener: (UserInfo) -> Unit) {
        onItemClickListener = listener
    }
}