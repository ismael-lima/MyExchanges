package com.master.myexchanges.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.master.myexchanges.R
import com.master.myexchanges.domain.User
import com.master.myexchanges.ui.BaseActivity
import com.master.myexchanges.ui.signin.SignInActivity
import com.master.myexchanges.util.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val sharedViewModel: SharedViewModel by viewModel()
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var tvNomeHeader: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_exchange, R.id.nav_history
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)

        var header = navView.getHeaderView(0)
        tvNomeHeader = header.findViewById(R.id.tvNomeHeader)
        setUpBaseComponents()
        setUpInitialData()
        setUpObservers()
    }

    private fun setUpObservers() {
        mainViewModel.observe(
            this,
            this::startWork,
            this::finishWork,
            this::showError
        )

        mainViewModel.user.observe(this, Observer {
            if(::tvNomeHeader.isInitialized) {
                tvNomeHeader.text = it.name
            }
        })

        mainViewModel.signOut.observe(this, Observer {
            val intentLogin = Intent(this, SignInActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                putExtra(Constants.Input.USER, mainViewModel.user.value)
            }
            startActivity(intentLogin)
        })
    }

    private fun setUpBaseComponents()
    {
        progress = progressBar
        baseSnack = layout
    }
    private fun setUpInitialData() {
        intent.extras?.let {
            sharedViewModel.user = it.getSerializable(Constants.Input.USER) as User
            mainViewModel.user.value = sharedViewModel.user
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        when (item.itemId) {
            R.id.nav_exchange -> {
                navController.navigate(R.id.nav_exchange)
                supportActionBar?.setTitle(R.string.exchange)
            }
            R.id.nav_history -> {
                navController.navigate(R.id.nav_history)
                supportActionBar?.setTitle(R.string.exchange_history)
            }
            R.id.nav_sign_out -> {
                mainViewModel.logout()
            }
        }

        drawer_layout.closeDrawers()
        return true
    }
}
