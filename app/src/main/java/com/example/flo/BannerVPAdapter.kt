package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class BannerVPAdapter(fragment:Fragment) : FragmentStateAdapter(fragment){

    private val fragmentlist : ArrayList<Fragment> = ArrayList() // 초기화함.

    override fun getItemCount(): Int = fragmentlist.size

    override fun createFragment(position: Int): Fragment = fragmentlist[position] //getItemCount의 값이 4라면 0,1,2,3까지 실행이 된다.

    fun addFragment(fragment: Fragment){ //처음 프레그먼트 리스트에는 아무것도 없으니까 추가해주기 위해 이 함수 필요.
        fragmentlist.add(fragment)
        notifyItemInserted(fragmentlist.size-1) //리스트안에 새로운 값이 추가가 되어졌을 때 뷰페이져 안에 새로운게 추가되었다고 말해야한다.
        //인자값을 써준거임. 새로운 값이 리스트에 추가가 된 위치를 나타내기 위해 fragmentlist.size-1임. 인덱스는 0부터 시작되니까.

    }
}