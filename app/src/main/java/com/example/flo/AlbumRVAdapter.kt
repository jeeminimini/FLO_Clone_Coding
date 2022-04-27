package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemAlbumBinding

class AlbumRVAdapter(private val albumList: ArrayList<Album>): RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>(){

    interface MyItemClickListener{
        fun onItemClick(album: Album)
        fun onRemoveAlbum(position: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListenr: MyItemClickListener){
        mItemClickListener = itemClickListenr
    }

    fun addItem(album: Album){
        albumList.add(album)
        notifyDataSetChanged()  //리사이클러뷰는 데이터가 바뀌었다는 것을 모르기때문에 데이터가 바뀌었다고 꼭 알려주어야함. 따라서 이 함수를 호출해주어야함.
    }

    fun removeItem(position: Int){
        albumList.removeAt(position)
        notifyDataSetChanged()
    }

    //리사이클러뷰는 처음에 화면에 보일 몇개만 만들면 되기 때문에, onCreateViewHolder는 몇번만 호출된다.
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumRVAdapter.ViewHolder {
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)
        return ViewHolder(binding)
    }

    //스크롤할 때 마다 호출된다.(데이터를 뷰에 바인딩할 때 마다 호출됨)
    override fun onBindViewHolder(holder: AlbumRVAdapter.ViewHolder, position: Int) {
        holder.bind(albumList[position])
        holder.itemView.setOnClickListener {mItemClickListener.onItemClick(albumList[position])}
//        holder.binding.itemAlbumTitleTv.setOnClickListener { mItemClickListener.onRemoveAlbum((position)) } //타이틀 누르면 삭제됨
    }

    //데이터셋 크기를 알려주는 함수로, 리사이클러뷰의 마지막이 언제인지를 알려줌.
    override fun getItemCount(): Int = albumList.size //앨범리스트의 크기를 넣어주면 됨.

    //ViewHolder는 아이템뷰 객체들을 재활용하기 위해 날라가지 않게 담는 그릇.
    inner class ViewHolder(val binding: ItemAlbumBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(album: Album){
            binding.itemAlbumTitleTv.text=album.title
            binding.itemAlbumSingerTv.text=album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)
        }
    }
}