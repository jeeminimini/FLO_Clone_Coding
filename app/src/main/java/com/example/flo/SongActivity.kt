package com.example.flo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding
    lateinit var song : Song
    lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySongBinding.inflate(layoutInflater) // 바인딩 초기화
        setContentView(binding.root)

        initSong()
        setPlayer(song)

        binding.songDownIb.setOnClickListener{
            finish() //엑티비티를 꺼줌.
        }
        binding.songMiniplayerIv.setOnClickListener{
            setPlayerStatus(true)
        }
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
    }

    private fun initSong(){
        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            song = Song(
                    intent.getStringExtra("title")!!,
                    intent.getStringExtra("singer")!!,
                    intent.getIntExtra("second",0),
                    intent.getIntExtra("playTime",0),
                    intent.getBooleanExtra("isPlaying",false),

                    )
        }
        startTimer()
    }

    private fun setPlayer(song: Song){
        binding.songMusicTitleTv.text=intent.getStringExtra("title")!!
        binding.songMusicSingerTv.text=intent.getStringExtra("singer")!!
        binding.songStartTimeTv.text=String.format("%02d:%02d",song.second/60, song.second % 60)
        binding.songEndTimeTv.text=String.format("%02d:%02d",song.playTime/60, song.playTime % 60)
        binding.songProgressSb.progress=(song.second*1000/song.playTime)

        setPlayerStatus(song.isPlaying)

    }

    private fun startTimer(){
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
    }

    fun setPlayerStatus(isPlaying : Boolean){
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.GONE //재생버튼은 보이게
            binding.songPauseIv.visibility = View.VISIBLE //정지버튼은 보이지 않게

        }else{ //처음이 false이다.
            binding.songMiniplayerIv.visibility = View.VISIBLE //재생버튼은 보이지 않게
            binding.songPauseIv.visibility = View.GONE //정지버튼은 보이게
        }
    }

    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true):Thread(){

        private var second : Int = 0
        private var mills: Float = 0f

        override fun run() {
            super.run()
            try {
                while (true){
                    if(second >= playTime){
                        break
                    }
                    if(isPlaying){
                        sleep(50) //진행되는 시간 관리를 위해
                        mills += 50
                        runOnUiThread{
                            binding.songProgressSb.progress = ((mills/playTime)*100).toInt()
                        }
                        if(mills % 1000 == 0f){
                            runOnUiThread {
                                binding.songStartTimeTv.text = String.format("%02d:%02d",second / 60, second % 60)
                            }
                            second++
                        }
                    }
                }
            }catch (e: InterruptedException){
                Log.d("song","쓰레드가 죽었습니다. ${e.message}")
            }

        }
    }

}