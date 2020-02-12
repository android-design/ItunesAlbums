package com.fedorov.itunes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fedorov.itunes.databinding.AlbumItemRvBinding
import com.fedorov.itunes.databinding.TrackItemRvBinding
import com.fedorov.itunes.ui.Album
import com.fedorov.itunes.ui.ItunesData
import com.fedorov.itunes.ui.Track
import com.fedorov.itunes.ui.searchAlbums.SearchAlbumsFragment
import javax.inject.Inject

class ItunesAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_ALBUM = 0
        const val VIEW_TYPE_TRACK = 1
    }

    private val mDataList: MutableList<ItunesData> = ArrayList()
    private var listener: SearchAlbumsFragment.OnAlbumSelectedListener? = null

    fun attachListener(listener: SearchAlbumsFragment.OnAlbumSelectedListener) {
        this.listener = listener
    }

    fun setData(data: List<ItunesData>) {
        mDataList.clear()
        mDataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_ALBUM -> {
                val binding = AlbumItemRvBinding.inflate(inflater, parent, false)
                AlbumViewHolder(binding, delegate = listener)
            }
            else -> {
                val binding = TrackItemRvBinding.inflate(inflater, parent, false)
                TrackViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (mDataList[position]) {
            is Album -> VIEW_TYPE_ALBUM
            else -> VIEW_TYPE_TRACK
        }
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AlbumViewHolder -> holder.bind(model = (mDataList[position] as Album))
            is TrackViewHolder -> holder.bind(model = (mDataList[position] as Track))
        }
    }

    class AlbumViewHolder(private val binding: AlbumItemRvBinding, val delegate: SearchAlbumsFragment.OnAlbumSelectedListener?) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Album) {
            binding.album = model
            binding.listener = delegate
            binding.executePendingBindings()
        }
    }

    class TrackViewHolder(private val binding: TrackItemRvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Track) {
            binding.track = model
            binding.executePendingBindings()
        }
    }
}