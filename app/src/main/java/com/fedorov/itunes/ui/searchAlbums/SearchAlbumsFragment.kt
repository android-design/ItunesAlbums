package com.fedorov.itunes.ui.searchAlbums

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fedorov.itunes.R
import com.fedorov.itunes.adapters.ItunesAdapter
import com.fedorov.itunes.ui.ItunesData
import com.fedorov.itunes.ui.Status
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_search_albums.*
import kotlinx.android.synthetic.main.progressbar_main.*
import javax.inject.Inject


class SearchAlbumsFragment : DaggerFragment() {

    private lateinit var listener: OnAlbumSelectedListener

    interface OnAlbumSelectedListener {
        fun openAlbumInfo(collectionId: Int)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<SearchAlbumsViewModel> { viewModelFactory }

    @Inject
    lateinit var mAdapter: ItunesAdapter

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

        observeNewEvents()
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

    private fun observeNewEvents() {
        viewModel.eventData.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> viewOnLoading()
                Status.SUCCESS -> viewOnSuccess(it.data)
                Status.ERROR -> viewOnError(it.error)
            }
        })
    }

    private fun viewOnLoading() {
        showProgressBar()
    }

    private fun showProgressBar() {
        progressBarGroup?.let {
            it.visibility = View.VISIBLE
        }
    }

    private fun hideProgressBar() {
        progressBarGroup?.let {
            it.visibility = View.INVISIBLE
        }
    }

    private fun viewOnError(error: Exception?) {
        hideProgressBar()
        error?.let {
            it.message?.let { errorMessage ->
                Toast.makeText(
                    context,
                    errorMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun viewOnSuccess(data: List<ItunesData>?) {
        hideProgressBar()
        data?.let {
            mAdapter.setData(it)
        }
    }
}