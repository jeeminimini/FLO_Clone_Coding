package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flo.databinding.ActivitySongBinding
import com.example.flo.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import layout.Like

class AlbumFragment : Fragment() { //fragment를 상속받음. app어쩌고가 아니라.
    lateinit var binding: FragmentAlbumBinding
    private var gson:Gson= Gson()

    private val information = arrayListOf("수록곡","상세정보","영상")

    private var isLiked : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater,container,false)
        //Home에서 넘어온 데이터 받아오기
        val albumJson=arguments?.getString("album")
        val album = gson.fromJson(albumJson,Album::class.java)
        //Home에서 넘어온 데이터를 반영
        isLiked=isLikedAlbum(album.id)
        setInit(album)
        setOnClickListeners(album)

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
        if(isLiked){
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }

    private fun getJwt():Int{
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE) //appcompat은 fragment에서 사용하는 방식이다
        return spf!!.getInt("jwt",0) //sharedpreferences에 값이 없으면 0을 반환

    }

    private fun likeAlbum(userId : Int, albumId: Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        val like = Like(userId, albumId)

        songDB.albumDao().likeAlbum(like)
    }

    private fun isLikedAlbum(albumId: Int): Boolean{
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()

        val likeId = songDB.albumDao().isLikedAlbum(userId, albumId)

        return likeId != null //유저가 좋아요누르면 null이 아니다. 따라서 리턴값이 true이고, 좋아요하지 않으면 null이어서 false를 반환한다.
    }

    private fun disLikedAlbum(albumId: Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()

        songDB.albumDao().disLikedAlbum(userId, albumId)
    }

    private fun setOnClickListeners(album: Album){
        val userId = getJwt()
        binding.albumLikeIv.setOnClickListener{
            if(isLiked){
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
                disLikedAlbum(album.id)
            }else{
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
                likeAlbum(userId,album.id)
            }
        }
    }
}