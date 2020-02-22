package com.fedorov.itunes.util

import androidx.recyclerview.widget.DiffUtil
import com.fedorov.itunes.ui.ItunesData

class AlbumsDiffUtilsCallback<T>(
    private val oldList: MutableList<ItunesData>,
    private val newList: List<T>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}