package com.zaen.githubuser.follow.pager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zaen.githubuser.detail.UserDetailsActivity
import com.zaen.githubuser.follow.BaseFollowFragment
import com.zaen.githubuser.follow.FollowersFragment
import com.zaen.githubuser.follow.FollowingsFragment
import com.zaen.githubuser.util.Constants.Companion.FOLLOW_ARG_OBJECT_USERNAME
import com.zaen.githubuser.follow.constant.FollowStates

class FollowPagerAdapter(fragment: UserDetailsActivity, private val showItemCount: Int, private val username: String) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return showItemCount
    }

    override fun createFragment(position: Int): Fragment {
        var fragment = BaseFollowFragment()

        when(position) {
            FollowStates.Followers.ordinal -> fragment = FollowersFragment()
            FollowStates.Following.ordinal -> fragment = FollowingsFragment()
        }

        fragment.arguments = Bundle().apply {
            putString(FOLLOW_ARG_OBJECT_USERNAME, username)
        }

        return fragment
    }
}