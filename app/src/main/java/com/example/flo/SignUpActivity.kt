package com.example.flo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpSignUpBtn.setOnClickListener {
            signUp()
            finish() //finish를 통해서 로그인 화면으로 이동을 하면서 회원가입 엑티비티가 꺼진다.
        }
    }

    private fun getUser(): User{
        val email : String = binding.signUpIdEt.text.toString() + "@" + binding.signUpEmailEt.text.toString()
        val pwd: String = binding.signUpPasswordEt.text.toString()

        return User(email, pwd)
    }

    private fun signUp(){
        if(binding.signUpIdEt.text.toString().isEmpty() || binding.signUpEmailEt.text.toString().isEmpty()){
            Toast.makeText(this,"이메일 형식이 잘못되었습니다.",Toast.LENGTH_SHORT).show()
            return //함수 끝나도록.
        }
        if(binding.signUpPasswordEt.text.toString() != binding.signUpPasswordConfirmEt.text.toString()){
            Toast.makeText(this,"비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show()
            return //함수 끝나도록.
        }

        //정보를 DB에 저장하는 방법
        val userDB = SongDatabase.getInstance(this)!!
        userDB.userDao().insert(getUser()) //getUser()는 유저가 입력한 정보이다

        val user = userDB.userDao().getUsers() //현재 테이블에 저장되어있는 정보 가져오기
        Log.d("SIGNUPACT",user.toString())

    }
}