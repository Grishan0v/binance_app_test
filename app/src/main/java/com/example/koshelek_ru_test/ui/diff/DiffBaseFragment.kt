package com.example.koshelek_ru_test.ui.diff

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.koshelek_ru_test.R
import com.example.koshelek_ru_test.ui.TabPagerAdapter
import kotlinx.android.synthetic.main.fragment_diff_base.*

class DiffBaseFragment : Fragment() {

    private lateinit var diffBidTableFragment: DiffBidTableFragment
    private lateinit var diffAskTableFragment: DiffAskTableFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        diffAskTableFragment = DiffAskTableFragment()
        diffBidTableFragment = DiffBidTableFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_diff_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager(viewpager_diff)
        tabLayoutDiff.setupWithViewPager(viewpager_diff)
    }

    private fun setupViewPager(viewpagerHome: ViewPager) {
        val adapter = TabPagerAdapter(childFragmentManager)
        adapter.addFragment(diffBidTableFragment, "Bid")
        adapter.addFragment(diffAskTableFragment, "Ask")
        viewpagerHome.adapter = adapter
    }

}