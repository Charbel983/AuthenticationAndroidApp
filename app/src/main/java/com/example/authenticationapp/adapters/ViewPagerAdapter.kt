package com.example.authenticationapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.authenticationapp.fragments.MoreFragment
import com.example.authenticationapp.fragments.NewsFragment

open class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        if(position == 1){
            return MoreFragment()
        }
        return NewsFragment()
    }

    override fun getItemCount(): Int {
        return 2
    }
}