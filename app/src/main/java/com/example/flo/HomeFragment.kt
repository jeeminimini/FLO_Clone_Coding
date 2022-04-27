package com.example.flo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.FragmentHomeBinding
import com.google.gson.Gson

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private var albumDatas = ArrayList<Album>()

    private var currentPosition_panel = Int.MAX_VALUE/3
    private var currentPosition_banner = 0
    val handler=Handler(Looper.getMainLooper()){
        setPage()
        true
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

//        binding.homeAlbumImgIv1.setOnClickListener{
//            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,AlbumFragment()).commitAllowingStateLoss()  //homefragment를 albumfragment로 대체한다는 의미
//        }

        albumDatas.apply {
            add(Album("Butter","방탄소년단 (BTS)",R.drawable.img_album_exp))
            add(Album("Lilac","아이유 (IU)",R.drawable.img_album_exp2))
            add(Album("Next Level","에스파 (AESPA)",R.drawable.img_album_exp))
            add(Album("Boy with Luv","방탄소년단 (BTS)",R.drawable.img_album_exp))
            add(Album("BBoom BBoom","모모랜드 (MOMOLAND)",R.drawable.img_album_exp))
            add(Album("Weekend","태연 (Tae Yeon)",R.drawable.img_album_exp))

        }

        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicAlbumRv.adapter=albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

        albumRVAdapter.setMyItemClickListener(object: AlbumRVAdapter.MyItemClickListener{
            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }

            override fun onRemoveAlbum(position: Int) {
                albumRVAdapter.removeItem(position)
            }
        })

        val panelAdapter = PanelVPAdapter(this)
        binding.homePanelVp.adapter = panelAdapter
        binding.homePanelVp.orientation=ViewPager2.ORIENTATION_HORIZONTAL
        binding.homePanelVp.setCurrentItem(currentPosition_panel,false) //현재 위치를 지정
        binding.homePanelVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                binding.homeCi.selectDot(position%3)
            }
        })

        binding.homeCi.createDotPanel(3,R.drawable.indicator_dot_off,R.drawable.indicator_dot_on,0)

        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL //뷰페이저가 좌우로 스크롤 될 수 있도록 하는 것.

        val thread = Thread(PagerRunnable())
        thread.start()

        return binding.root
    }

    private fun changeAlbumFragment(album: Album) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, AlbumFragment().apply {
                    arguments = Bundle().apply {
                        val gson = Gson()
                        val albumJson = gson.toJson(album)
                        putString("album", albumJson)
                    }
                })
                .commitAllowingStateLoss()
    }

    fun setPage(){
        Log.d("home","position: ${currentPosition_banner}")
        if(currentPosition_banner==2) currentPosition_banner=0
        binding.homeBannerVp.setCurrentItem(currentPosition_banner,true)
        currentPosition_banner+=1
    }

    //2초마다 페이지 넘기기
    inner class PagerRunnable:Runnable{
        override fun run() {
            while (true){
                Thread.sleep(2000)
                handler.sendEmptyMessage(0)
            }
        }
    }

}