package com.javedkhan.newsapp.android.view.activities.main

import com.javedkhan.newsapp.android.R
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.javedkhan.newsapp.android.BR
import com.javedkhan.newsapp.android.databinding.ActivitySplashBinding
import com.javedkhan.newsapp.android.view.base.BaseActivity
import com.novoda.merlin.Merlin
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(), SplashNavigator {
    override val viewModel: SplashViewModel by viewModels()
    override val bindingVariable: Int
    get() = BR.viewModel

    override val layoutId: Int
    get() = R.layout.activity_splash

    private lateinit var navHost: NavHostFragment
    private lateinit var graph: NavGraph
    private lateinit var navController: NavController
    private lateinit var merlin: Merlin

    override fun decideNextActivity() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        viewModel.setNavigator(this)
        try {
            setUpNavFragment()

        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        merlin.bind()
    }

    override fun onPause() {
        merlin.unbind()
        super.onPause()
    }

    private fun setUpNavFragment() {
        navHost =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?)!!
        navController = navHost.navController
        val navInflater = navController.navInflater
        graph = navInflater.inflate(R.navigation.home_nav_graph)
        merlin = Merlin.Builder().withConnectableCallbacks().build(this)
    }


}