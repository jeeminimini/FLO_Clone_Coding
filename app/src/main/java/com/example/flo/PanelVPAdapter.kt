package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PanelVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = Int.MAX_VALUE
    override fun createFragment(position: Int): Fragment {
        return when (position%3) {
            0 -> Panel1Fragment()
            1 -> Panel2Fragment()
            else -> Panel3Fragment()
        }
    }
}