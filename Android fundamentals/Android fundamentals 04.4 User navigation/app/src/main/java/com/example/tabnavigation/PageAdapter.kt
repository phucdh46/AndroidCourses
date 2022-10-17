package com.example.tabnavigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class PageAdapter(fm: FragmentManager, val mNumOfTabs: Int) :
    FragmentStatePagerAdapter(fm, mNumOfTabs) {


    override fun getCount() = mNumOfTabs

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return TabFragment1()
            1 -> return TabFragment2()
            2 -> return TabFragment3()
            else -> return TabFragment1()
        }
    }
}