package com.fedorov.itunes.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.progressbar_main.*
import kotlinx.android.synthetic.main.toolbar.*
import com.fedorov.itunes.App
import com.fedorov.itunes.R
import com.fedorov.itunes.adapters.ItunesAdapter
import com.fedorov.itunes.data.Repository
import com.fedorov.itunes.ui.ItunesDelegate
import com.fedorov.itunes.ui.ItunesData
import com.fedorov.itunes.ui.albumInfo.AlbumInfoActivity
import com.fedorov.itunes.ui.base.BaseActivity
import javax.inject.Inject


class MainActivity : BaseActivity<List<ItunesData>>() {

    override val layoutRes: Int = R.layout.activity_main

    override val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(
            this,
            MainActivityViewModelFactory(repository = repository)
        )
            .get(MainActivityViewModel::class.java)
    }

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var mAdapter: ItunesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        mAdapter.attachDelegate(object :
            ItunesDelegate {
            override fun openAlbumInfo(collectionId: Int) {
                navigateToAlbum(collectionId)
            }
        })

        initRecyclerView()
        initSearchView()
    }

    override fun initAppBar() {
        super.initAppBar()
        search_view_appbar.visibility = View.VISIBLE
    }

    private fun initRecyclerView() {
        list_recycler_view.apply {
            layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
            adapter = mAdapter
        }
    }

    private fun initSearchView() {
        search_view_appbar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                hideSoftKeyboard()
                viewModel.getAlbums(albumName = query)
                search_view_appbar.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
    }

    override fun viewOnSuccess(data: List<ItunesData>?) {
        super.viewOnSuccess(data)
        progressBarGroup.visibility = View.INVISIBLE
        data?.let {
            mAdapter.setData(it)
        }
    }

    fun navigateToAlbum(collectionId: Int) {
        startActivity(
            AlbumInfoActivity.newInstance(
                context = applicationContext,
                collectionId = collectionId
            )
        )
    }
}