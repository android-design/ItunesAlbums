package com.fedorov.itunes.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fedorov.itunes.R
import com.fedorov.itunes.adapters.ItunesAdapter
import com.fedorov.itunes.ui.vm.AlbumsViewModel
import com.fedorov.itunes.util.AlbumsDiffUtilsCallback
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_search_albums.*
import kotlinx.android.synthetic.main.progressbar_main.*
import javax.inject.Inject


class SearchAlbumsFragment : DaggerFragment() {

    interface OnAlbumSelectedListener {
        fun openAlbumInfo(collectionId: Int)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<AlbumsViewModel> { viewModelFactory }

    private lateinit var listener: OnAlbumSelectedListener

    private val mAdapter = ItunesAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnAlbumSelectedListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnAlbumSelectedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_albums, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter.attachListener(
            listener
        )

        viewModel.getShowPB().observe(this, Observer { show ->
            showProgressBar(show)
        })

        viewModel.getData().observe(this, Observer { data ->
            data?.let {
                val albumsDiffUtilCallback =
                    AlbumsDiffUtilsCallback(mAdapter.mDataList, it)
                val albumsDiffResult = DiffUtil.calculateDiff(albumsDiffUtilCallback)

                mAdapter.setData(it)
                albumsDiffResult.dispatchUpdatesTo(mAdapter)
            }
        })

        viewModel.getException().observe(this, Observer { error ->
            error?.let {
                it.message?.let { errorMessage ->
                    Toast.makeText(
                        context,
                        errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        initRecyclerView()
    }

    private fun initRecyclerView() {
        list_recycler_view.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = mAdapter
        }
    }

    fun getAlbums(albumName: String) {
        viewModel.getAlbums(albumName = albumName)
    }

    private fun showProgressBar(show: Boolean) {
        progressBarGroup?.let {
            when (show) {
                true -> it.visibility = View.VISIBLE
                else -> it.visibility = View.INVISIBLE
            }
        }
    }
}