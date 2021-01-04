package com.zaen.githubuser

import android.os.Bundle

import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.zaen.githubuser.databinding.ActivityGithubUsersBinding

class GithubUsersActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private var _binding: ActivityGithubUsersBinding? = null
    public val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGithubUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)
        setupAppbarConfiguration()
    }

    private fun setupAppbarConfiguration() {
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.action_favorite))

        val navController = findNavController(R.id.nav_host_github_users_fragment)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.topAppBar.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.top_app_bar, menu)

        super.onCreateOptionsMenu(menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val navController = findNavController(R.id.nav_host_github_users_fragment)
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.action_favorite -> {
                    navController.navigate(R.id.savedUsersFragment, null, navOptions.build())
                    true
                }
                else -> false
            }
        }

        super.onPrepareOptionsMenu(menu)
        return true
    }

}
