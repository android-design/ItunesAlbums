package com.fedorov.itunes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fedorov.itunes.R
import com.fedorov.itunes.adapters.ItunesAdapter
import com.fedorov.itunes.ui.vm.AlbumsViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_album_info.*
import kotlinx.android.synthetic.main.progressbar_main.*
import javax.inject.Inject

class AlbumInfoFragment : DaggerFragment() {

    private val args: AlbumInfoFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AlbumsViewModel> { viewModelFactory }
    private val mAdapter = ItunesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getShowPB().observe(this, Observer { show ->
            showProgressBar(show)
        })

        viewModel.getData().observe(this, Observer { data ->
            data?.let {
                mAdapter.setData(it)
                mAdapter.notifyDataSetChanged()
            }
        })

        viewModel.getEx().observe(this, Observer { error ->
            error?.let {
                it.message?.let { errorMessage ->
                    Toast.makeText(
                        context,
                        errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()

                    requireActivity().onBackPressed()
                }
            }
        })

        initRecyclerView()

        viewModel.getTracks(args.collectionId)
    }

    private fun initRecyclerView() {
        list_recycler_view.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = mAdapter
        }
    }


    private fun showProgressBar(show: Boolean) {
        progressBarGroup?.let {
            when (show) {
                true -> it.visibility = View.VISIBLE
                else -> it.visibility = View.INVISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJob()
    }
}