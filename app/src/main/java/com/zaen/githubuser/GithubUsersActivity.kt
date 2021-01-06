package com.zaen.githubuser

import android.content.Intent
import android.net.Uri
import android.os.Bundle

import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.zaen.githubuser.databinding.ActivityGithubUsersBinding
import com.zaen.githubuser.search.SearchUsersFragment

class GithubUsersActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private var _binding: ActivityGithubUsersBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGithubUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mFragmentManager = supportFragmentManager

        val mSearchUsersFragment = SearchUsersFragment()
        val fragment = mFragmentManager.findFragmentByTag(SearchUsersFragment::class.java.simpleName)

        if (fragment !is SearchUsersFragment) {
            mFragmentManager
                .beginTransaction()
                .add(R.id.frame_container, mSearchUsersFragment, SearchUsersFragment::class.java.simpleName)
                .commit()
        }

        setSupportActionBar(binding.topAppBar)
        setupAppbarConfiguration()
    }

    private fun setupAppbarConfiguration() {
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.action_favorite))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.top_app_bar, menu)

        super.onCreateOptionsMenu(menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.action_favorite -> {
                    val uri = Uri.parse("saved://favorite")
                    startActivity(Intent(Intent.ACTION_VIEW, uri))
                    true
                }
                else -> false
            }
        }

        super.onPrepareOptionsMenu(menu)
        return true
    }

}
