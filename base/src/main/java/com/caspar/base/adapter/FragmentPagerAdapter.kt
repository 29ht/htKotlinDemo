package com.caspar.base.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.*

class FragmentPagerAdapter(activity: AppCompatActivity, pagerItemList: ArrayList<Fragment>) : FragmentStateAdapter(activity) {
    private var mFragmentList: ArrayList<Fragment>? = pagerItemList


    override fun getItemCount(): Int {
        return mFragmentList!!.size
    }

    override fun createFragment(position: Int): Fragment {
        return if (position < mFragmentList!!.size) {
            mFragmentList!![position]
        } else {
            mFragmentList!![0]
        }
    }


}

