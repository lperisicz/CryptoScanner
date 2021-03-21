package hr.perisic.luka.cryptoscanner.ui.main

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import hr.perisic.luka.cryptoscanner.R
import hr.perisic.luka.base.BaseActivity
import hr.perisic.luka.cryptoscanner.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutId: Int = R.layout.activity_main

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        val navController = findNavController(R.id.navHostFragment)
        NavigationUI.setupWithNavController(
            binding.bottomNavigationHome, navController
        )
    }

}