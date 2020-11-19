package com.example.koshelek_ru_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.koshelek_ru_test.data.App
import com.example.koshelek_ru_test.ui.diff.DiffBaseFragment
import com.example.koshelek_ru_test.ui.home.HomeBaseFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var homeFragment: HomeBaseFragment
    private lateinit var diffFragment: DiffBaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragments()
        startFragment(homeFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home_menu_item -> startFragment(homeFragment)
                R.id.diff_menu_item -> startFragment(diffFragment)
            }
            true
        }
    }

    private fun initFragments() {
        homeFragment = HomeBaseFragment()
        diffFragment = DiffBaseFragment()
    }

    private fun startFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_home_screen, fragment)
            commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.btcusdt_menu_item -> App.symbol = "BTCUSDT"
            R.id.bnbbtc_menu_item -> App.symbol = "BNBBTC"
            R.id.ethbtc_menu_item -> App.symbol = "ETHBTC"
        }
        return super.onOptionsItemSelected(item)
    }
}