package com.example.flo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemLockerAlbumBinding
import com.example.flo.databinding.ItemSavedSongBinding

class AlbumLockerRVAdapter(): RecyclerView.Adapter<AlbumLockerRVAdapter.ViewHolder>() { //6주차에는 그냥 먼저 노래들을 보관함에 넣어줬는데, 7주차에는 좋아요 누른 것만 넣어줌
    private val albums = ArrayList<Album>()
    interface MyItemClickListener{
        fun onRemoveSong(songId: Int)
    }
    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumLockerRVAdapter.ViewHolder {
        val binding: ItemLockerAlbumBinding = ItemLockerAlbumBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumLockerRVAdapter.ViewHolder, position: Int) { //인덱스 아이디가 position이다.
        holder.bind(albums[position])
        holder.binding.itemLockerAlbumPlayerMoreIv.setOnClickListener {
            mItemClickListener.onRemoveSong(albums[position].id)
            removeAlbum(position)
        }
    }

    override fun getItemCount(): Int = albums.size

    @SuppressLint("NotifyDataSetChanged")
    fun addAlbums(albums: ArrayList<Album>){
        this.albums.clear()
        this.albums.addAll(albums)

        notifyDataSetChanged()  //리사이클러뷰는 데이터가 바뀌었다는 것을 모르기때문에 데이터가 바뀌었다고 꼭 알려주어야함. 따라서 이 함수를 호출해주어야함.
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeAlbum(position: Int){
        albums.removeAt(position)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemLockerAlbumBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(album: Album){
            binding.itemLockerAlbumTitleTv.text=album.title
            binding.itemLockerAlbumSingerTv.text=album.singer
            binding.itemLockerAlbumCoverImgIv.setImageResource(album.coverImg!!)
        }
    }
}