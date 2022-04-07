package com.example.flo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.flo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var song : Song
    lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FLO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString(),0,60,0,0f,false)
        timer=Timer(song.playTime,song.isPlaying)
        timer.start()
        setPlayer(song)

        binding.mainMiniplayerBtn.setOnClickListener {
            setPlayerStatus(true)
        }
        binding.mainPauseBtn.setOnClickListener {
            setPlayerStatus(false)
        }

        binding.mainPlayerCl.setOnClickListener {
            //startActivity(Intent(this,SongActivity::class.java))
            val intent = Intent(this,SongActivity::class.java)
            intent.putExtra("title",song.title)
            intent.putExtra("singer",song.singer)
            intent.putExtra("second",song.second)
            intent.putExtra("playTime",song.playTime)
            intent.putExtra("playingSecond",song.playingSecond)
            intent.putExtra("playingMills",song.playingMills)
            intent.putExtra("isPlaying",song.isPlaying)
            startActivity(intent)
        }

        initBottomNavigation()



        Log.d("Song",song.title + song.singer)
    }

    fun setPlayerStatus(isPlaying: Boolean){
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.mainMiniplayerBtn.visibility= View.GONE
            binding.mainPauseBtn.visibility= View.VISIBLE
        }else{
            binding.mainPauseBtn.visibility= View.GONE
            binding.mainMiniplayerBtn.visibility= View.VISIBLE
        }

    }

    private fun setPlayer(song:Song){
        binding.mainProgressSb.progress=(song.playingSecond*1000/song.playTime) //이게 뭘까..
        Log.d("main","setPlayer 함수 progress: ${song.playingSecond*1000/song.playTime}")
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
                            binding.mainProgressSb.progress = ((mills/playTime)*100).toInt()
                            song.playingMills = mills
                            Log.d("main seekbar","재생중 ${((mills/playTime)*100).toInt()}")
                        }
                        if(mills % 1000 == 0f){
                            runOnUiThread {
                                song.playingSecond=second
                            }
                            second++
                        }
                    }
                }
            }catch (e: InterruptedException){
                Log.d("main","쓰레드가 죽었습니다. ${e.message}")
            }

        }
    }

    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}