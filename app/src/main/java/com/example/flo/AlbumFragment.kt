package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flo.databinding.ActivitySongBinding
import com.example.flo.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment() { //fragment를 상속받음. app어쩌고가 아니라.
    lateinit var binding: FragmentAlbumBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater,container,false)

        binding.albumBackIv.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,HomeFragment()).commitAllowingStateLoss() //home으로 바뀐다.
        }
        binding.songLilacLayout.setOnClickListener {
            Toast.makeText(activity,"LILAC",Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }


}