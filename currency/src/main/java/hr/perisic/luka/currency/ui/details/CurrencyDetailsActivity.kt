package hr.perisic.luka.currency.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import hr.perisic.luka.currency.R

internal class CurrencyDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_details)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setupNavGraph()
        setupActionBar()
    }

    private fun setupNavGraph() {
        findNavController(R.id.navHostFragment)
            .setGraph(R.navigation.nav_graph_currency_details, intent.extras)
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return findNavController(R.id.navHostFragment)
            .navigateUp()
    }

}