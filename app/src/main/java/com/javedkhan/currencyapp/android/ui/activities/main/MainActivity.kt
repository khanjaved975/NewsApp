package com.javedkhan.currencyapp.android.ui.activities.main


import android.content.Intent
import com.javedkhan.currencyapp.android.R
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.javedkhan.currencyapp.android.BR
import com.javedkhan.currencyapp.android.databinding.ActivitySplashBinding
import com.javedkhan.currencyapp.android.ui.base.BaseActivity
import com.novoda.merlin.Merlin
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(), SplashNavigator {
    override val viewModel: SplashViewModel by viewModels()
    override val bindingVariable: Int
    get() = BR.viewModel

    override val layoutId: Int
    get() = R.layout.activity_splash

    lateinit var navHost: NavHostFragment
    lateinit var graph: NavGraph
    lateinit var navController: NavController
    lateinit var parentLayout: View
    lateinit var merlin: Merlin

    override fun decideNextActivity() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        viewModel.setNavigator(this)
        //viewModel.startSeeding()
        try {
           // parentLayout = findViewById<View>(android.R.id.content)
            setUpNavFragment()

        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        merlin.bind();
    }

    override fun onPause() {
        merlin.unbind()
        super.onPause()
    }

    fun setUpNavFragment() {
        navHost =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?)!!
        navController = navHost!!.navController
        val navInflater = navController.navInflater
        graph = navInflater.inflate(R.navigation.home_nav_graph)
        merlin = Merlin.Builder().withConnectableCallbacks().build(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}