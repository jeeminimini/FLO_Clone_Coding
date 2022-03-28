package com.example.flo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySongBinding.inflate(layoutInflater) // 바인딩 초기화
        setContentView(binding.root)
        binding.songDownIb.setOnClickListener{
            finish() //엑티비티를 꺼줌.
        }
        binding.songMiniplayerIv.setOnClickListener{
            setPlayerStatus(false)
        }
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(true)
        }
        //데이터가 안올 수도 있으니 if문 사용.
        if(intent.hasExtra("title")&&intent.hasExtra("singer")) { //타이틀과 싱어가 인텐트에 존재한다면
            binding.songMusicTitleTv.text=intent.getStringExtra("title")
            binding.songMusicSingerTv.text=intent.getStringExtra("singer")

        }
    }

    fun setPlayerStatus(isPlaying : Boolean){
        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.VISIBLE //재생버튼은 보이게
            binding.songPauseIv.visibility = View.GONE //정지버튼은 보이지 않게

        }else{ //처음이 false이다.
            binding.songMiniplayerIv.visibility = View.GONE //재생버튼은 보이지 않게
            binding.songPauseIv.visibility = View.VISIBLE //정지버튼은 보이게
        }
    }

}