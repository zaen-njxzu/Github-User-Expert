package com.zaen.githubuser.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AbsListView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zaen.githubuser.GithubUsersActivity
import com.zaen.githubuser.R
import com.zaen.githubuser.core.ui.UsersAdapter
import com.zaen.githubuser.util.Constants.Companion.SEARCH_GITHUB_USERS_TIME_DELAY
import com.zaen.githubuser.core.data.Resource
import com.zaen.githubuser.databinding.FragmentSearchUsersBinding
import com.zaen.githubuser.detail.UserDetailsActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class SearchUsersFragment : Fragment() {

    private val viewModel: SearchUsersViewModel by viewModel()

    private var _binding: FragmentSearchUsersBinding? = null
    private val binding get() = _binding!!

    private lateinit var usersInfoAdapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        setupTitleTopbar()
        setupRecycleView()
        setupOnClickUserDetailsListener()
        setupUsernameListenerWithFetchData()
    }

    override fun onPause() {
        super.onPause()
        hideSoftKeyboard()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_favorite).isVisible = true
        super.onPrepareOptionsMenu(menu)
    }

    private fun setupTitleTopbar() {
        (activity as GithubUsersActivity).binding.topAppBar.title = "Github Users"
    }

    private fun observeAndUpdateListOfUsers(query: String) {
        viewModel.searchUsers(query).observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { it ->
                        usersInfoAdapter.differ.submitList(it)
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

    private fun hideSoftKeyboard() {
        context?.apply {
            val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            view?.apply {
                inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
            }
        }
    }

    private fun setupUsernameListenerWithFetchData() {
        var job: Job? = null
        binding.etSearch.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideSoftKeyboard()
                return@OnEditorActionListener true
            }
            false
        })

        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_GITHUB_USERS_TIME_DELAY)
                editable?.let {
                    if(editable.toString().isNotEmpty()) {
                        observeAndUpdateListOfUsers(editable.toString())
                    }
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupOnClickUserDetailsListener() {
        usersInfoAdapter.setOnItemClickListener {
            val intent = Intent(activity, UserDetailsActivity::class.java)
            intent.putExtra(UserDetailsActivity.EXTRA_DATA, it)
            startActivity(intent)
        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                hideSoftKeyboard()
            }
        }
    }

    private fun setupRecycleView() {
        usersInfoAdapter = UsersAdapter()
        binding.rvUsers.apply {
            adapter = usersInfoAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(scrollListener)
        }
    }

}