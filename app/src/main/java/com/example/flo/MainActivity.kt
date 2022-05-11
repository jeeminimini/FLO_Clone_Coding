package com.example.flo

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.flo.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var song : Song=Song()
    private var gson : Gson= Gson()

    private var mediaPlayer: MediaPlayer?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 처음 실행땐 R.style.SplashTheme가 먼저 실행되나 메인 액티비티가 onCreate될 땐 다시 메인 테마로 돌아와야 하기 때문에 작성해줌
        setTheme(R.style.Theme_FLO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainMiniplayerBtn.setOnClickListener {
            setPlayerStatus(true)
        }
        binding.mainPauseBtn.setOnClickListener {
            setPlayerStatus(false)
        }

        binding.mainPlayerCl.setOnClickListener {
            //startActivity(Intent(this,SongActivity::class.java))
//            val intent = Intent(this,SongActivity::class.java)
//            intent.putExtra("title",song.title)
//            intent.putExtra("singer",song.singer)
//            intent.putExtra("second",song.second)
//            intent.putExtra("playTime",song.playTime)
//            intent.putExtra("isPlaying",song.isPlaying)
//            intent.putExtra("music",song.music)
//             --> roomDB 쓰면서 밑에껄로 씀.
         val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
         editor.putInt("songId",song.id)
         editor.apply()

         val intent = Intent(this,SongActivity::class.java)
            startActivity(intent)
        }

        inputDummySongs()
        inputDummyAlbums()
        initBottomNavigation()



        Log.d("Song",song.title + song.singer)
    }

    fun setPlayerStatus(isPlaying: Boolean){
        song.isPlaying = isPlaying
        if(isPlaying){
            binding.mainMiniplayerBtn.visibility= View.GONE
            binding.mainPauseBtn.visibility= View.VISIBLE
            mediaPlayer?.start()
        }else{
            binding.mainPauseBtn.visibility= View.GONE
            binding.mainMiniplayerBtn.visibility= View.VISIBLE
            if(mediaPlayer?.isPlaying==true){
                mediaPlayer?.pause()
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

    private fun setMiniPlayer(song: Song){
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerTitleTv.text = song.singer
        binding.mainProgressSb.progress = (song.second*100000)/song.playTime


        //챌린지 과제
        val music = resources.getIdentifier(song.music,"raw",this.packageName)
        mediaPlayer=MediaPlayer.create(this,music) //mediaPlayer에 이 음악 재생할 것임을 알려줌.
        //여기까지
    }

    private fun inputDummySongs(){
        val songDB = SongDatabase.getInstance(this)!!
        val songs = songDB.songDao().getSongs()

        if (songs.isNotEmpty()) return

        songDB.songDao().insert(
            Song(
                "Lilac",
            "아이유 (IU)",
                0,
                200,
                false,
                "music_lilac",
                R.drawable.img_album_exp2,
                false,
            )
        )
        songDB.songDao().insert(
            Song(
                "Flu",
                "아이유 (IU)",
                0,
                200,
                false,
                "music_flu",
                R.drawable.img_album_exp2,
                false,
            )
        )
        songDB.songDao().insert(
            Song(
                "Next Level",
                "에스파 (AESPA)",
                0,
                210,
                false,
                "music_next_level",
                R.drawable.img_album_exp3,
                false,
            )
        )
        songDB.songDao().insert(
            Song(
                "Boy with Luv",
                "방탄소년단 (BTS)",
                0,
                230,
                false,
                "music_boy_with_luv",
                R.drawable.img_album_exp4,
                false,
            )
        )
        songDB.songDao().insert(
            Song(
                "BBoom BBoom",
                "모모랜드 (MOMOLAND)",
                0,
                240,
                false,
                "music_bboom",
                R.drawable.img_album_exp5,
                false,
            )
        )
        val _songs = songDB.songDao().getSongs()
        Log.d("DB data",_songs.toString())
    }

    private fun inputDummyAlbums(){
        val songDB = SongDatabase.getInstance(this)!!
        val albums = songDB.albumDao().getAlbums()

        if (albums.isNotEmpty()) return

        songDB.albumDao().insert(
            Album(
                0,
                "IU 5th Album 'LILAC'",
                "아이유 (IU)",
                R.drawable.img_album_exp2
            )
        )
        songDB.albumDao().insert(
            Album(
                1,
                "Butter",
                "방탄소년단 (BTS)",
                R.drawable.img_album_exp
            )
        )
        songDB.albumDao().insert(
            Album(
                2,
                "iScreaM Vol.10 : Next Level Remixes",
                "에스파 (AESPA)",
                R.drawable.img_album_exp3
            )
        )
        songDB.albumDao().insert(
            Album(
                3,
                "MAP OF THE SOUL : PERSONA",
                "방탄소년단 (BTS)",
                R.drawable.img_album_exp4
            )
        )

        songDB.albumDao().insert(
            Album(
                4,
                "GREAT!",
                "모모랜드 (MOMOLAND)",
                R.drawable.img_album_exp5
            )
        )

        val _songs = songDB.songDao().getSongs()
        Log.d("DB data",_songs.toString())
    }

    override fun onStart() { //onCreate가 아닌 onStart부터 하는 이유는 엑티비티가 전환이 될 때 onStart부터 시작되기 때문이다.
        //onResume에서 해도 되지만, onStart에서 ui와 관련한 코드를 초기화하는게 더 안정적이다.
        super.onStart()
//        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
//        val songJson = sharedPreferences.getString("songData",null)
//
//        song = if(songJson==null){
//            Song("라일락","아이유(IU)",0,60,false,"music_lilac")
//        }else{
//            gson.fromJson(songJson,Song::class.java)
//        }
        val spf = getSharedPreferences("song",MODE_PRIVATE)
        val songId = spf.getInt("songId",0)

        val songDB = SongDatabase.getInstance(this)!!

        song = if(songId == 0){
            songDB.songDao().getSong(1)
        }else{
            songDB.songDao().getSong(songId)
        }

        Log.d("song ID",song.id.toString())
        setMiniPlayer(song)

    }

    //챌린지 과제
    override fun onPause() {
        super.onPause()
        setPlayerStatus(false) //음악 재생 종료
        Log.d("onPause","miniPlayer 실행 도중 앱이 포커스를 잃음") //Log 출력
    }
    //여기까지
}