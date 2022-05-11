package com.example.flo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun insert(User:User)

    @Query("SELECT * FROM UserTable")
    fun getUsers() : List<User>

    @Query("SELECT * FROM UserTable WHERE email = :email AND password = :password")
    fun getUser(email:String, password:String) : User? //정보가 있을수도, 없을수도 있으니 ?로 null처리함.
    //로그인 정보가 일치하는지 학인해주는 함수
}