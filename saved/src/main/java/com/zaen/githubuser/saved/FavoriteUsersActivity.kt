package com.zaen.githubuser.saved

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.zaen.githubuser.core.ui.UsersAdapter
import com.zaen.githubuser.detail.UserDetailsActivity
import org.koin.android.viewmodel.ext.android.viewModel
import androidx.lifecycle.Observer
import com.zaen.githubuser.saved.databinding.ActivityFavoriteUsersBinding
import com.zaen.githubuser.saved.di.savedModule
import org.koin.core.context.loadKoinModules

class FavoriteUsersActivity : AppCompatActivity() {

    private val viewModel: FavoriteUsersViewModel by viewModel()
    private lateinit var usersInfoAdapter: UsersAdapter

    private var _binding: ActivityFavoriteUsersBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityFavoriteUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadKoinModules(savedModule)

        binding.topAppBar.title = "Favorite Users"

        setupSwipeActionDeleteUser()
        setupRecycleView()
        setupOnClickUserDetailsListener()
        observeAndUpdateListOfSavedUsers()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun setupSwipeActionDeleteUser() {
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
                Snackbar.make(binding.root, "Successfully deleted user", Snackbar.LENGTH_LONG).apply {
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
        viewModel.favoriteUsers.observe(this, Observer { usersInfo ->
            usersInfoAdapter.differ.submitList(usersInfo)
        })
    }

    private fun setupOnClickUserDetailsListener() {
        usersInfoAdapter.setOnItemClickListener {
            val intent = Intent(this, UserDetailsActivity::class.java)
            intent.putExtra(UserDetailsActivity.EXTRA_DATA, it)
            startActivity(intent)
        }
    }

    private fun setupRecycleView() {
        usersInfoAdapter = UsersAdapter()
        binding.rvUsers.apply {
            adapter = usersInfoAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}