package com.fedorov.itunes.ui.albumInfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import com.fedorov.itunes.App
import com.fedorov.itunes.R
import com.fedorov.itunes.adapters.ItunesAdapter
import com.fedorov.itunes.data.Repository
import com.fedorov.itunes.ui.ItunesData
import com.fedorov.itunes.ui.base.BaseActivity
import javax.inject.Inject

class AlbumInfoActivity : BaseActivity<List<ItunesData>>() {

    companion object {
        const val ALBUM_ID = "COLLECTION_ID"
        fun newInstance(context: Context, collectionId: Int): Intent {
            val intent = Intent(
                context,
                AlbumInfoActivity::class.java
            )
            intent.putExtra(ALBUM_ID, collectionId)
            return intent
        }
    }

    override val layoutRes: Int = R.layout.activity_album_info
    override val viewModel: AlbumInfoViewModel by lazy {
        ViewModelProvider(this, AlbumInfoViewModelFactory(repository = repository)).get(
            AlbumInfoViewModel::class.java
        )
    }

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var mAdapter: ItunesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)

        super.onCreate(savedInstanceState)

        val arguments = intent.extras
        arguments?.let {
            it.getInt(ALBUM_ID).let { collectionId -> viewModel.getTracks(collectionId) }
        }

        list_recycler_view.apply {
            layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
            adapter = mAdapter
        }
    }

    override fun initAppBar() {
        super.initAppBar()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun viewOnSuccess(data: List<ItunesData>?) {
        super.viewOnSuccess(data)
        data?.let {
            mAdapter.setData(it)
        }
    }

    override fun viewOnError(error: Exception?) {
        super.viewOnError(error)
        // return to main view
        onBackPressed()
    }
}