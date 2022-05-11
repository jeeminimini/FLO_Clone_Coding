package com.example.flo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import layout.Like

@Dao
interface AlbumDao {
    @Insert
    fun insert(album: Album)

    @Query("SELECT * FROM AlbumTable")
    fun getAlbums():List<Album>

    @Insert
    fun likeAlbum(Like: Like)

    @Query("SELECT id FROM LikeTable WHERE userId = :userId AND albumId = :albumId")
    fun isLikedAlbum(userId:Int, albumId:Int):Int?

    @Query("DELETE FROM LikeTable WHERE userId = :userId AND albumId = :albumId")
    fun disLikedAlbum(userId:Int, albumId:Int)

    @Query("SELECT AT.* FROM LikeTable as LT LEFT JOIN AlbumTable as AT On LT.albumId = AT.id WHERE LT.userID = :userId")
    fun getLikedAlbums(userId: Int) : List<Album>
}