package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemSavedSongBinding

class SavedSongRVAdapter(private val albumList: ArrayList<Album>): RecyclerView.Adapter<SavedSongRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onRemoveAlbum(position: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    fun addItem(album: Album){
        albumList.add(album)
        notifyDataSetChanged()  //리사이클러뷰는 데이터가 바뀌었다는 것을 모르기때문에 데이터가 바뀌었다고 꼭 알려주어야함. 따라서 이 함수를 호출해주어야함.
    }

    fun removeItem(position: Int){
        albumList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SavedSongRVAdapter.ViewHolder {
        val binding: ItemSavedSongBinding = ItemSavedSongBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedSongRVAdapter.ViewHolder, position: Int) { //인덱스 아이디가 position이다.
        holder.bind(albumList[position])
        holder.binding.itemSavedSongPlayerMoreIv.setOnClickListener { mItemClickListener.onRemoveAlbum(position) }
    }

    override fun getItemCount(): Int = albumList.size

    inner class ViewHolder(val binding: ItemSavedSongBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(album: Album){
            binding.itemSavedSongTitleTv.text=album.title
            binding.itemSavedSongSingerTv.text=album.singer
            binding.itemSavedSongCoverImgIv.setImageResource(album.coverImg!!)
        }
    }
}