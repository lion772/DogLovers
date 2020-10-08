package com.example.dogapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.dogapp.R
import com.example.dogapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()
    private lateinit var appBarConfiguration : AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_activity)

        setupNavigation()

    }

    private fun setupNavigation() {
        val host: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment?
                ?: return

        val navController = host.navController
        val drawerLayout: DrawerLayout? = findViewById(R.id.drawer_layout)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.listFragment,
                R.id.settingsFragment),
            drawerLayout
        )

        setupActionBar(navController, appBarConfiguration)
        setupNavigationMenu(navController)
    }

    private fun setupActionBar(navController: NavController, appBarConfig: AppBarConfiguration){
        setupActionBarWithNavController(navController, appBarConfig)
    }

    private fun setupNavigationMenu(navController: NavController) {
        nav_view?.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp() =
        findNavController(R.id.host_fragment).navigateUp(appBarConfiguration)


}