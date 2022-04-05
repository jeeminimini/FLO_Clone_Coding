package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.ActivitySongBinding
import com.example.flo.databinding.FragmentBannerBinding
import com.example.flo.databinding.FragmentDetailBinding
import com.example.flo.databinding.FragmentSongBinding

class SongFragment : Fragment(){
    lateinit var binding: FragmentSongBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSongBinding.inflate(inflater,container,false)

        binding.songMixoffTg.setOnClickListener{
            setPlayerStatus(false)
        }
        binding.songMixonTg.setOnClickListener {
            setPlayerStatus(true)
        }

        return binding.root
    }

    fun setPlayerStatus(isChecking : Boolean){
        if(isChecking){
            binding.songMixoffTg.visibility = View.VISIBLE
            binding.songMixonTg.visibility = View.GONE

        }else{ //처음이 false이다.
            binding.songMixoffTg.visibility = View.GONE
            binding.songMixonTg.visibility = View.VISIBLE
        }
    }

}