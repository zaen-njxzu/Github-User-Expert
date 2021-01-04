package com.zaen.githubuser.follow

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.zaen.githubuser.util.Constants
import com.zaen.githubuser.core.data.Resource
import org.koin.android.viewmodel.ext.android.viewModel

class FollowingsFragment : BaseFollowFragment() {

    private val viewModel: FollowingsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.takeIf { it.containsKey(Constants.FOLLOW_ARG_OBJECT_USERNAME) }?.apply {
            val username = getString(Constants.FOLLOW_ARG_OBJECT_USERNAME)

            username?.let {
                observeAndUpdateFollowingUser(it)
            }
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun observeAndUpdateFollowingUser(username: String) {
        viewModel.getFollowingsData(username).observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { usersResponse ->
                        followAdapter.differ.submitList(usersResponse.toList())
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
}