package com.zaen.githubuser.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.zaen.githubuser.GithubUsersActivity
import com.zaen.githubuser.R
import com.zaen.githubuser.core.ui.UsersAdapter
import com.zaen.githubuser.databinding.FragmentFavoriteUsersBinding
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteUsersFragment : Fragment() {

    private val viewModel: FavoriteUsersViewModel by viewModel()

    private var _binding: FragmentFavoriteUsersBinding? = null
    private val binding get() = _binding!!

    private lateinit var usersInfoAdapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSwipeActionDeleteUser(view)
        setupTitleTopbar()
        setupRecycleView()
        setupOnClickUserDetailsListener()
        observeAndUpdateListOfSavedUsers()
    }

    private fun setupSwipeActionDeleteUser(view: View) {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val favoriteUser = usersInfoAdapter.differ.currentList[position]
                viewModel.deleteUser(favoriteUser)
                Snackbar.make(view, "Successfully deleted user", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.saveUser(favoriteUser)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvUsers)
        }
    }

    private fun observeAndUpdateListOfSavedUsers() {
        viewModel.favoriteUsers.observe(viewLifecycleOwner, Observer { usersInfo ->
            usersInfoAdapter.differ.submitList(usersInfo)
        })
    }

    private fun setupOnClickUserDetailsListener() {
        usersInfoAdapter.setOnItemClickListener {

            val bundle = Bundle().apply {
                putParcelable("user_info", it)
            }
            findNavController().navigate(
                R.id.action_savedUsersFragment_to_githubDetailUserFragment,
                bundle
            )
        }
    }

    private fun setupRecycleView() {
        usersInfoAdapter = UsersAdapter()
        binding.rvUsers.apply {
            adapter = usersInfoAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun setupTitleTopbar() {
        (activity as GithubUsersActivity).binding.topAppBar.title = "Favorite Users"
    }
}