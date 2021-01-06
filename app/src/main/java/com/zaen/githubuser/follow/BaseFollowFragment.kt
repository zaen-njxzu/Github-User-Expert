package com.zaen.githubuser.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaen.githubuser.core.ui.UsersAdapter
import com.zaen.githubuser.databinding.FragmentBaseFollowBinding

open class BaseFollowFragment : Fragment() {

    private var _binding: FragmentBaseFollowBinding? = null
    protected val binding get() = _binding!!

    protected lateinit var followAdapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBaseFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycleView()
    }

    private fun setupRecycleView() {
        followAdapter = UsersAdapter()
        binding.rvFollowUser.apply {
            adapter = followAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}