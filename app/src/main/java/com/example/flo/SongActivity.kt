package com.example.flo

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding
    lateinit var song : Song
    lateinit var timer: Timer
    private var mediaPlayer : MediaPlayer? = null // ?는 null 값이 들어올 수 있음. 엑티비티가 소멸될 때 mediaplayer를 해제시켜줘야하기 때문에 nullable로 해줬다.
    private var gson : Gson= Gson()
    var pos : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySongBinding.inflate(layoutInflater) // 바인딩 초기화
        //inflater는 xml에 표기된 레이아웃들을 메모리에 객체화 시키는 행동으로 이해
        setContentView(binding.root)// ctrl + 'root'click 하면 activity_song.xml로 이동

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

        // 4주차 챌린지 과제 반복 재생 이미지 변경 *******************************************
        var repeat:Boolean=false
        binding.songPlaylistRepeatOnIb.setOnClickListener {
            if(!repeat){
                binding.songPlaylistRepeatOnIb.setImageResource(R.drawable.btn_playlist_repeat_on)// 이미지가 없어서 일단 이걸로
            }
            else{
                binding.songPlaylistRepeatOnIb.setImageResource(R.drawable.nugu_btn_repeat_inactive)
            }
            repeat=!repeat
        }
        var random:Boolean=true
        binding.songPlaylistRandomOnIb.setOnClickListener {
            if(!random){
                binding.songPlaylistRandomOnIb.setImageResource(R.drawable.btn_playlist_random_on)
            }
            else{
                binding.songPlaylistRandomOnIb.setImageResource(R.drawable.nugu_btn_random_inactive)
            }
            random=!random
        }
        // 여기까지 *****************************************************************
    /* onCreate는 AppCompatActivity라는 클래스 안에 있는 함수를 오버라이딩 */



       /* //이거는 5주차 챌린지하던거
        binding.songProgressSb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                //binding.songStartTimeTv.text = String.format("onProgressChanged p1 : $p1")
                Log.d("song","p1 입니다 : $p1")
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                binding.songStartTimeTv.text = String.format("onStartTrackingTouch")
                Log.d("song","onStartTrackingTouch")

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                song.isPlaying=true
                var ttt = p0?.getProgress()
                mediaPlayer?.seekTo(ttt!!)
                mediaPlayer?.start()
                binding.songStartTimeTv.text = String.format("onStopTrackingTouch")
//                timer.start()
                Log.d("song","onStopTrackingTouch")

            }

        })*/


    }

    private fun initSong(){
        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            song = Song(
                    intent.getStringExtra("title")!!,
                    intent.getStringExtra("singer")!!,
                    intent.getIntExtra("second",0),
                    intent.getIntExtra("playTime",0),
                    intent.getBooleanExtra("isPlaying",false),
                    intent.getStringExtra("music")!!

                    )
            Log.d("song","title : ${song.title}\n" +
                    "singer : ${song.singer}\n" +
                    "second : ${song.second}\n" +
                    "playTime : ${song.playTime}\n" +
                    "isPlaying : ${song.isPlaying}\n" +
                    "music : ${song.music}")
        }
        startTimer()
    }

    private fun setPlayer(song: Song){
        binding.songMusicTitleTv.text=intent.getStringExtra("title")!!
        binding.songMusicSingerTv.text=intent.getStringExtra("singer")!!
        binding.songStartTimeTv.text=String.format("%02d:%02d",song.second/60, song.second % 60)
        binding.songEndTimeTv.text=String.format("%02d:%02d",song.playTime/60, song.playTime % 60)
        binding.songProgressSb.progress=(song.second*1000/song.playTime)
        val music = resources.getIdentifier(song.music,"raw",this.packageName)//song data파일이 String 값으로 되어있음. 이것을 실제로 실행시키려면
        // 리소스파일에서 해당 스트링 값으로 찾아서 리소스를 반환해주는 무언가가 필요하다. 이는 resources.getIdentifier로 할 수 있다.
        mediaPlayer = MediaPlayer.create(this,music)
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
            mediaPlayer?.seekTo(pos)
            mediaPlayer?.start()

        }else{ //처음이 false이다.
            binding.songMiniplayerIv.visibility = View.VISIBLE //재생버튼은 보이지 않게
            binding.songPauseIv.visibility = View.GONE //정지버튼은 보이게
            pos = mediaPlayer?.currentPosition!!
            if(mediaPlayer?.isPlaying==true){ //mediaPlayer는 재생중이 아닐 때 pasue를 하면 오류가 생길 수 있으므로 if문을 사용했다.
                mediaPlayer?.pause()
            }
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
                            Log.d("song seekbar","재생중 ${((mills/playTime)*100).toInt()}, 미디어플레이어 currentposition ${mediaPlayer?.currentPosition}")
                        }
                        if(mills % 1000 == 0f){
                            runOnUiThread {
                                binding.songStartTimeTv.text = String.format("%02d:%02d",second / 60, second % 60)
                            }
                            second++
                        }
//                        binding.songProgressSb.setProgress(mediaPlayer?.currentPosition!!)
                    }
                }
            }catch (e: InterruptedException){
                Log.d("song","쓰레드가 죽었습니다. ${e.message}")
            }

        }
    }
    //사용자가 포커스를 잃었을 때 음악이 중지
    override fun onPause() {
        super.onPause()
        setPlayerStatus(false)
        song.second = ((binding.songProgressSb.progress * song.playTime)/100)/1000 //밀리세컨드로 계산되어있어서 세컨드로 바꿔주기 위해 1000으로 나눔.
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE) //내부저장소에 데이터를 저장해놀을 수 있게함. 앱을 종료했다 켜도 저장된 데이터를 꺼내서 사용핲 수 있게 함.
        // 간단한 설정값을 저장하기 좋음. ex.비밀번호 기억.
        val editor = sharedPreferences.edit() //에디터
        //gson : java <-> json 변환 해줌
        val songJson = gson.toJson(song) //song객체를 json포멧으로 변환시켜줌.
        editor.putString("songData",songJson) //깃에서 푸시라 생각.
        editor.apply() //깃에서 푸시라고 생각.

    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release() // 미디어플레이어가 갖고 있던 리소스 해제
        mediaPlayer = null // 미디어 플레이어 해제
    }

}