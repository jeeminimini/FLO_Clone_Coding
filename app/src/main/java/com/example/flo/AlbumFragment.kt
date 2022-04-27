package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flo.databinding.ActivitySongBinding
import com.example.flo.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class AlbumFragment : Fragment() { //fragment를 상속받음. app어쩌고가 아니라.
    lateinit var binding: FragmentAlbumBinding
    private var gson:Gson= Gson()

    private val information = arrayListOf("수록곡","상세정보","영상")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater,container,false)

        val albumJson=arguments?.getString("album")
        val album = gson.fromJson(albumJson,Album::class.java)
        setInit(album)

        binding.albumBackIv.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,HomeFragment()).commitAllowingStateLoss() //home으로 바뀐다.
        }

        val albumAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdapter
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp){
            tab, position -> tab.text = information[position]
        }.attach()

//        binding.songLilacLayout.setOnClickListener {
//            Toast.makeText(activity,"LILAC",Toast.LENGTH_SHORT).show()
//        }
        return binding.root
    }

    private fun setInit(album: Album){
        binding.albumAlbumIv.setImageResource(album.coverImg!!)
        binding.albumTitleTv.text=album.title.toString()
        binding.albumSingerTv.text=album.singer.toString()
    }


}