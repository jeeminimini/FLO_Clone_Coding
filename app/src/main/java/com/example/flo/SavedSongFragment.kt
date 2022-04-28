package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentSavedSongBinding

class SavedSongFragment : Fragment() {
    lateinit var binding: FragmentSavedSongBinding
    private var albumDatas = ArrayList<Album>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSavedSongBinding.inflate(inflater,container,false)

        albumDatas.apply {
            add(Album("Butter","방탄소년단 (BTS)",R.drawable.img_album_exp))
            add(Album("Lilac","아이유 (IU)",R.drawable.img_album_exp2))
            add(Album("Next Level","에스파 (AESPA)",R.drawable.img_album_exp))
            add(Album("Boy with Luv","방탄소년단 (BTS)",R.drawable.img_album_exp))
            add(Album("BBoom BBoom","모모랜드 (MOMOLAND)",R.drawable.img_album_exp))
            add(Album("Weekend","태연 (Tae Yeon)",R.drawable.img_album_exp))

        }

        val SavedSongRVAdapter= SavedSongRVAdapter(albumDatas)
        binding.savedSongMusicRv.adapter=SavedSongRVAdapter
        binding.savedSongMusicRv.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        SavedSongRVAdapter.setMyItemClickListener(object: SavedSongRVAdapter.MyItemClickListener{
            override fun onRemoveAlbum(position: Int) {
                SavedSongRVAdapter.removeItem(position)
            }

        })

        return binding.root
    }
}