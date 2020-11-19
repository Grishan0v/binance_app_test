package com.example.koshelek_ru_test.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.koshelek_ru_test.R
import com.example.koshelek_ru_test.ui.TabPagerAdapter
import kotlinx.android.synthetic.main.fragment_home_base.*

class HomeBaseFragment : Fragment() {
    private lateinit var homeBidTableFragment: HomeBidTableFragment
    private lateinit var homeAskTableFragment: HomeAskTableFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBidTableFragment = HomeBidTableFragment()
        homeAskTableFragment = HomeAskTableFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager(viewpager_home)
        tabLayoutHome.setupWithViewPager(viewpager_home)

    }

    private fun setupViewPager(viewpagerHome: ViewPager) {
        val adapter = TabPagerAdapter(childFragmentManager)
        adapter.addFragment(homeBidTableFragment, "Bid")
        adapter.addFragment(homeAskTableFragment, "Ask")
        viewpagerHome.adapter = adapter
    }
}