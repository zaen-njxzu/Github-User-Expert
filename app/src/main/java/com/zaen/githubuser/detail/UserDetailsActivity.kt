package com.zaen.githubuser.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.zaen.githubuser.core.data.Resource
import com.zaen.githubuser.core.domain.model.UserInfo
import com.zaen.githubuser.databinding.ActivityUserDetailsBinding
import com.zaen.githubuser.follow.constant.FollowStates
import com.zaen.githubuser.follow.pager.FollowPagerAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class UserDetailsActivity : AppCompatActivity() {

    private val viewModel: UserDetailsViewModel by viewModel()

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private var _binding: ActivityUserDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.topAppBar.title = "Detail User"

        val detailUser = intent.getParcelableExtra<UserInfo>(EXTRA_DATA)
        detailUser?.apply {
            binding.fab.setOnClickListener {
                viewModel.saveFavoriteUser(this)
                Snackbar.make(it, "User favorite saved successfully", Snackbar.LENGTH_SHORT).show()
            }

            attachTabLayout(this)
            observeAndUpdateUserDetails(this)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun attachTabLayout(userInfo: UserInfo) {
        val screensCount = 2
        binding.pager.adapter = FollowPagerAdapter(
            this,
            screensCount,
            userInfo.username
        )
        binding.pager.offscreenPageLimit = screensCount
        binding.pager.currentItem = 0

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            when(position) {
                FollowStates.Followers.ordinal -> tab.text = "Followers"
                FollowStates.Following.ordinal -> tab.text = "Following"
                else -> tab.text = "Undefined"
            }
        }.attach()
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun observeAndUpdateUserDetails(userInfo: UserInfo) {
        viewModel.getUserDetail(userInfo.username).observe(this, { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { userInfo ->
                        MainScope().launch(Dispatchers.Main) {
                            binding.tvUsername.text = userInfo.username
                            binding.tvName.text = userInfo.name
                            binding.tvGithubLink.text = userInfo.userGithubUrl
                            binding.tvRepos.text = userInfo.publicRepos.toString()
                            binding.tvCreatedAt.text = userInfo.createdAt.toDateString()
                            Glide.with(this@UserDetailsActivity).load(userInfo.profileImageUrl).into(binding.ivProfileImage)
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(this, "An error occured: $message", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun String.toDateString() : String {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        try {
            return formatter.format(parser.parse(this))
        }catch (e: ParseException) {
            e.printStackTrace()
        }
        return this
    }
}