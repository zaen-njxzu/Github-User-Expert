package com.zaen.githubuser.userdetails

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.zaen.githubuser.GithubUsersActivity
import com.zaen.githubuser.R
import com.zaen.githubuser.follow.pager.FollowPagerAdapter
import com.zaen.githubuser.follow.constant.FollowStates
import com.zaen.githubuser.core.data.Resource
import kotlinx.android.synthetic.main.activity_github_users.*
import kotlinx.android.synthetic.main.fragment_user_details.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class UserDetailsFragment : Fragment(R.layout.fragment_user_details) {

    private val viewModel: UserDetailsViewModel by viewModel()
    private val args: UserDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener {
            viewModel.saveFavoriteUser(args.userInfo)
            Snackbar.make(view, "User favorite saved successfully", Snackbar.LENGTH_SHORT).show()
        }

        setupTitleTopbar()
        observeAndUpdateUserDetails()
        attachTabLayout()
    }

    private fun setupTitleTopbar() {
        (activity as GithubUsersActivity).binding.topAppBar.title = "Detail User"
    }

    private fun attachTabLayout() {
        val screensCount = 2
        pager.adapter = FollowPagerAdapter(
            this,
            screensCount,
            args.userInfo.username
        )
        pager.offscreenPageLimit = screensCount
        pager.currentItem = 0

        TabLayoutMediator(tab_layout, pager) { tab, position ->
            when(position) {
                FollowStates.Followers.ordinal -> tab.text = "Followers"
                FollowStates.Following.ordinal -> tab.text = "Following"
                else -> tab.text = "Undefined"
            }
        }.attach()
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun observeAndUpdateUserDetails() {
        val user = args.userInfo

        viewModel.getUserDetail(user.username).observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { userInfo ->
                        MainScope().launch(Dispatchers.Main) {
                            tvUsername.text = userInfo.username
                            tvName.text = userInfo.name
                            tvGithubLink.text = userInfo.userGithubUrl
                            tvRepos.text = userInfo.publicRepos.toString()
                            tvCreatedAt.text = userInfo.createdAt.toDateString()
                            Glide.with(this@UserDetailsFragment).load(userInfo.profileImageUrl).into(ivProfileImage)
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    fun String.toDateString() : String {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        try {
            val output = formatter.format(parser.parse(this))
            return output
        }catch (e: ParseException) {
            e.printStackTrace()
        }
        return this
    }

}